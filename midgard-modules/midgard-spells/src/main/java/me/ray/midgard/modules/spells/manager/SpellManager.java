package me.ray.midgard.modules.spells.manager;

import io.lumine.mythic.api.MythicProvider;
import io.lumine.mythic.api.skills.Skill;
import io.lumine.mythic.bukkit.MythicBukkit;
import me.ray.midgard.core.MidgardCore;
import me.ray.midgard.modules.spells.SpellsModule;
import me.ray.midgard.modules.spells.profile.MatrixState;
import me.ray.midgard.modules.spells.profile.SpellProfile;
import me.ray.midgard.modules.spells.obj.NodeType;
import me.ray.midgard.modules.spells.obj.Spell;
import me.ray.midgard.modules.spells.obj.template.MatrixTemplate;
import me.ray.midgard.modules.spells.obj.template.MatrixTemplate.MatrixNode;
import me.ray.midgard.modules.spells.obj.SpellRune;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.core.skills.variables.VariableScope;
import io.lumine.mythic.core.skills.variables.VariableRegistry;

import java.io.File;
import java.util.*;

import org.bukkit.configuration.ConfigurationSection;

public class SpellManager {

    private final SpellsModule module;
    private final MatrixTemplateManager templateManager;
    private final Map<String, Spell> loadedSpells = new HashMap<>();
    private final Map<String, SpellRune> loadedRunes = new HashMap<>();
    private final Set<UUID> castingModePlayers = new HashSet<>();

    private final Map<UUID, Integer> castingAnchors = new HashMap<>();
    private final Map<Integer, String> defaultCombos = new HashMap<>();
    
    // Active profiles (In-memory for now)
    private final Map<UUID, SpellProfile> profiles = new HashMap<>();

    public SpellManager(SpellsModule module) {
        this.module = module;
        this.templateManager = module.getTemplateManager(); // Recupera do módulo
        loadDefaultCombos();
    }

    private void loadDefaultCombos() {
        defaultCombos.put(1, "LLL");
        defaultCombos.put(2, "RRR");
        defaultCombos.put(3, "LRL");
        defaultCombos.put(4, "RLR");
        defaultCombos.put(5, "LLR");
        defaultCombos.put(6, "RRL");
        
        if (module.getConfig() != null) {
            ConfigurationSection section = module.getConfig().getConfigurationSection("combos");
            if (section != null) {
                for (String key : section.getKeys(false)) {
                    try {
                        int slot = Integer.parseInt(key);
                        String seq = section.getString(key);
                        if (seq != null) {
                            defaultCombos.put(slot, seq.toUpperCase());
                        }
                    } catch (NumberFormatException ignored) {}
                }
            }
        }
    }
    
    public String getDefaultCombo(int slot) {
        return defaultCombos.getOrDefault(slot, "Undefined");
    }

    public SpellsModule getModule() {
        return module;
    }

    public void toggleCastingMode(Player player) {
        if (castingModePlayers.contains(player.getUniqueId())) {
            disableCastingMode(player);
        } else {
            enableCastingMode(player);
        }
    }

    public void enableCastingMode(Player player) {
        castingModePlayers.add(player.getUniqueId());
        
        int anchor = player.getInventory().getHeldItemSlot(); 
        castingAnchors.put(player.getUniqueId(), anchor);
        
        player.setMetadata("midgard_casting_mode", new org.bukkit.metadata.FixedMetadataValue(module.getPlugin(), true));
        player.playSound(player.getLocation(), org.bukkit.Sound.BLOCK_IRON_TRAPDOOR_OPEN, 1f, 1.5f);
        
        String enableMsg = module.getMessage("casting.mode_enabled");
        me.ray.midgard.core.text.MessageUtils.send(player, enableMsg);
    }

    public void disableCastingMode(Player player) {
        castingModePlayers.remove(player.getUniqueId());
        castingAnchors.remove(player.getUniqueId());
        player.removeMetadata("midgard_casting_mode", module.getPlugin());
        player.playSound(player.getLocation(), org.bukkit.Sound.BLOCK_IRON_TRAPDOOR_CLOSE, 1f, 0.5f);
        me.ray.midgard.core.text.MessageUtils.sendActionBar(player, "");
        
        String disableMsg = module.getMessage("casting.mode_disabled");
        me.ray.midgard.core.text.MessageUtils.send(player, disableMsg);
    }

