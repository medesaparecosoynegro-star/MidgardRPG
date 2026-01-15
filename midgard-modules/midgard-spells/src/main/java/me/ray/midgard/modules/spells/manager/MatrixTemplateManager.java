package me.ray.midgard.modules.spells.manager;

import me.ray.midgard.modules.spells.SpellsModule;
import me.ray.midgard.modules.spells.obj.NodeType;
import me.ray.midgard.modules.spells.obj.template.MatrixTemplate;
import me.ray.midgard.modules.spells.obj.template.MatrixTemplate.MatrixNode;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class MatrixTemplateManager {

    private final SpellsModule module;
    private final Map<String, MatrixTemplate> templates = new HashMap<>();
    private final File templateFolder;

    public MatrixTemplateManager(SpellsModule module) {
        this.module = module;
        this.templateFolder = new File(module.getPlugin().getDataFolder(), "modules/spells/templates");
        if (!templateFolder.exists()) {
            templateFolder.mkdirs();
        }
    }
    
    public MatrixTemplate getTemplate(String id) {
        return templates.get(id);
    }
    
    public Collection<String> getTemplateIds() {
        return java.util.Collections.unmodifiableSet(templates.keySet());
    }

    public void loadTemplates() {
        createDefaultTemplates();
        templates.clear();
        File[] files = templateFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files == null) return;

        for (File file : files) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            String id = file.getName().replace(".yml", "");
            String displayName = config.getString("name", id);

            MatrixTemplate template = new MatrixTemplate(id, displayName);
            
            ConfigurationSection nodesSec = config.getConfigurationSection("nodes");
            if (nodesSec != null) {
                for (String key : nodesSec.getKeys(false)) {
                    ConfigurationSection nodeSec = nodesSec.getConfigurationSection(key);
                    try {
                        int slot = nodeSec.getInt("slot");
                        NodeType type = NodeType.valueOf(nodeSec.getString("type", "PASSIVE"));
                        List<Integer> parents = nodeSec.getIntegerList("parents");
                        String mutationSkill = nodeSec.getString("mutation-id");
                        
                        // Visuals
                        String display = nodeSec.getString("display-name");
                        String icon = nodeSec.getString("icon");
                        int model = nodeSec.getInt("model-data");
                        
                        MatrixNode node = new MatrixNode(type, slot, parents, mutationSkill, display, icon, model);
                        template.addNode(slot, node);
                    } catch (Exception e) {
                        module.getPlugin().getLogger().warning("Error loading template node " + key + " in " + id);
                    }
                }
            }
            templates.put(id, template);
        }
        module.getPlugin().getLogger().info("Loaded " + templates.size() + " matrix templates.");
    }

    public void saveTemplate(MatrixTemplate template) {
        templates.put(template.getId(), template); // Ensure it's in memory
        File file = new File(templateFolder, template.getId() + ".yml");
        YamlConfiguration config = new YamlConfiguration();
        
        config.set("name", template.getDisplayName());
        
        for (MatrixNode node : template.getNodes().values()) {
            String key = "node_" + node.getSlot();
            config.set("nodes." + key + ".slot", node.getSlot());
            config.set("nodes." + key + ".type", node.getType().name());
            config.set("nodes." + key + ".parents", node.getParents());
            if (node.getMutationSkill() != null) {
                config.set("nodes." + key + ".mutation-id", node.getMutationSkill());
            }
            if (node.getDisplayName() != null) config.set("nodes." + key + ".display-name", node.getDisplayName());
            if (node.getIconMaterial() != null) config.set("nodes." + key + ".icon", node.getIconMaterial());
            config.set("nodes." + key + ".model-data", node.getCustomModelData());
        }
        
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDefaultTemplates() {
        if (templateFolder.listFiles().length > 0) return;

        // Create a default Pyromancy Template
        MatrixTemplate pyro = new MatrixTemplate("pyromancy", "Pyromancy Tree");
        
        // Root
        pyro.addNode(0, new MatrixNode(NodeType.ROOT, 4, null, null, "<red>Pyromancy Base", "FIRE_CHARGE", 0));
        
        // Level 1 Branches (Slots 11, 13, 15 for example in a 5x9 or similar grid? Depends on GUI layout)
        // Assuming Standard Chest GUI (9 cols). 
        // Root at Row 0, Col 4 (Index 4).
        // Let's go down. Row 1: Index 13 (Center), 12 (Left), 14 (Right)
        
        // Example: Split into Damage vs Range
        // Left (Damage)
        pyro.addNode(13, new MatrixNode(NodeType.PASSIVE, 13, List.of(4), null, "<gold>Ignite", "BLAZE_POWDER", 0));
        
        // Mutation (Explosive vs DoT)
        pyro.addNode(22, new MatrixNode(NodeType.MUTATION, 22, List.of(13), "MM_Skill_Explosion", "<dark_red>Explosion", "TNT", 0));
        
        saveTemplate(pyro);
        module.getPlugin().getLogger().info("Created default template: pyromancy.yml");
    }
}
