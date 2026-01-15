package me.ray.midgard.modules.spells.gui;

import me.ray.midgard.core.gui.BaseGui;
import me.ray.midgard.core.gui.GuiUtils;
import me.ray.midgard.core.text.MessageUtils;
import me.ray.midgard.core.utils.ItemBuilder;
import me.ray.midgard.modules.spells.SpellsModule;
import me.ray.midgard.modules.spells.data.MatrixState;
import me.ray.midgard.modules.spells.data.SpellProfile;
import me.ray.midgard.modules.spells.obj.NodeType;
import me.ray.midgard.modules.spells.obj.Spell;
import me.ray.midgard.modules.spells.obj.SpellNode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Menu de seleção de feitiços para edição de Matrix.
 * Modernizado com sistema de ajuda interativo e estatísticas detalhadas.
 */
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
            if (index >= 45) break; // Máximo 45 slots para feitiços
            
            String spellId = spell.getId();
            MatrixState matrixState = profile.getMatrixState(spellId);
            
            // Calcular estatísticas
            final int totalNodes = spell.getMatrixLayout().size();
            final int unlockedNodes = calculateUnlockedNodes(spell, matrixState);
            final int totalSockets = calculateTotalSockets(spell);
            final int filledSockets = calculateFilledSockets(spell, matrixState);
            final int activeMutations = matrixState.getActiveMutations().size();
            
            // Criar item
            List<String> lore = module.getMessageList("selector_gui.spell_icon.lore");
            lore = lore.stream()
                .map(line -> line
                    .replace("%spell_id%", spellId)
                    .replace("%spell_name%", spell.getDisplayName())
                    .replace("%unlocked_nodes%", String.valueOf(unlockedNodes))
                    .replace("%total_nodes%", String.valueOf(totalNodes))
                    .replace("%active_mutations%", String.valueOf(activeMutations))
                    .replace("%filled_sockets%", String.valueOf(filledSockets))
                    .replace("%total_sockets%", String.valueOf(totalSockets)))
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

        // Botões de controle
        addControlButtons();
    }
    
    /**
     * Adiciona botões de controle (back, help).
     */
    private void addControlButtons() {
        inventory.setItem(BACK_SLOT, GuiUtils.createBackButton());
        inventory.setItem(HELP_SLOT, GuiUtils.createHelpButton());
    }
    
    /**
     * Verifica se um nó está desbloqueado (simplificado).
     */
    private boolean isNodeUnlocked(Spell spell, MatrixState matrixState, SpellNode node) {
        if (node.getType() == NodeType.ROOT) return true;
        if (node.getParentSlots().isEmpty()) return true;
        
        for (int parentSlot : node.getParentSlots()) {
            SpellNode parent = spell.getNode(parentSlot);
            if (parent == null) continue;
            
            if (parent.getType() == NodeType.ROOT) return true;
            if (parent.getType() == NodeType.MUTATION && matrixState.getActiveMutations().contains(parentSlot)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Calcula quantos nós estão desbloqueados.
     */
    private int calculateUnlockedNodes(Spell spell, MatrixState matrixState) {
        int count = 0;
        for (SpellNode node : spell.getMatrixLayout().values()) {
            if (node.getType() == NodeType.ROOT || 
                (node.getParentSlots().isEmpty() && node.getType() != NodeType.CONNECTOR) ||
                isNodeUnlocked(spell, matrixState, node)) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Calcula o total de sockets no feitiço.
     */
    private int calculateTotalSockets(Spell spell) {
        int count = 0;
        for (SpellNode node : spell.getMatrixLayout().values()) {
            if (node.getType() == NodeType.SOCKET) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Calcula quantos sockets estão preenchidos.
     */
    private int calculateFilledSockets(Spell spell, MatrixState matrixState) {
        int count = 0;
        for (SpellNode node : spell.getMatrixLayout().values()) {
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

        // Botão Voltar
        if (slot == BACK_SLOT) {
            new MainSpellGUI(player, module).open();
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
            return;
        }
        
        // Botão Ajuda
        if (slot == HELP_SLOT) {
            new MatrixSpellSelectorHelpMenu(player, module, this).open();
            player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1f, 1f);
            return;
        }

        // Seleção de Feitiço
        if (slot < 45 && inventory.getItem(slot) != null) {
            List<Spell> spells = new ArrayList<>(module.getSpellManager().getSpells());
            if (slot < spells.size()) {
                Spell selectedSpell = spells.get(slot);
                
                MessageUtils.send(player, module.getMessage("selector_gui.messages.spell_selected")
                    .replace("%spell_name%", selectedSpell.getDisplayName()));
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
                
                new SpellMatrixGUI(player, module, selectedSpell.getId()).open();
            }
        }
    }
}