    public boolean isCastingMode(Player player) {
        return castingModePlayers.contains(player.getUniqueId());
    }
    
    public Set<UUID> getCastingPlayers() {
        return Collections.unmodifiableSet(castingModePlayers);
    }
    
    public void castSkillBar(Player player, int slot) {
        SpellProfile profile = getProfile(player);
        if (profile == null) return;
        
        String spellId = profile.getSkillInSlot(slot);
        if (spellId == null) return;
        
        castSpell(player, spellId);
    }
    
    public String getSkillInVirtualSlot(Player player, int visualSlotIndex) {
        SpellProfile profile = getProfile(player);
        if (profile == null) return null;

        Integer anchor = castingAnchors.get(player.getUniqueId());
        if (anchor == null) {
            return profile.getSkillInSlot(visualSlotIndex + 1);
        }

        if (visualSlotIndex == anchor) {
            return null;
        }

        if (visualSlotIndex > anchor) {
            return profile.getSkillInSlot(visualSlotIndex); 
        }

        return profile.getSkillInSlot(visualSlotIndex + 1);
    }

    public void loadRunes() {
        loadedRunes.clear();
        
        // Mock "Jade Rune"
        Map<String, Double> jadeStats = new HashMap<>();
        jadeStats.put("radius", 2.0);
        jadeStats.put("mana_cost", -5.0);
        loadedRunes.put("jade_rune", new SpellRune("jade_rune", "Jade Rune", "Aumenta o raio e reduz custo de mana.", "EMERALD", 0, jadeStats));
        
        module.getPlugin().getLogger().info("Loaded " + loadedRunes.size() + " runes.");
    }
    
    public SpellRune getRune(String id) {
        return loadedRunes.get(id);
    }

    public Collection<SpellRune> getRunes() {
        return Collections.unmodifiableCollection(loadedRunes.values());
    }

    public void loadSpells() {
        loadRunes(); 
        loadedSpells.clear();
        
        File moduleFolder = new File(module.getPlugin().getDataFolder(), "modules/spells");
        File spellsFolder = new File(moduleFolder, "spells");
        
        if (!spellsFolder.exists()) {
            spellsFolder.mkdirs();
            createDefaultSpell(spellsFolder, "fireball.yml");
            createDefaultSpell(spellsFolder, "icebolt.yml");
            createDefaultSpell(spellsFolder, "magic_missile.yml");
        }

        File[] files = spellsFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files == null) return;

        for (File file : files) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            String id = file.getName().replace(".yml", "");
            
            String name = config.getString("name", id);
            String mythicSkill = config.getString("mythic-skill");
            double cooldown = config.getDouble("cooldown");
            double mana = config.getDouble("mana");
            double stamina = config.getDouble("stamina");
            List<String> lore = config.getStringList("lore");
            
            String templateId = config.getString("matrix-template");

