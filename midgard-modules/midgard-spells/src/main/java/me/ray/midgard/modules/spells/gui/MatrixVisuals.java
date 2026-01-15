package me.ray.midgard.modules.spells.gui;

import me.ray.midgard.core.text.MessageUtils;
import me.ray.midgard.modules.spells.obj.NodeType;
import me.ray.midgard.modules.spells.obj.SpellNode;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MatrixVisuals {

    @SuppressWarnings("deprecation")
    public static ItemStack buildNodeIcon(SpellNode node, boolean isUnlocked, boolean isActive, boolean canUnlock, String socketItemName) {
        ItemStack item;
        ItemMeta meta;
        List<String> lore = new ArrayList<>();

        // 1. Determinar o Material Base e o Modelo
        if (!isUnlocked) {
            // BLOQUEADO OU DISPONÍVEL
            if (canUnlock) {
                // Disponível para compra
                item = new ItemStack(Material.LIME_STAINED_GLASS_PANE); // Representa "Aberto/Permitido"
                meta = item.getItemMeta();
                if (meta != null) meta.displayName(MessageUtils.parse("<green>🔓 Desbloquear: " + node.getDisplayName()));
                lore.add("<gray>Clique para aprender este talento.");
                lore.add("");
                lore.add("<yellow>Custo: <white>5 Pontos de Talento"); // TODO: Integrar sistema de custo real
            } else {
                // Bloqueado totalmente
                item = new ItemStack(Material.RED_STAINED_GLASS_PANE); // Representa "Fechado"
                meta = item.getItemMeta();
                if (meta != null) meta.displayName(MessageUtils.parse("<red>🔒 Bloqueado"));
                lore.add("<gray>Requer conexão anterior.");
            }
        } else {
            // DESBLOQUEADO (Já possui)
            
            if (node.getType() == NodeType.SOCKET) {
                // É um Soquete (Slot de Runa)
                if (socketItemName != null) {
                    // Tem runa dentro
                    item = new ItemStack(Material.EMERALD); // Placeholder, idealmente seria dynamic
                    meta = item.getItemMeta();
                    if (meta != null) meta.displayName(MessageUtils.parse("<green>♦ " + socketItemName));
                    lore.add("<gray>Runa equipada.");
                    lore.add("<yellow>Clique para remover ou trocar.");
                    // Brilho para indicar poder
                    if (meta != null) {
                        meta.addEnchant(Enchantment.UNBREAKING, 1, true);
                        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    }
                } else {
                    // Soquete Vazio
                    item = new ItemStack(Material.IRON_NUGGET); // Use CustomModelData para parecer um anel vazio
                    meta = item.getItemMeta();
                    if (meta != null) meta.displayName(MessageUtils.parse("<dark_gray>○ Soquete Vazio"));
                    lore.add("<gray>Espaço para runas de poder.");
                    lore.add("<yellow>Clique para inserir uma runa.");
                }
            } else {
                // É uma Mutação/Habilidade
                Material iconMat = Material.BOOK;
                try {
                    if (node.getIcon() != null) {
                        iconMat = Material.valueOf(node.getIcon().toUpperCase());
                    }
                } catch (IllegalArgumentException ignored) {}
                
                item = new ItemStack(iconMat);
                meta = item.getItemMeta();
                 
                if (isActive) {
                    if (meta != null) {
                        meta.displayName(MessageUtils.parse("<green><bold>★ " + node.getDisplayName()));
                        lore.add("<green>✔ Habilidade Ativa");
                        lore.add("<gray>Esta modificação está aplicada.");
                        
                        // O "Glint" (Brilho) é crucial para mostrar atividade
                        meta.addEnchant(Enchantment.UNBREAKING, 1, true);
                        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    }
                } else {
                    if (meta != null) {
                        meta.displayName(MessageUtils.parse("<gray>" + node.getDisplayName()));
                        lore.add("<dark_gray>Desativado");
                        lore.add("<yellow>Clique para ativar.");
                    }
                }
            }
        }

        if (meta != null) {
            // Aplicar Lore e CustomModelData
            if (node.getModelData() > 0 && isUnlocked) {
                meta.setCustomModelData(node.getModelData());
            }
            
            meta.lore(lore.stream().map(MessageUtils::parse).collect(Collectors.toList()));
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES); // Limpar visual sujo
            item.setItemMeta(meta);
        }

        return item;
    }
    
    public static ItemStack getBackground() {
        ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) meta.displayName(Component.text(" "));
        item.setItemMeta(meta);
        return item;
    }
    
    @SuppressWarnings("deprecation")
    public static ItemStack getConnectionLine(boolean unlocked) {
        ItemStack item = new ItemStack(unlocked ? Material.WHITE_STAINED_GLASS_PANE : Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(Component.text(" "));
            // Se quiser usar model data para criar "fios" ou "tubos"
            meta.setCustomModelData(101); 
        }
        item.setItemMeta(meta);
        return item;
    }
}
