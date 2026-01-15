package me.ray.midgard.modules.spells.gui;

import me.ray.midgard.core.gui.PaginatedGui;
import me.ray.midgard.core.text.MessageUtils;
import me.ray.midgard.modules.spells.data.MatrixState;
import me.ray.midgard.modules.spells.data.SpellProfile;
import me.ray.midgard.modules.spells.manager.SpellManager;
import me.ray.midgard.modules.spells.obj.Spell;
import me.ray.midgard.modules.spells.obj.SpellNode;
import me.ray.midgard.modules.spells.obj.SpellRune;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class RuneSelectorMenu extends PaginatedGui<SpellRune> {

    private final Spell spell;
    private final SpellNode node;
    private final SpellManager spellManager;
    private final MatrixMenu parentMenu; // Para o botão de voltar

    public RuneSelectorMenu(Player player, Spell spell, SpellNode node, SpellManager spellManager, MatrixMenu parentMenu) {
        super(player, "Select a Rune", new ArrayList<>(spellManager.getRunes()));
        
        this.spell = spell;
        this.node = node;
        this.spellManager = spellManager;
        this.parentMenu = parentMenu;
    }
    
    // Workaround removido, método getRunes adicionado ao Manager.


    @Override
    public ItemStack createItem(SpellRune rune) {
        Material mat = Material.matchMaterial(rune.getMaterial());
        if (mat == null) mat = Material.FLINT;
        
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(MessageUtils.parse("<green>" + rune.getName()));
            meta.setCustomModelData(rune.getModelData());
            List<net.kyori.adventure.text.Component> lore = new ArrayList<>();
            lore.add(MessageUtils.parse("<gray>" + rune.getDescription())); 
            lore.add(MessageUtils.parse(""));
            rune.getStats().forEach((k, v) -> lore.add(MessageUtils.parse("<gray>" + k + ": <white>" + v)));
            lore.add(MessageUtils.parse(""));
            lore.add(MessageUtils.parse("<yellow>Click to equip!"));
            meta.lore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    @Override
    public void onItemClick(Player player, int slot) {
        // Encontrar a runa clicada
        int startIndex = page * maxItemsPerPage;
        int currentSlotIndex = 0;
        SpellRune clickedRune = null;
        
        // Simular o loop do PaginatedGui para achar o item
        int itemLoopIndex = startIndex;
        outer:
        for (int row = 1; row < 4; row++) {
            for (int col = 1; col < 8; col++) {
                if (itemLoopIndex >= items.size()) break outer;
                
                int s = row * 9 + col;
                if (s == slot) {
                    clickedRune = items.get(itemLoopIndex);
                    break outer;
                }
                itemLoopIndex++;
            }
        }
        
        if (clickedRune != null) {
            SpellProfile profile = spellManager.getProfile(player);
            if (profile != null) {
                MatrixState state = profile.getMatrixState(spell.getId());
                
                // Remover runa antiga do inventário e devolver? (Lógica de jogo)
                // Por enquanto apenas substitui no soquete
                state.setRune(node.getSlot(), clickedRune.getId());
                
                MessageUtils.send(player, "<green>Rune " + clickedRune.getName() + " equipped!");
                player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_FRAME_FILL, 1, 1);
                
                // Voltar para o menu principal
                player.closeInventory(); // PaginatedGui não tem back fácil, reabrimos o parent
                parentMenu.initializeItems(); // Atualiza estado
                parentMenu.open();
            }
        }
    }
}