            Spell spell = new Spell(id, mythicSkill, name, lore, cooldown, mana, stamina, templateId);
            loadedSpells.put(id, spell);
        }
        
        module.getPlugin().getLogger().info("Loaded " + loadedSpells.size() + " spells from " + spellsFolder.getPath());
    }

    private void createDefaultSpell(File folder, String fileName) {
        File file = new File(folder, fileName);
        if (!file.exists()) {
            try {
                module.getPlugin().saveResource("modules/spells/spells/" + fileName, false);
            } catch (Exception e) {
                module.getPlugin().getLogger().warning("Could not save default spell " + fileName + ": " + e.getMessage());
            }
        }
    }

    public Spell getSpell(String id) {
        return loadedSpells.get(id);
    }

    public Collection<Spell> getSpells() {
        return Collections.unmodifiableCollection(loadedSpells.values());
    }
    
    public Set<String> getLoadedSpellIds() {
        return Collections.unmodifiableSet(loadedSpells.keySet());
    }

    public SpellProfile getProfile(Player player) {
        return profiles.computeIfAbsent(player.getUniqueId(), SpellProfile::new);
    }
    
    // Method to clear profile (on quit)
    public void unloadProfile(Player player) {
        profiles.remove(player.getUniqueId());
    }

    public boolean castSpell(Player player, String spellId) {
        Spell spell = getSpell(spellId);
        if (spell == null) return false;

        SpellProfile profile = getProfile(player);
        if (profile == null) return false;
        
        if (profile.isOnCooldown(spellId)) {
            long remaining = profile.getCooldownRemainingKey(spellId) / 1000;
            String cooldownMsg = module.getMessage("casting.on_cooldown")
                .replace("%spell%", spell.getDisplayName())
                .replace("%time%", String.valueOf(remaining));
            me.ray.midgard.core.text.MessageUtils.send(player, cooldownMsg);
            return false;
        }

        MatrixState matrix = profile.getMatrixState(spellId);
        String skillToCast = spell.getMythicSkillName();
        
        // --- LOGIC REFACTOR START ---
        // Recuperar o Template (A Spell diz qual template usa)
        String templateId = spell.getMatrixTemplateId();
        
        if (templateId != null && templateManager.getTemplate(templateId) != null) {
            MatrixTemplate template = templateManager.getTemplate(templateId);
            
             // 3. Resolver Mutações (Cruzando Estado com Template)
            for (int activeSlot : matrix.getActiveMutations()) {
                MatrixNode node = template.getNode(activeSlot);
                
                if (node != null && node.getType() == NodeType.MUTATION && node.getMutationSkill() != null) {
                    skillToCast = node.getMutationSkill();
                }
            }
        }
        // --- LOGIC REFACTOR END ---
        
        // Validate Skill
        Optional<Skill> mythicSkill = MythicProvider.get().getSkillManager().getSkill(skillToCast);
        if (mythicSkill.isEmpty()) {
            String errorMsg = module.getMessage("errors.config_error")
                .replace("%skill%", skillToCast);
            me.ray.midgard.core.text.MessageUtils.send(player, errorMsg);
            return false;
        }

        // 2. Calculate Rune Variables (Math Injection)
        Map<String, Double> runtimeVariables = new HashMap<>();
        
        runtimeVariables.put("damage_bonus", 0.0);
        runtimeVariables.put("size_bonus", 0.0);
        runtimeVariables.put("duration_bonus", 0.0);

        // Sum up stats from runes (Not implemented fully in MatrixState yet, assuming future)
        // matrix.getSocketRunes() requires MatrixState update. 
        // For now, ignoring runes or assuming base Logic 
        // NOTE: MatrixState provided by user did not have getSocketRunes().
        // I will comment out Rune logic linked to sockets for now.
        
        /*
        for (String runeId : matrix.getSocketRunes().values()) {
            SpellRune rune = getRune(runeId);
            if (rune != null) {
                rune.getStats().forEach((key, value) -> 
                    runtimeVariables.merge(key, value, Double::sum)
                );
            }
        }
        */

        // 3. Inject into MythicMobs Caster Scope
        AbstractEntity abstractPlayer = BukkitAdapter.adapt(player);
        VariableRegistry registry = MythicBukkit.inst().getVariableManager().getRegistry(VariableScope.CASTER, abstractPlayer);
        
        for (Map.Entry<String, Double> entry : runtimeVariables.entrySet()) {
            registry.putFloat(entry.getKey(), entry.getValue().floatValue());
        }

        // Cast
        boolean success = MythicBukkit.inst().getAPIHelper().castSkill(player, skillToCast);
        if (success) {
            
            // Execute Extra Matrix Skills (Passives/Extras)
            if (templateId != null && templateManager.getTemplate(templateId) != null) {
                MatrixTemplate template = templateManager.getTemplate(templateId);
                 for (int activeSlot : matrix.getActiveMutations()) { // Assuming 'ActiveMutations' tracks unlocked nodes
                     MatrixNode node = template.getNode(activeSlot);
                     if (node != null && node.getType() == NodeType.MYTHIC_SKILL && node.getMutationSkill() != null) {
                          MythicBukkit.inst().getAPIHelper().castSkill(player, node.getMutationSkill());
                     }
                }
            }

            profile.setCooldown(spellId, spell.getCooldown());
            if (module.getConfig().getBoolean("general.show_cast_messages", true)) {
                String castMsg = module.getMessage("casting.spell_cast")
                    .replace("%spell%", spell.getDisplayName());
                me.ray.midgard.core.text.MessageUtils.send(player, castMsg);
            }
        }

        return success;
    }
}
