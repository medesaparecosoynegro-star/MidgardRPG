package me.ray.midgard.modules.spells.obj;

import java.util.Collections;
import java.util.List;

public class SpellNode {

    private final NodeType type;
    private final int slot; // 0-53 do inventário
    private final List<Integer> parentSlots; // Quais slots precisam estar ativos para liberar este?
    
    // Configurações Específicas
    private final String mutationId; // Para NodeType.MUTATION: Qual MythicSkill executar?
    private final String displayName; // Nome visível no menu
    private final String iconMaterial; 
    private final int customModelData;

    public SpellNode(NodeType type, int slot, List<Integer> parentSlots, String mutationId, String displayName, String iconMaterial, int customModelData) {
        this.type = type;
        this.slot = slot;
        this.parentSlots = parentSlots != null ? parentSlots : Collections.emptyList();
        this.mutationId = mutationId;
        this.displayName = displayName;
        this.iconMaterial = iconMaterial;
        this.customModelData = customModelData;
    }

    public NodeType getType() {
        return type;
    }

    public int getSlot() {
        return slot;
    }

    public List<Integer> getParents() {
        return parentSlots;
    }

    public List<Integer> getParentSlots() {
        return parentSlots;
    }
    
    public String getMutationId() {
        return mutationId;
    }
    
    public String getDisplayName() {
        return displayName;
    }

    public String getIcon() {
        return iconMaterial;
    }

    public String getIconMaterial() {
        return iconMaterial;
    }

    public int getModelData() {
        return customModelData;
    }

    public int getCustomModelData() {
        return customModelData;
    }
}
