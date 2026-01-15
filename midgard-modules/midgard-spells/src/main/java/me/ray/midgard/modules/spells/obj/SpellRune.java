package me.ray.midgard.modules.spells.obj;

import java.util.Collections;
import java.util.Map;

public class SpellRune {

    private final String id;
    private final String displayName;
    private final String description;
    private final String material;
    private final int modelData;
    private final Map<String, Double> stats; 
    // Ex: "damage" -> 2.0, "mana_reduction" -> 0.1 (10%), "radius" -> 1.5

    public SpellRune(String id, String displayName, String description, String material, int modelData, Map<String, Double> stats) {
        this.id = id;
        this.displayName = displayName;
        this.description = description != null ? description : "";
        this.material = material;
        this.modelData = modelData;
        this.stats = stats != null ? stats : Collections.emptyMap();
    }
    
    // Construtor legado para compatibilidade temporária
    public SpellRune(String id, String displayName, String material, int modelData, Map<String, Double> stats) {
        this(id, displayName, "", material, modelData, stats);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public String getMaterial() {
        return material;
    }
    
    public int getModelData() {
        return modelData;
    }

    public Map<String, Double> getStats() {
        return stats;
    }

    public double getStat(String key) {
        return stats.getOrDefault(key, 0.0);
    }
}
