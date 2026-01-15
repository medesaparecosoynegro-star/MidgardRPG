package me.ray.midgard.modules.spells.manager;

import io.lumine.mythic.api.MythicProvider;
import io.lumine.mythic.api.skills.Skill;
import io.lumine.mythic.bukkit.MythicBukkit;
import me.ray.midgard.core.MidgardCore;
import me.ray.midgard.core.profile.MidgardProfile;
import me.ray.midgard.modules.spells.SpellsModule;
import me.ray.midgard.modules.spells.data.MatrixState;
import me.ray.midgard.modules.spells.data.SpellProfile;
import me.ray.midgard.modules.spells.obj.NodeType;
import me.ray.midgard.modules.spells.obj.Spell;
import me.ray.midgard.modules.spells.obj.SpellNode;
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
    private final Map<String, Spell> loadedSpells = new HashMap<>();
    private final Map<String, SpellRune> loadedRunes = new HashMap<>();
    private final Set<UUID> castingModePlayers = new HashSet<>();

    private final Map<UUID, Integer> castingAnchors = new HashMap<>();

    private final Map<Integer, String> defaultCombos = new HashMap<>();

    public SpellManager(SpellsModule module) {
        this.module = module;
        loadDefaultCombos();
    }

    private void loadDefaultCombos() {
        // Defaults if config is missing (Plain L/R without hyphens to match Listener)
        defaultCombos.put(1, "LLL");
        defaultCombos.put(2, "RRR");
        defaultCombos.put(3, "LRL");
        defaultCombos.put(4, "RLR");
        defaultCombos.put(5, "LLR");
        defaultCombos.put(6, "RRL");
        
        // Load from module config if available
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

    @SuppressWarnings("deprecation")
    public void enableCastingMode(Player player) {
        castingModePlayers.add(player.getUniqueId());
        
        // Define Anchor Slot (Current Slot becomes the "Void")
        // Everything to the right of this slot is shifted +1 visuals
        int anchor = player.getInventory().getHeldItemSlot(); 
        castingAnchors.put(player.getUniqueId(), anchor);
        
        player.setMetadata("midgard_casting_mode", new org.bukkit.metadata.FixedMetadataValue(module.getPlugin(), true));
        player.playSound(player.getLocation(), org.bukkit.Sound.BLOCK_IRON_TRAPDOOR_OPEN, 1f, 1.5f);
        
        String enableMsg = module.getMessage("casting.mode_enabled");
        me.ray.midgard.core.text.MessageUtils.send(player, enableMsg);
    }

    public void disableCastingMode(Player player) {
        castingModePlayers.remove(player.getUniqueId());
        castingAnchors.remove(player.getUniqueId()); // Limpa anchor
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
        
        // Use getVirtualSkillId logic here? Or parameter is already virtual?
        // Parameter 'slot' comes from listener which usually sends raw slot (1-9)
        // But we want to abstract this. Let's create a helper method.
        
        String spellId = profile.getSkillInSlot(slot);
        if (spellId == null) return;
        
        castSpell(player, spellId);
    }
    
    // Retorna o ID da skill que estaria no slot visualizado, considerando o deslocamento do Anchor
    public String getSkillInVirtualSlot(Player player, int visualSlotIndex) {
        // visualSlotIndex: 0-8 (Hotbar index)
        SpellProfile profile = getProfile(player);
        if (profile == null) return null;

        Integer anchor = castingAnchors.get(player.getUniqueId());
        if (anchor == null) {
            // Se não tem anchor, retorna normal
            return profile.getSkillInSlot(visualSlotIndex + 1);
        }

        if (visualSlotIndex == anchor) {
            return null; // O slot âncora é sempre vazio/buraco
        }

        // Se o slot visualizado é maior que o âncora, shift right
        if (visualSlotIndex > anchor) {
            // Ex: Anchor 0. Query 1.
            // Skill que deveria estar aqui é a do índice (1-1) = 0.
            int originalIndex = visualSlotIndex - 1;
            return profile.getSkillInSlot(originalIndex + 1); // +1 because profile is 1-based
        }

        // Se o slot visualizado é menor que o âncora, mantém original
        // Ex: Anchor 2. Query 1. Skill Index 1.
        return profile.getSkillInSlot(visualSlotIndex + 1);
    }

    public void loadRunes() {
        loadedRunes.clear();
        // TODO: Load from files in modules/spells/runes
        
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
            
            // Load Matrix
            Map<Integer, SpellNode> layout = new HashMap<>();
            ConfigurationSection matrixSec = config.getConfigurationSection("matrix");
            if (matrixSec != null) {
                for (String key : matrixSec.getKeys(false)) {
                    ConfigurationSection nodeSec = matrixSec.getConfigurationSection(key);
                    try {
                        NodeType type = NodeType.valueOf(nodeSec.getString("type"));
                        int slot = nodeSec.getInt("slot");
                        List<Integer> parents = nodeSec.getIntegerList("parents");
                        String mutId = nodeSec.getString("mutation-id");
                        String display = nodeSec.getString("display-name");
                        String icon = nodeSec.getString("icon");
                        int model = nodeSec.getInt("model-data");
                        
                        layout.put(slot, new SpellNode(type, slot, parents, mutId, display, icon, model));
                    } catch (Exception e) {
                        module.getPlugin().getLogger().warning("Error loading node " + key + " in spell " + id + ": " + e.getMessage());
                    }
                }
            }

            Spell spell = new Spell(id, mythicSkill, name, lore, cooldown, mana, stamina, layout);
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
        MidgardProfile coreProfile = MidgardCore.getProfileManager().getProfile(player.getUniqueId());
        if (coreProfile == null) return null; 
        return coreProfile.getOrCreateData(SpellProfile.class);
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
        String skillName = spell.getMythicSkillName();
        
        // Resolve Mutation
        for (int slot : matrix.getActiveMutations()) {
            SpellNode node = spell.getNode(slot);
            if (node != null && node.getType() == NodeType.MUTATION && node.getMutationId() != null) {
                skillName = node.getMutationId();
            }
        }
        
        // Validate Skill
        Optional<Skill> mythicSkill = MythicProvider.get().getSkillManager().getSkill(skillName);
        if (mythicSkill.isEmpty()) {
            String errorMsg = module.getMessage("errors.config_error")
                .replace("%skill%", skillName);
            me.ray.midgard.core.text.MessageUtils.send(player, errorMsg);
            return false;
        }

        // 2. Calculate Rune Variables (Math Injection)
        Map<String, Double> runtimeVariables = new HashMap<>();
        
        // Base variables (can be config driven too, e.g. from Spell obj)
        runtimeVariables.put("damage_bonus", 0.0);
        runtimeVariables.put("size_bonus", 0.0);
        runtimeVariables.put("duration_bonus", 0.0);

        // Sum up all stats from equipped runes
        for (String runeId : matrix.getSocketRunes().values()) {
            SpellRune rune = getRune(runeId);
            if (rune != null) {
                rune.getStats().forEach((key, value) -> 
                    runtimeVariables.merge(key, value, Double::sum)
                );
            }
        }

        // 3. Inject into MythicMobs Caster Scope
        // Scope CASTER persists until cleared or overwritten. 
        // Ideal is to use 'Skill Scope' if we could pass Metadata, but setting on Caster is standard for simpler API use.
        AbstractEntity abstractPlayer = BukkitAdapter.adapt(player);
        VariableRegistry registry = MythicBukkit.inst().getVariableManager().getRegistry(VariableScope.CASTER, abstractPlayer);
        
        for (Map.Entry<String, Double> entry : runtimeVariables.entrySet()) {
            // Ex: <caster.var.damage_bonus>
            registry.putFloat(entry.getKey(), entry.getValue().floatValue());
        }

        // Cast
        boolean success = MythicBukkit.inst().getAPIHelper().castSkill(player, skillName);
        if (success) {
            // Execute Extra Matrix Skills
            for (int slot : matrix.getUnlockedNodes()) {
                 SpellNode node = spell.getNode(slot);
                 if (node != null && node.getType() == NodeType.MYTHIC_SKILL && node.getMutationId() != null) {
                      MythicBukkit.inst().getAPIHelper().castSkill(player, node.getMutationId());
                 }
            }

            profile.setCooldown(spellId, spell.getCooldown());
            if (module.getConfig().getBoolean("general.show_cast_messages", true)) {
                String castMsg = module.getMessage("casting.spell_cast")
                    .replace("%spell%", spell.getDisplayName());
                me.ray.midgard.core.text.MessageUtils.send(player, castMsg);
            }
        }
        
        // Cleanup? 
        // Usually fine to leave variables as they are scoped to "last cast state" or overwrite next time.
        // If we want to be safe, we can clear them later, but that might clear variables for other skills running async.
        // A unique prefix like "spell_midgard_var_" is safer.

        return success;
    }
}
