package me.ray.midgard.modules.spells.obj.template;

import me.ray.midgard.modules.spells.obj.NodeType;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatrixTemplate {

    private final String id;
    private final String displayName;
    // Slot -> Node
    private final Map<Integer, MatrixNode> nodes = new HashMap<>();

    public MatrixTemplate(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Map<Integer, MatrixNode> getNodes() {
        return Collections.unmodifiableMap(nodes);
    }

    public void addNode(int slot, MatrixNode node) {
        nodes.put(slot, node);
    }
    
    public void removeNode(int slot) {
        nodes.remove(slot);
    }
    
    public MatrixNode getNode(int slot) {
        return nodes.get(slot);
    }

    public static class MatrixNode {
        private final NodeType type;
        private final int slot;
        private final List<Integer> parents;
        
        // Dados de Mutação
        private final String mutationSkill; // O antigo mutationId
        
        // Dados Visuais
        private final String displayName;
        private final String iconMaterial;
        private final int customModelData;

        public MatrixNode(NodeType type, int slot, List<Integer> parents, String mutationSkill, String displayName, String iconMaterial, int customModelData) {
            this.type = type;
            this.slot = slot;
            this.parents = parents != null ? parents : Collections.emptyList();
            this.mutationSkill = mutationSkill;
            this.displayName = displayName;
            this.iconMaterial = iconMaterial;
            this.customModelData = customModelData;
        }

        public NodeType getType() { return type; }
        public int getSlot() { return slot; }
        public List<Integer> getParents() { return parents; }
        public String getMutationSkill() { return mutationSkill; }
        public String getDisplayName() { return displayName; }
        public String getIconMaterial() { return iconMaterial; }
        public int getCustomModelData() { return customModelData; }
    }
}
