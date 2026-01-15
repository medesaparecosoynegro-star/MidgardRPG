package me.ray.midgard.modules.spells.gui.editor;

import me.ray.midgard.core.gui.BaseGui;
import me.ray.midgard.core.text.MessageUtils;
import me.ray.midgard.core.utils.ItemBuilder;
import me.ray.midgard.modules.spells.SpellsModule;
import me.ray.midgard.modules.spells.obj.NodeType;
import me.ray.midgard.modules.spells.obj.template.MatrixTemplate;
import me.ray.midgard.modules.spells.obj.template.MatrixTemplate.MatrixNode;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MatrixTemplateEditor extends BaseGui {

    private final SpellsModule module;
    private final MatrixTemplate template;

    public MatrixTemplateEditor(Player player, SpellsModule module, MatrixTemplate template) {
        super(player, 6, "Editor: <dark_aqua>" + template.getDisplayName());
        this.module = module;
        this.template = template;
    }

    @Override
    public void initializeItems() {
        inventory.clear();

        // Grid Area (Rows 1-5, slots 0-44)
        for (int i = 0; i < 45; i++) {
            MatrixNode node = template.getNode(i);
            if (node != null) {
                inventory.setItem(i, createNodeItem(node));
            } else {
                inventory.setItem(i, new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                        .name(MessageUtils.parse(" "))
                        .lore(MessageUtils.parse("<gray>Click to add node"))
                        .build());
            }
        }

        // Toolbar (Row 6)
        fillToolbar();
    }

    private ItemStack createNodeItem(MatrixNode node) {
        Material mat = Material.matchMaterial(node.getIconMaterial());
        if (mat == null) mat = Material.STONE;

        String typeColor = switch (node.getType()) {
            case ROOT -> "<gold>";
            case MUTATION -> "<red>";
            case PASSIVE -> "<blue>";
            case SOCKET -> "<green>";
            default -> "<gray>";
        };

        List<Component> lore = new ArrayList<>();
        lore.add(MessageUtils.parse("<gray>Type: " + typeColor + node.getType().name()));
        lore.add(MessageUtils.parse("<gray>Slot: <white>" + node.getSlot()));
        lore.add(Component.empty());
        lore.add(MessageUtils.parse("<yellow>Parents:"));
        if (node.getParents().isEmpty()) {
            lore.add(MessageUtils.parse(" <dark_gray>- None"));
        } else {
            for (int p : node.getParents()) {
                lore.add(MessageUtils.parse(" <gray>- Slot " + p));
            }
        }
        
        if (node.getType() == NodeType.MUTATION || node.getType() == NodeType.MYTHIC_SKILL) {
            lore.add(Component.empty());
            lore.add(MessageUtils.parse("<red>Skill: <white>" + (node.getMutationSkill() != null ? node.getMutationSkill() : "None")));
        }

        lore.add(Component.empty());
        lore.add(MessageUtils.parse("<yellow>Click to Edit"));

        return new ItemBuilder(mat)
                .name(MessageUtils.parse(typeColor + (node.getDisplayName() != null ? node.getDisplayName() : "Node")))
                .lore(lore)
                .customModelData(node.getCustomModelData())
                .build();
    }

    private void fillToolbar() {
        // Background
        ItemStack filler = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name(MessageUtils.parse(" ")).build();
        for (int i = 45; i < 54; i++) {
            inventory.setItem(i, filler);
        }

        // Save Button (49)
        inventory.setItem(49, new ItemBuilder(Material.EMERALD_BLOCK)
                .name(MessageUtils.parse("<green>Save & Exit"))
                .lore(MessageUtils.parse("<gray>Save changes to file"))
                .build());
                
        // Refresh (53)
        inventory.setItem(53, new ItemBuilder(Material.COMPASS)
                .name(MessageUtils.parse("<yellow>Reload"))
                .lore(MessageUtils.parse("<gray>Reload view"))
                .build());
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        int slot = event.getRawSlot();

        if (slot < 45) {
            // Node Area
            MatrixNode node = template.getNode(slot);
            new NodeEditorMenu(player, module, template, node, slot, this).open();
        } else if (slot == 49) {
            // Save
            module.getTemplateManager().saveTemplate(template);
            MessageUtils.send(player, "<green>Template saved successfully!");
            player.closeInventory();
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
        } else if (slot == 53) {
            initializeItems();
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
        }
    }
}
