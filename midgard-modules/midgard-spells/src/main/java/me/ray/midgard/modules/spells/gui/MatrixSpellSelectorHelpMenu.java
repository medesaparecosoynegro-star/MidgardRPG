package me.ray.midgard.modules.spells.gui;

import me.ray.midgard.core.gui.BaseGui;
import me.ray.midgard.core.gui.BaseHelpMenu;
import me.ray.midgard.modules.spells.SpellsModule;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Menu de ajuda para o Seletor de Matrix de Feitiços.
 * Exibe tópicos sobre navegação, feitiços, progressão e dicas.
 */
public class MatrixSpellSelectorHelpMenu extends BaseHelpMenu {

    private final SpellsModule module;
    
    private static final String[] TOPIC_IDS = {
        "overview", "navigation", "spells", "progression", "tips"
    };

    public MatrixSpellSelectorHelpMenu(Player player, SpellsModule module, BaseGui parentMenu) {
        super(player, module.getMessage("selector_gui.buttons.help.name"), TOPIC_IDS, 0, 28, parentMenu);
        this.module = module;
    }
    
    // Construtor privado para navegação entre páginas
    private MatrixSpellSelectorHelpMenu(Player player, SpellsModule module, BaseGui parentMenu, int page) {
        super(player, module.getMessage("selector_gui.buttons.help.name"), TOPIC_IDS, page, 28, parentMenu);
        this.module = module;
    }

    @Override
    protected Material getTopicMaterial(String topicId) {
        return switch (topicId) {
            case "overview" -> Material.BOOK;
            case "navigation" -> Material.COMPASS;
            case "spells" -> Material.ENCHANTED_BOOK;
            case "progression" -> Material.EXPERIENCE_BOTTLE;
            case "tips" -> Material.LANTERN;
            default -> Material.PAPER;
        };
    }

    @Override
    protected String getTopicName(String topicId) {
        return module.getMessage("selector_gui.help." + topicId + ".name");
    }

    @Override
    protected List<String> getTopicLore(String topicId) {
        return module.getMessageList("selector_gui.help." + topicId + ".lore");
    }

    @Override
    protected BaseHelpMenu createNewPage(int newPage) {
        return new MatrixSpellSelectorHelpMenu(player, module, parentMenu, newPage);
    }
}
