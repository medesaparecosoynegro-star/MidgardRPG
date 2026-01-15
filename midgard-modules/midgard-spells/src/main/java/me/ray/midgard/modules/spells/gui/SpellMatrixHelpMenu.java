package me.ray.midgard.modules.spells.gui;

import me.ray.midgard.core.gui.BaseGui;
import me.ray.midgard.core.gui.BaseHelpMenu;
import me.ray.midgard.modules.spells.SpellsModule;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Menu de ajuda interativo para o sistema Matrix de talentos.
 * Explica mutações, sockets, desbloqueio de nós e estratégias de build.
 */
public class SpellMatrixHelpMenu extends BaseHelpMenu {
    
    private final SpellsModule module;
    
    private static final String[] TOPICS = {
        "overview",
        "nodes",
        "unlocking",
        "mutations",
        "sockets",
        "strategy",
        "tips"
    };
    
    public SpellMatrixHelpMenu(Player player, SpellsModule module, BaseGui parentMenu) {
        super(player,
              module.getMessage("matrix_gui.help.title"),
              TOPICS,
              0,
              28,
              parentMenu);
        this.module = module;
    }
    
    private SpellMatrixHelpMenu(Player player, SpellsModule module, BaseGui parentMenu, int page) {
        super(player,
              module.getMessage("matrix_gui.help.title"),
              TOPICS,
              page,
              28,
              parentMenu);
        this.module = module;
    }
    
    @Override
    protected Material getTopicMaterial(String topicId) {
        return switch (topicId) {
            case "overview" -> Material.BOOK;
            case "nodes" -> Material.NETHER_STAR;
            case "unlocking" -> Material.TRIPWIRE_HOOK;
            case "mutations" -> Material.BLAZE_POWDER;
            case "sockets" -> Material.EMERALD;
            case "strategy" -> Material.ENCHANTED_BOOK;
            case "tips" -> Material.LANTERN;
            default -> Material.PAPER;
        };
    }
    
    @Override
    protected String getTopicName(String topicId) {
        return module.getMessage("matrix_gui.help.topics." + topicId + ".name");
    }
    
    @Override
    protected List<String> getTopicLore(String topicId) {
        return module.getMessageList("matrix_gui.help.topics." + topicId + ".lore");
    }
    
    @Override
    protected BaseHelpMenu createNewPage(int page) {
        return new SpellMatrixHelpMenu(player, module, parentMenu, page);
    }
}
