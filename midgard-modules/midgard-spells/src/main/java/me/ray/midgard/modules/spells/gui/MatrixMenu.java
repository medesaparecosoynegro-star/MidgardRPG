package me.ray.midgard.modules.spells.gui;

import me.ray.midgard.core.gui.BaseGui;
import me.ray.midgard.core.text.MessageUtils;
import me.ray.midgard.modules.spells.data.MatrixState;
import me.ray.midgard.modules.spells.data.SpellProfile;
import me.ray.midgard.modules.spells.manager.SpellManager;
import me.ray.midgard.modules.spells.obj.NodeType;
import me.ray.midgard.modules.spells.obj.Spell;
import me.ray.midgard.modules.spells.obj.SpellNode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MatrixMenu extends BaseGui {

    private final Spell spell;
    private final SpellManager spellManager;

    public MatrixMenu(Player player, Spell spell, SpellManager spellManager) {
        super(player, 6, "Matrix: " + spell.getDisplayName());
        this.spell = spell;
        this.spellManager = spellManager;
    }

    @Override
    public void initializeItems() {
        inventory.clear();
        SpellProfile profile = spellManager.getProfile(player);
        if (profile == null) return;
        
        MatrixState state = profile.getMatrixState(spell.getId());

        // Preenchimento de Fundo (Background 54 slots)
        ItemStack bg = MatrixVisuals.getBackground();
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, bg);
        }

        // Renderiza os Nós
        for (SpellNode node : spell.getLayout().values()) {
            if (node.getSlot() >= inventory.getSize()) continue;

            boolean isUnlocked = state.isUnlocked(node.getSlot());
            boolean canUnlock = canUnlockNode(state, node);
            boolean isActive = state.getActiveMutations().contains(node.getSlot());
            
            String socketItemName = null;
            if (isUnlocked && node.getType() == NodeType.SOCKET) {
                String runeId = state.getSocketRunes().get(node.getSlot());
                if (runeId != null) {
                    var rune = spellManager.getRune(runeId);
                    if (rune != null) socketItemName = rune.getName();
                }
            }

            ItemStack icon = MatrixVisuals.buildNodeIcon(node, isUnlocked, isActive, canUnlock, socketItemName);
            inventory.setItem(node.getSlot(), icon);
            
            // Renderização simples das conexões (apenas para pais em slots adjacentes)
            // Para uma renderização complexa de linhas, seria necessário um algoritmo de pathfinding na grade
            // Aqui vamos assumir que o layout visual cuida disso ou simplificar
        }
    }

    private boolean canUnlockNode(MatrixState state, SpellNode node) {
        if (state.isUnlocked(node.getSlot())) return false; // Já desbloqueado
        if (node.getParents() == null || node.getParents().isEmpty()) return true; // É raiz
        
        for (int parentSlot : node.getParents()) {
            // Lógica OR: Se tiver QUALQUER pai desbloqueado, pode liberar este
            // Se a lógica do seu jogo for AND (precisa de todos), mude aqui.
            if (state.isUnlocked(parentSlot)) return true;
        }
        return false;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        
        int slot = event.getSlot();
        if (!spell.getLayout().containsKey(slot)) return;
        
        SpellNode node = spell.getLayout().get(slot);
        SpellProfile profile = spellManager.getProfile(player);
        MatrixState state = profile.getMatrixState(spell.getId());
        
        boolean isUnlocked = state.isUnlocked(slot);
        
        if (!isUnlocked) {
            // Tentar Desbloquear
            if (canUnlockNode(state, node)) {
                // TODO: Implementar Custo (XP / Moeda)
                // Por enquanto, desbloqueio grátis
                state.unlockNode(slot);
                player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_HIT, 1, 1.5f);
                // Salvar perfil? spellManager.saveProfile(player);
                initializeItems(); // Re-render
            } else {
                player.playSound(player.getLocation(), Sound.BLOCK_CHEST_LOCKED, 1, 0.5f);
                MessageUtils.send(player, "<red>Você precisa desbloquear o nó anterior primeiro!");
            }
            return;
        }
        
        // Interação com Nó Desbloqueado
        if (node.getType() == NodeType.MUTATION) {
            if (state.getActiveMutations().contains(slot)) {
                state.deactivateMutation(slot);
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 0.8f);
            } else {
                state.activateMutation(slot);
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1.5f);
            }
            initializeItems();
        } 
        else if (node.getType() == NodeType.SOCKET) {
             // Abrir Seletor de Runas
             player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
             new RuneSelectorMenu(player, spell, node, spellManager, this).open();
        }
    }
}
