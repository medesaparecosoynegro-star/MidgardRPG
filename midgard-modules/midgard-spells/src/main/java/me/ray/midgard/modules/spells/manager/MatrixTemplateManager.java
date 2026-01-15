package me.ray.midgard.modules.spells.manager;

import me.ray.midgard.modules.spells.SpellsModule;
import me.ray.midgard.modules.spells.obj.NodeType;
import me.ray.midgard.modules.spells.obj.template.MatrixTemplate;
import me.ray.midgard.modules.spells.obj.template.MatrixTemplate.TemplateNode;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

    public void loadTemplates() {
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
                        
                        TemplateNode node = new TemplateNode(type, slot);
                        node.setParents(nodeSec.getIntegerList("parents"));
                        node.setExtraData(nodeSec.getString("mutation-id"));
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
        File file = new File(templateFolder, template.getId() + ".yml");
        YamlConfiguration config = new YamlConfiguration();
        
        config.set("name", template.getDisplayName());
        
        for (TemplateNode node : template.getNodes().values()) {
            String key = "node_" + node.getSlot();
            config.set("nodes." + key + ".slot", node.getSlot());
            config.set("nodes." + key + ".type", node.getType().name());
            config.set("nodes." + key + ".parents", node.getParents());
            if (node.getExtraData() != null) {
                config.set("nodes." + key + ".mutation-id", node.getExtraData());
            }
        }
        
        try {
            config.save(file);
            templates.put(template.getId(), template);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public MatrixTemplate getTemplate(String id) {
        return templates.get(id);
    }
    
    public Collection<String> getTemplateIds() {
        return templates.keySet();
    }
}
