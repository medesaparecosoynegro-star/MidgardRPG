package me.ray.midgard.modules.spells.gui.editor;

import me.ray.midgard.core.gui.BaseGui;
import me.ray.midgard.core.text.MessageUtils;
import me.ray.midgard.core.utils.ItemBuilder;
import me.ray.midgard.modules.spells.SpellsModule;
import me.ray.midgard.modules.spells.obj.NodeType;
import me.ray.midgard.modules.spells.obj.template.MatrixTemplate;
import me.ray.midgard.modules.spells.obj.template.MatrixTemplate.MatrixNode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NodeEditorMenu extends BaseGui {

    private final SpellsModule module;
    private final MatrixTemplate template;
    private final int slot;
    private final MatrixTemplateEditor parentGui;

    // Editable State
    private String displayName = "New Node";
    private String icon = "STONE";
    private NodeType type = NodeType.PASSIVE;
    private String mutationSkill = null;
    private int modelData = 0;
    private List<Integer> parents = new ArrayList<>();

    public NodeEditorMenu(Player player, SpellsModule module, MatrixTemplate template, MatrixNode existingNode, int slot, MatrixTemplateEditor parentGui) {
        super(player, 3, "Edit Node: Slot " + slot);
        this.module = module;
        this.template = template;
        this.slot = slot;
        this.parentGui = parentGui;

        if (existingNode != null) {
            this.displayName = existingNode.getDisplayName();
            this.icon = existingNode.getIconMaterial();
            this.type = existingNode.getType();
            this.mutationSkill = existingNode.getMutationSkill();
            this.modelData = existingNode.getCustomModelData();
            this.parents = new ArrayList<>(existingNode.getParents());
        }
    }

    @Override
    public void initializeItems() {
        inventory.clear();
        
        // Slot 10: Icon & Name
        ItemStack preview = new ItemBuilder(Material.matchMaterial(icon) != null ? Material.matchMaterial(icon) : Material.STONE)
                .name(MessageUtils.parse(displayName))
                .lore(
                    MessageUtils.parse("<gray>Click to change Name"),
                    MessageUtils.parse("<gray>Right-Click to change Icon (Material)")
                )
                .customModelData(modelData)
                .build();
        inventory.setItem(10, preview);

        // Slot 12: Type
        inventory.setItem(12, new ItemBuilder(Material.HOPPER)
                .name(MessageUtils.parse("<yellow>Type: <white>" + type.name()))
                .lore(MessageUtils.parse("<gray>Click to cycle"))
                .build());

        // Slot 14: Parents
        String parentsStr = parents.isEmpty() ? "None" : parents.stream().map(String::valueOf).collect(Collectors.joining(", "));
        inventory.setItem(14, new ItemBuilder(Material.IRON_BARS)
                .name(MessageUtils.parse("<yellow>Parents: <white>" + parentsStr))
                .lore(
                    MessageUtils.parse("<gray>Left-Click to Add Parent (ID)"),
                    MessageUtils.parse("<gray>Right-Click to Clear Parents")
                )
                .build());

        // Slot 16: Mutation Skill
        inventory.setItem(16, new ItemBuilder(Material.ENCHANTED_BOOK)
                .name(MessageUtils.parse("<yellow>Skill/Mutation: <white>" + (mutationSkill != null ? mutationSkill : "None")))
                .lore(MessageUtils.parse("<gray>Click to set MythicMobs Skill ID"))
                .build());

        // Save (26)
        inventory.setItem(26, new ItemBuilder(Material.EMERALD_BLOCK)
                .name(MessageUtils.parse("<green>Save Node"))
                .build());

        // Delete (22)
        inventory.setItem(22, new ItemBuilder(Material.REDSTONE_BLOCK)
                .name(MessageUtils.parse("<red>Delete Node"))
                .build());
                
        // Back (18)
        inventory.setItem(18, new ItemBuilder(Material.ARROW)
                .name(MessageUtils.parse("<red>Cancel"))
                .build());

        // Model Data (8) - Extra
        inventory.setItem(8, new ItemBuilder(Material.PAINTING)
             .name(MessageUtils.parse("<yellow>Model Data: <white>" + modelData))
             .lore(MessageUtils.parse("<gray>Click to set CustomModelData"))
             .build());
    }

    private void save() {
        MatrixNode newNode = new MatrixNode(type, slot, parents, mutationSkill, displayName, icon, modelData);
        template.addNode(slot, newNode);
        parentGui.open();
    }
    
    private void delete() {
        template.removeNode(slot);
        parentGui.open();
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        int clickSlot = event.getRawSlot();
        
        switch (clickSlot) {
            case 18: // Cancel
                parentGui.open();
                break;
                
            case 26: // Save
                save();
                break;
                
            case 22: // Delete
                delete();
                break;
                
            case 10: // Name/Icon
                if (event.isRightClick()) {
                    setChatInput(input -> {
                         if (Material.matchMaterial(input.toUpperCase()) != null) {
                             this.icon = input.toUpperCase();
                             open();
                         } else {
                             MessageUtils.send(player, "<red>Invalid Material.");
                             open();
                         }
                    });
                } else {
                     setChatInput(input -> {
                         this.displayName = input; // Supports colors if using MessageUtils later
                         open();
                     });
                }
                break;
                
            case 12: // Type
                int next = (type.ordinal() + 1) % NodeType.values().length;
                type = NodeType.values()[next];
                initializeItems();
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
                break;
                
            case 14: // Parents
                if (event.isRightClick()) {
                    parents.clear();
                    initializeItems();
                } else {
                    setChatInput(input -> {
                        try {
                            int pSlot = Integer.parseInt(input);
                            if (!parents.contains(pSlot) && pSlot != slot) {
                                parents.add(pSlot);
                            }
                            open();
                        } catch (NumberFormatException e) {
                            MessageUtils.send(player, "<red>Invalid number.");
                            open();
                        }
                    });
                }
                break;
            
            case 16: // Skill
                 setChatInput(input -> {
                     this.mutationSkill = input.equals("null") ? null : input;
                     open();
                 });
                 break;
                 
            case 8: // Model Data
                 setChatInput(input -> {
                     try {
                         this.modelData = Integer.parseInt(input);
                         open();
                     } catch(Exception e) { open(); }
                 });
                 break;
        }
    }

    private void setChatInput(java.util.function.Consumer<String> callback) {
        player.closeInventory();
        MessageUtils.send(player, "<green>Type value in chat:");
        module.getEditorListener().requestInput(player, callback);
    }
}
