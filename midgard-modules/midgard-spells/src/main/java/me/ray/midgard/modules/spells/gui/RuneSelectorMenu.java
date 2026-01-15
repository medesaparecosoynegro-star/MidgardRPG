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
    public void onItemClick(SpellRune rune) {
        // ...
    }
}
