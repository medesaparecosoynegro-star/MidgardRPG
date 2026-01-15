package me.ray.midgard.modules.spells.obj;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Spell {

    private final String id;
    private final String mythicSkillName;
    private final String displayName;
    private final List<String> lore;
    private final double cooldown; // seconds
    private final double manaCost;
    private final double staminaCost;
    
    // Matriz de Habilidade (Layout)
    private final Map<Integer, SpellNode> matrixLayout;

    public Spell(String id, String mythicSkillName, String displayName, List<String> lore, double cooldown, double manaCost, double staminaCost, Map<Integer, SpellNode> matrixLayout) {
        this.id = id;
        this.mythicSkillName = mythicSkillName;
        this.displayName = displayName;
        this.lore = lore;
        this.cooldown = cooldown;
        this.manaCost = manaCost;
        this.staminaCost = staminaCost;
        this.matrixLayout = matrixLayout != null ? matrixLayout : Collections.emptyMap();
    }
    
    // Construtor legado para compatibilidade temporária
    public Spell(String id, String mythicSkillName, String displayName, List<String> lore, double cooldown, double manaCost, double staminaCost) {
        this(id, mythicSkillName, displayName, lore, cooldown, manaCost, staminaCost, Collections.emptyMap());
    }

    public String getId() {
        return id;
    }

    public String getMythicSkillName() {
        return mythicSkillName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getLore() {
        return lore;
    }

    public Map<Integer, SpellNode> getLayout() {
        return matrixLayout;
    }

    public double getCooldown() {
        return cooldown;
    }

    public double getManaCost() {
        return manaCost;
    }

    public double getStaminaCost() {
        return staminaCost;
    }
    
    public Map<Integer, SpellNode> getMatrixLayout() {
        return matrixLayout;
    }
    
    public SpellNode getNode(int slot) {
        return matrixLayout.get(slot);
    }
}
