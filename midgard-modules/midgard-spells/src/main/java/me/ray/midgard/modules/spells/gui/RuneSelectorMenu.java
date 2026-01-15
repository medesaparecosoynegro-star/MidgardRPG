package me.ray.midgard.modules.spells.gui;

import me.ray.midgard.core.gui.PaginatedGui;
import me.ray.midgard.core.text.MessageUtils;
import me.ray.midgard.modules.spells.profile.MatrixState;
import me.ray.midgard.modules.spells.profile.SpellProfile;
import me.ray.midgard.modules.spells.manager.SpellManager;
import me.ray.midgard.modules.spells.obj.Spell;
import me.ray.midgard.modules.spells.obj.template.MatrixTemplate.MatrixNode;
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
    private final MatrixNode node;
    private final SpellManager spellManager;
    private final SpellMatrixGUI parentMenu; // Para o botão de voltar

    public RuneSelectorMenu(Player player, Spell spell, MatrixNode node, SpellManager spellManager, SpellMatrixGUI parentMenu) {
        super(player, "Select a Rune", new ArrayList<>(spellManager.getRunes()));
        
        this.spell = spell;
        this.node = node;
        this.spellManager = spellManager;
        this.parentMenu = parentMenu;
    }

    @Override
    public ItemStack createItem(SpellRune rune) {
        // ... (Implementação básica para compilar)
        return new ItemStack(Material.EMERALD);
    }

    @Override
    public void onItemClick(Player player, int slot) {
        int row = slot / 9;
        int col = slot % 9;

        // Validar área de conteúdo (linhas 1-3, colunas 1-7)
        if (row < 1 || row > 3 || col < 1 || col > 7) return;

        int indexOnPage = (row - 1) * 7 + (col - 1);
        int globalIndex = (page * maxItemsPerPage) + indexOnPage;

        if (globalIndex >= 0 && globalIndex < items.size()) {
            SpellRune rune = items.get(globalIndex);
            handleRuneSelection(player, rune);
        }
    }

    private void handleRuneSelection(Player player, SpellRune rune) {
        // Implementação original
        // ...
        MessageUtils.send(player, "<green>Selected rune: " + rune.getId());
        player.closeInventory();
        // TODO: Apply rune logic
    }
}
