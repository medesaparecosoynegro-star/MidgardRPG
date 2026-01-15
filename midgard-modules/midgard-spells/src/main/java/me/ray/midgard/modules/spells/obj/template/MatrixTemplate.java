package me.ray.midgard.modules.spells.obj.template;

import me.ray.midgard.modules.spells.obj.NodeType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatrixTemplate {

    private final String id;
    private final String displayName;
    // Slot -> Template Node Data
    private final Map<Integer, TemplateNode> nodes = new HashMap<>();

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

    public Map<Integer, TemplateNode> getNodes() {
        return nodes;
    }

    public void addNode(int slot, TemplateNode node) {
        nodes.put(slot, node);
    }
    
    public TemplateNode getNode(int slot) {
        return nodes.get(slot);
    }

    public static class TemplateNode {
        private NodeType type;
        private final int slot;
        private List<Integer> parents = new ArrayList<>();
        private String extraData; // Stores mutation ID or skill name
        
        // Default visual properties (can be overridden by spell)
        private String defaultIcon;
        private String defaultName;

        public TemplateNode(NodeType type, int slot) {
            this.type = type;
            this.slot = slot;
            this.defaultIcon = (type == NodeType.SOCKET) ? "IRON_NUGGET" : "BOOK"; 
            this.defaultName = (type == NodeType.SOCKET) ? "Socket" : "Talent";
        }

        public NodeType getType() {
            return type;
        }

        public void setType(NodeType type) {
            this.type = type;
        }

        public int getSlot() {
            return slot;
        }

        public List<Integer> getParents() {
            return parents;
        }
        
        public void setParents(List<Integer> parents) {
            this.parents = parents;
        }
        
        public String getDefaultIcon() {
            return defaultIcon;
        }
        
        public String getDefaultName() {
            return defaultName;
        }

        public String getExtraData() {
            return extraData;
        }

        public void setExtraData(String extraData) {
            this.extraData = extraData;
        }
    }
}
