package me.ray.midgard.modules.spells.gui;

import me.ray.midgard.core.text.MessageUtils;
import me.ray.midgard.modules.spells.obj.NodeType;
import me.ray.midgard.modules.spells.obj.template.MatrixTemplate.MatrixNode;
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
    public static ItemStack buildNodeIcon(MatrixNode node, boolean isUnlocked, boolean isActive, boolean canUnlock, String socketItemName) {
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
                    if (meta != null) meta.displayName(MessageUtils.parse("<green>✦ " + socketItemName));
                    lore.add("<gray>Runa equipada.");
                    lore.add("<gray>Clique para remover.");
                } else {
                    // Vazio
                    item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                    meta = item.getItemMeta();
                    if (meta != null) meta.displayName(MessageUtils.parse("<gray>○ Engaste Vazio"));
                    lore.add("<gray>Clique para equipar uma runa.");
                }
            } else {
                // É um Talento (Root, Mutation, Passive)
                Material mat = Material.matchMaterial(node.getIconMaterial());
                if (mat == null) mat = Material.NETHER_STAR;
                item = new ItemStack(mat);
                meta = item.getItemMeta();
                
                if (meta != null) {
                    meta.setCustomModelData(node.getCustomModelData());
                    meta.displayName(MessageUtils.parse("<green>" + node.getDisplayName()));
                }

                if (isActive) {
                    lore.add("<green>✔ Ativo");
                    item.addUnsafeEnchantment(Enchantment.UNBREAKING, 1);
                    if (meta != null) meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                } else {
                    lore.add("<yellow>⚠ Inativo (Clique para ativar)");
                }
            }
        }

        if (meta == null) return item;
        
        // Finalizar Lore (adicionar lore customizada do node se houver, aqui simplified)
        
        // Converter List<String> para List<Component>
        List<Component> componentLore = lore.stream()
            .map(MessageUtils::parse)
            .collect(Collectors.toList());
        
        meta.lore(componentLore);
        item.setItemMeta(meta);
        
        return item;
    }
}
