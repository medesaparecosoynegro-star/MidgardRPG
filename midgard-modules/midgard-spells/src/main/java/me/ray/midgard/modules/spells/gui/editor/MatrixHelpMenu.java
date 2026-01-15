package me.ray.midgard.modules.spells.gui.editor;

import me.ray.midgard.core.gui.BaseGui;
import me.ray.midgard.core.utils.ItemBuilder;
import me.ray.midgard.modules.spells.SpellsModule;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MatrixHelpMenu extends BaseGui {

    private final SpellsModule module;
    private final MatrixEditorMenu editorMenu;
    private int currentPage = 0;
    
    private static final int[] TOPIC_SLOTS = {10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25};
    private static final int BACK_SLOT = 49;
    private static final int PREV_PAGE = 48;
    private static final int NEXT_PAGE = 50;

    public MatrixHelpMenu(Player player, SpellsModule module, MatrixEditorMenu editorMenu) {
        super(player, 6, module.getMessage("help.title"));
        this.module = module;
        this.editorMenu = editorMenu;
    }

    @Override
    public void initializeItems() {
        inventory.clear();
        
        // Background
        for(int i=0; i<54; i++) {
            if (!isContentSlot(i)) {
                inventory.setItem(i, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build());
            }
        }
        
        // Topics based on page
        String[] topics = {"overview", "nodes", "tools_create", "tools_link", "tools_type", 
                          "tools_delete", "workflow", "examples", "tips", "shortcuts"};
        
        int startIndex = currentPage * TOPIC_SLOTS.length;
        for (int i = 0; i < TOPIC_SLOTS.length && (startIndex + i) < topics.length; i++) {
            String topic = topics[startIndex + i];
            Material mat = getTopicMaterial(topic);
            
            ItemBuilder builder = new ItemBuilder(mat)
                .setName(module.getMessage("help.topics." + topic + ".name"));
                
            for (String line : module.getMessageList("help.topics." + topic + ".lore")) {
                builder.addLore(line);
            }
            
            inventory.setItem(TOPIC_SLOTS[i], builder.build());
        }
        
        // Navigation
        if (currentPage > 0) {
            inventory.setItem(PREV_PAGE, new ItemBuilder(Material.ARROW)
                .setName(module.getMessage("help.nav.previous"))
                .build());
        }
        
        if ((currentPage + 1) * TOPIC_SLOTS.length < topics.length) {
            inventory.setItem(NEXT_PAGE, new ItemBuilder(Material.ARROW)
                .setName(module.getMessage("help.nav.next"))
                .build());
        }
        
        // Back Button
        inventory.setItem(BACK_SLOT, new ItemBuilder(Material.BARRIER)
            .setName(module.getMessage("help.nav.back"))
            .addLore(module.getMessage("help.nav.back_desc"))
            .build());
    }
    
    private boolean isContentSlot(int slot) {
        if (slot == BACK_SLOT || slot == PREV_PAGE || slot == NEXT_PAGE) return true;
        for (int s : TOPIC_SLOTS) {
            if (s == slot) return true;
        }
        return false;
    }
    
    private Material getTopicMaterial(String topic) {
        switch (topic) {
            case "overview": return Material.BOOK;
            case "nodes": return Material.EMERALD;
            case "tools_create": return Material.LIME_DYE;
            case "tools_link": return Material.IRON_BARS;
            case "tools_type": return Material.REPEATER;
            case "tools_delete": return Material.BARRIER;
            case "workflow": return Material.COMPASS;
            case "examples": return Material.MAP;
            case "tips": return Material.LANTERN;
            case "shortcuts": return Material.COMMAND_BLOCK;
            default: return Material.PAPER;
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        int slot = event.getSlot();
        
        if (slot == BACK_SLOT) {
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
            editorMenu.open();
        } else if (slot == PREV_PAGE && currentPage > 0) {
            currentPage--;
            player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);
            initializeItems();
        } else if (slot == NEXT_PAGE) {
            currentPage++;
            player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);
            initializeItems();
        }
    }
}
