package me.ray.midgard.modules.spells.gui;

import me.ray.midgard.core.gui.BaseGui;
import me.ray.midgard.core.gui.GuiUtils;
import me.ray.midgard.core.text.MessageUtils;
import me.ray.midgard.core.utils.ItemBuilder;
import me.ray.midgard.modules.spells.SpellsModule;
import me.ray.midgard.modules.spells.profile.MatrixState;
import me.ray.midgard.modules.spells.profile.SpellProfile;
import me.ray.midgard.modules.spells.obj.NodeType;
import me.ray.midgard.modules.spells.obj.Spell;
import me.ray.midgard.modules.spells.obj.template.MatrixTemplate;
import me.ray.midgard.modules.spells.obj.template.MatrixTemplate.MatrixNode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MatrixSpellSelectorGUI extends BaseGui {

    private final SpellsModule module;
    
    // Slots de controle
    private static final int BACK_SLOT = 45;
    private static final int HELP_SLOT = 49;

    public MatrixSpellSelectorGUI(Player player, SpellsModule module) {
        super(player, 6, module.getMessage("selector_gui.title"));
        this.module = module;
    }

    @Override
    public void initializeItems() {
        // Preencher fundo
        ItemStack filler = GuiUtils.createFiller();
        for (int i = 0; i < 54; i++) {
            inventory.setItem(i, filler);
        }
        
        List<Spell> spells = new ArrayList<>(module.getSpellManager().getSpells());
        
        if (spells.isEmpty()) {
            MessageUtils.send(player, module.getMessage("selector_gui.messages.no_spells"));
            return;
        }

        SpellProfile profile = module.getSpellManager().getProfile(player);
        
        int index = 0;
        for (Spell spell : spells) {
            if (index >= 45) break; 
            
            String spellId = spell.getId();
            MatrixState matrixState = profile.getMatrixState(spellId);
            
            // Get Template
            String templateId = spell.getMatrixTemplateId();
            MatrixTemplate template = null;
            if (templateId != null) {
                template = module.getTemplateManager().getTemplate(templateId);
            }

            // Calculate Stats
            int totalNodes = 0;
            int unlockedNodes = 0;
            int totalSockets = 0;
            int filledSockets = 0;
            int activeMutations = matrixState.getActiveMutations().size();

            if (template != null) {
                totalNodes = template.getNodes().size();
                unlockedNodes = calculateUnlockedNodes(template, matrixState);
                totalSockets = calculateTotalSockets(template);
                filledSockets = calculateFilledSockets(template, matrixState);
            }
            
            // Create Item
            List<String> lore = module.getMessageList("selector_gui.spell_icon.lore");
            int finalUnlockedNodes = unlockedNodes;
            int finalTotalNodes = totalNodes;
            int finalFilledSockets = filledSockets;
            int finalTotalSockets = totalSockets;
            
            lore = lore.stream()
                .map(line -> line
                    .replace("%spell_id%", spellId)
                    .replace("%spell_name%", spell.getDisplayName())
                    .replace("%unlocked_nodes%", String.valueOf(finalUnlockedNodes))
                    .replace("%total_nodes%", String.valueOf(finalTotalNodes))
                    .replace("%active_mutations%", String.valueOf(activeMutations))
                    .replace("%filled_sockets%", String.valueOf(finalFilledSockets))
                    .replace("%total_sockets%", String.valueOf(finalTotalSockets)))
                .toList();
            
            ItemBuilder builder = new ItemBuilder(Material.ENCHANTED_BOOK)
                .setName(module.getMessage("selector_gui.spell_icon.name")
                    .replace("%spell_name%", spell.getDisplayName()));
            
            for (String line : lore) {
                builder.addLore(line);
            }
            
            inventory.setItem(index, builder.build());
            index++;
        }

        addControlButtons();
    }
    
    private void addControlButtons() {
        inventory.setItem(BACK_SLOT, GuiUtils.createBackButton());
        inventory.setItem(HELP_SLOT, GuiUtils.createHelpButton());
    }
    
    private boolean isNodeUnlocked(MatrixTemplate template, MatrixState matrixState, MatrixNode node) {
        if (node.getType() == NodeType.ROOT) return true;
        if (node.getParents().isEmpty()) return true;
        
        for (int parentSlot : node.getParents()) {
            MatrixNode parent = template.getNode(parentSlot);
            if (parent == null) continue;
            
            if (parent.getType() == NodeType.ROOT) return true;
            if (parent.getType() == NodeType.MUTATION && matrixState.getActiveMutations().contains(parentSlot)) {
                return true;
            }
        }
        return false;
    }
    
    private int calculateUnlockedNodes(MatrixTemplate template, MatrixState matrixState) {
        int count = 0;
        for (MatrixNode node : template.getNodes().values()) {
            if (node.getType() == NodeType.ROOT || 
                (node.getParents().isEmpty() && node.getType() != NodeType.CONNECTOR) ||
                isNodeUnlocked(template, matrixState, node)) {
                count++;
            }
        }
        return count;
    }
    
    private int calculateTotalSockets(MatrixTemplate template) {
        int count = 0;
        for (MatrixNode node : template.getNodes().values()) {
            if (node.getType() == NodeType.SOCKET) {
                count++;
            }
        }
        return count;
    }
    
    private int calculateFilledSockets(MatrixTemplate template, MatrixState matrixState) {
        int count = 0;
        for (MatrixNode node : template.getNodes().values()) {
            if (node.getType() == NodeType.SOCKET) {
                if (matrixState.getRune(node.getSlot()) != null) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        if (event.getClickedInventory() != inventory) return;
        
        int slot = event.getSlot();

        if (slot == BACK_SLOT) {
            new MainSpellGUI(player, module).open();
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
            return;
        }
        
        if (slot == HELP_SLOT) {
            new MatrixSpellSelectorHelpMenu(player, module, this).open();
            player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1f, 1f);
            return;
        }

        if (slot < 45 && inventory.getItem(slot) != null) {
            List<Spell> spells = new ArrayList<>(module.getSpellManager().getSpells());
            if (slot < spells.size()) {
                Spell selectedSpell = spells.get(slot);
                // Open Matrix GUI
                new SpellMatrixGUI(player, module, selectedSpell.getId()).open();
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
            }
        }
    }
}
