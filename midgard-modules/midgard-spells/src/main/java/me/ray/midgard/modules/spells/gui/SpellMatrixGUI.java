package me.ray.midgard.modules.spells.gui;

import me.ray.midgard.core.gui.BaseGui;
import me.ray.midgard.core.gui.GuiUtils;
import me.ray.midgard.core.gui.VisualState;
import me.ray.midgard.core.text.MessageUtils;
import me.ray.midgard.core.utils.ItemBuilder;
import me.ray.midgard.modules.spells.SpellsModule;
import me.ray.midgard.modules.spells.profile.MatrixState;
import me.ray.midgard.modules.spells.profile.SpellProfile;
import me.ray.midgard.modules.spells.manager.SpellManager;
import me.ray.midgard.modules.spells.obj.NodeType;
import me.ray.midgard.modules.spells.obj.Spell;
import me.ray.midgard.modules.spells.obj.template.MatrixTemplate;
import me.ray.midgard.modules.spells.obj.template.MatrixTemplate.MatrixNode;
import me.ray.midgard.modules.spells.obj.SpellRune;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Menu da Matrix de Talentos - Visualização e customização da árvore de progressão.
 * Modernizado com sistema de ajuda interativo, lores detalhadas e feedback visual.
 */
public class SpellMatrixGUI extends BaseGui {

    private final Spell spell;
    private final MatrixTemplate template;
    private final SpellManager manager;
    private final SpellsModule module;
    private final MatrixState matrixState;
    
    // Slots de controle
    private static final int BACK_SLOT = 45;
    private static final int INFO_SLOT = 48;
    private static final int HELP_SLOT = 49;
    private static final int RESET_SLOT = 53;

    public SpellMatrixGUI(Player player, SpellsModule module, String spellId) {
        super(player, 6, module.getMessage("matrix_gui.title")
            .replace("%spell_name%", module.getSpellManager().getSpell(spellId).getDisplayName()));
        this.module = module;
        this.manager = module.getSpellManager();
        this.spell = manager.getSpell(spellId);
        
        // Load Template
        String templateId = spell.getMatrixTemplateId();
        if (templateId != null) {
            this.template = module.getTemplateManager().getTemplate(templateId);
        } else {
            this.template = null;
        }
        
        SpellProfile profile = manager.getProfile(player);
        this.matrixState = profile.getMatrixState(spellId);
    }

    @Override
    public void initializeItems() {
        // Preencher fundo
        ItemStack filler = GuiUtils.createFiller();
        for (int i = 0; i < 54; i++) {
            inventory.setItem(i, filler);
        }
        
        if (template == null) {
            inventory.setItem(22, GuiUtils.createStateIndicator(VisualState.ERROR, "Error", "No Matrix Template found for this spell."));
            addControlButtons();
            return;
        }
        
        // Renderizar nós da matrix
        for (Map.Entry<Integer, MatrixNode> entry : template.getNodes().entrySet()) {
            int slot = entry.getKey();
            MatrixNode node = entry.getValue();
            
            if (!isNodeUnlocked(node)) {
                renderLockedNode(slot, node);
                continue;
            }

            switch (node.getType()) {
                case ROOT:
                case MUTATION:
                case PASSIVE:
                    renderMutation(slot, node);
                    break;
                case SOCKET:
                    renderSocket(slot, node);
                    break;
                case CONNECTOR:
                    renderConnector(slot, node);
                    break;
            }
        }
        
        // Adicionar botões de controle
        addControlButtons();
    }
    
    /**
     * Adiciona botões de controle (back, info, help, reset).
     */
    private void addControlButtons() {
        // Botão Voltar
        inventory.setItem(BACK_SLOT, GuiUtils.createBackButton());
        
        // Botão Info
        List<String> infoLore = module.getMessageList("matrix_gui.buttons.info.lore");
        ItemStack info = GuiUtils.createStateIndicator(
            VisualState.INFO,
            module.getMessage("matrix_gui.buttons.info.name"),
            infoLore.toArray(new String[0])
        );
        inventory.setItem(INFO_SLOT, info);
        
        // Botão Ajuda
        inventory.setItem(HELP_SLOT, GuiUtils.createHelpButton());
        
        // Botão Reset
        List<String> resetLore = module.getMessageList("matrix_gui.buttons.reset.lore");
        ItemStack reset = GuiUtils.createStateIndicator(
            VisualState.ERROR,
            module.getMessage("matrix_gui.buttons.reset.name"),
            resetLore.toArray(new String[0])
        );
        inventory.setItem(RESET_SLOT, reset);
    }

    /**
     * Verifica se um nó está desbloqueado.
     * Um nó está desbloqueado se:
     * - É do tipo ROOT, ou
     * - Não tem pais (nó órfão), ou
     * - Pelo menos um dos pais está ativo
     */
    private boolean isNodeUnlocked(MatrixNode node) {
        if (node.getType() == NodeType.ROOT) return true;
        if (node.getParents().isEmpty()) return true;
        
        for (int parentSlot : node.getParents()) {
             MatrixNode parent = template.getNode(parentSlot);
             if (parent == null) continue;
             
             if (parent.getType() == NodeType.ROOT) return true;
             
             if (parent.getType() == NodeType.MUTATION) {
                 if (matrixState.getActiveMutations().contains(parentSlot)) return true;
             }
        }
        return false;
    }
    
    /**
     * Renderiza um nó bloqueado.
     */
    private void renderLockedNode(int slot, MatrixNode node) {
        List<String> lore = module.getMessageList("matrix_gui.nodes.locked.lore");
        ItemStack locked = GuiUtils.createStateIndicator(
            VisualState.LOCKED,
            module.getMessage("matrix_gui.nodes.locked.name"),
            lore.toArray(new String[0])
        );
        inventory.setItem(slot, locked);
    }

    /**
     * Renderiza um nó de mutação.
     */
    private void renderMutation(int slot, MatrixNode node) {
        boolean isRoot = node.getType() == NodeType.ROOT;
        boolean isActive = matrixState.getActiveMutations().contains(slot) || isRoot;
        
        String messageKey;
        if (isRoot) {
            messageKey = "matrix_gui.nodes.root";
        } else if (isActive) {
            messageKey = "matrix_gui.nodes.mutation_active";
        } else {
            messageKey = "matrix_gui.nodes.mutation_available";
        }
        
        String name = module.getMessage(messageKey + ".name")
            .replace("%node_name%", node.getDisplayName());
        
        List<String> lore = module.getMessageList(messageKey + ".lore");
        lore = lore.stream()
            .map(line -> line.replace("%node_name%", node.getDisplayName()))
            .toList();
        
        Material material = Material.matchMaterial(node.getIconMaterial());
        if (material == null) material = Material.NETHER_STAR;
        
        ItemBuilder builder = new ItemBuilder(material)
            .customModelData(node.getCustomModelData())
            .setName(name);
        
        if (isActive) {
            builder.glow();
        }
        
        for (String line : lore) {
            builder.addLore(line);
        }
        
        inventory.setItem(slot, builder.build());
    }

    /**
     * Renderiza um socket (engaste de runa).
     */
    private void renderSocket(int slot, MatrixNode node) {
        String runeId = matrixState.getRune(slot);
        
        if (runeId != null) {
            SpellRune rune = manager.getRune(runeId);
            if (rune != null) {
                String name = module.getMessage("matrix_gui.nodes.socket_filled.name")
                    .replace("%rune_name%", rune.getDisplayName());
                
                List<String> lore = module.getMessageList("matrix_gui.nodes.socket_filled.lore");
                
                // Construir stats da runa
                StringBuilder stats = new StringBuilder();
                String statFormat = module.getMessage("matrix_gui.nodes.socket_filled.stat_format");
                rune.getStats().forEach((k, v) -> {
                    if (stats.length() > 0) stats.append("\n");
                    stats.append(statFormat.replace("%key%", k).replace("%value%", v.toString()));
                });
                
                lore = lore.stream()
                    .map(line -> line
                        .replace("%rune_name%", rune.getDisplayName())
                        .replace("%rune_stats%", stats.toString()))
                    .toList();
                
                Material material = Material.matchMaterial(rune.getMaterial());
                if (material == null) material = Material.EMERALD;
                
                ItemBuilder builder = new ItemBuilder(material)
                    .setName(name)
                    .customModelData(rune.getModelData());
                
                for (String line : lore) {
                    builder.addLore(line);
                }
                
                inventory.setItem(slot, builder.glow().build());
            } else {
                inventory.setItem(slot, GuiUtils.createStateIndicator(
                    VisualState.ERROR,
                    module.getMessage("matrix_gui.messages.rune_error"),
                    module.getMessage("matrix_gui.messages.rune_invalid_id").replace("%id%", runeId)
                ));
            }
        } else {
            List<String> lore = module.getMessageList("matrix_gui.nodes.socket_empty.lore");
            ItemStack empty = GuiUtils.createStateIndicator(
                VisualState.INFO,
                module.getMessage("matrix_gui.nodes.socket_empty.name"),
                lore.toArray(new String[0])
            );
            inventory.setItem(slot, empty);
        }
    }

    /**
     * Renderiza um conector (nó visual de caminho).
     */
    private void renderConnector(int slot, MatrixNode node) {
        ItemStack connector = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE)
            .setName(module.getMessage("matrix_gui.nodes.connector.name"))
            .customModelData(node.getCustomModelData())
            .build();
        inventory.setItem(slot, connector);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        
        if (event.getClickedInventory() != inventory) return;
        
        int slot = event.getSlot();
        
        // Botões de controle
        if (slot == BACK_SLOT) {
            new MatrixSpellSelectorGUI(player, module).open();
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
            return;
        }
        
        if (slot == INFO_SLOT) {
            return; // Apenas informativo
        }
        
        if (slot == HELP_SLOT) {
            new SpellMatrixHelpMenu(player, module, this).open();
            player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1f, 1f);
            return;
        }
        
        if (slot == RESET_SLOT) {
            handleReset();
            return;
        }
        
        // Cliques em nós da matrix
        if (template == null) return;
        MatrixNode node = template.getNode(slot);
        if (node == null) return;
        
        if (!isNodeUnlocked(node)) {
            MessageUtils.send(player, module.getMessage("matrix_gui.messages.node_locked"));
            player.playSound(player.getLocation(), Sound.BLOCK_CHEST_LOCKED, 1f, 1f);
            return;
        }

        switch (node.getType()) {
            case MUTATION:
            case PASSIVE:
                handleMutationClick(slot, node);
                break;
            case SOCKET:
                handleSocketClick(event, slot, node);
                break;
            case ROOT:
            case CONNECTOR:
            default:
                break;
        }
    }
    
    /**
     * Trata clique em mutação - ativa ou desativa.
     */
    private void handleMutationClick(int slot, MatrixNode node) {
        if (matrixState.getActiveMutations().contains(slot)) {
            matrixState.deactivateMutation(slot);
            MessageUtils.send(player, module.getMessage("matrix_gui.messages.mutation_deactivated")
                .replace("%node_name%", node.getDisplayName()));
            player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1f, 1f);
        } else {
            matrixState.activateMutation(slot);
            MessageUtils.send(player, module.getMessage("matrix_gui.messages.mutation_activated")
                .replace("%node_name%", node.getDisplayName()));
            player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1f, 1f);
        }
        initializeItems();
    }
    
    /**
     * Trata reset completo da matrix.
     */
    private void handleReset() {
        if (template == null) return;
        
        // Desativar todas as mutações
        for (int mutationSlot : new ArrayList<>(matrixState.getActiveMutations())) {
            matrixState.deactivateMutation(mutationSlot);
        }
        
        // Remover todas as runas (devolver ao inventário)
        for (Map.Entry<Integer, MatrixNode> entry : template.getNodes().entrySet()) {
            if (entry.getValue().getType() == NodeType.SOCKET) {
                String runeId = matrixState.getRune(entry.getKey());
                if (runeId != null) {
                    SpellRune rune = manager.getRune(runeId);
                    if (rune != null) {
                        Material material = Material.matchMaterial(rune.getMaterial());
                        if (material == null) material = Material.EMERALD;
                        ItemStack item = new ItemBuilder(material)
                            .setName(rune.getDisplayName())
                            .customModelData(rune.getModelData())
                            .build();
                        player.getInventory().addItem(item);
                    }
                    matrixState.removeRune(entry.getKey());
                }
            }
        }
        
        MessageUtils.send(player, module.getMessage("matrix_gui.messages.matrix_reset"));
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1f, 1f);
        initializeItems();
    }

    /**
     * Trata clique em socket - remove ou equipa runa.
     */
    private void handleSocketClick(InventoryClickEvent event, int slot, MatrixNode node) {
        String existingRune = matrixState.getRune(slot);
        ItemStack cursor = event.getCursor();
        
        if (existingRune != null) {
            // Remover runa existente
            SpellRune rune = manager.getRune(existingRune);
            if (rune != null) {
                Material material = Material.matchMaterial(rune.getMaterial());
                if (material == null) material = Material.EMERALD;
                
                ItemStack item = new ItemBuilder(material)
                    .setName(rune.getDisplayName())
                    .customModelData(rune.getModelData())
                    .build();
                
                if (player.getInventory().firstEmpty() == -1) {
                    MessageUtils.send(player, module.getMessage("matrix_gui.messages.inventory_full"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                    return;
                }
                
                matrixState.removeRune(slot);
                player.getInventory().addItem(item);
                MessageUtils.send(player, module.getMessage("matrix_gui.messages.socket_removed")
                    .replace("%rune_name%", rune.getDisplayName()));
                player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 1f, 1f);
            }
            initializeItems();
        } else {
            // Equipar nova runa
            if (cursor != null && cursor.getType() != Material.AIR) {
                // Validação simplificada - assumindo esmeraldas como runas para MVP
                // Em produção, usar PDC para identificar runas
                String runeId = "jade_rune"; // Mock ID
                if (cursor.getType().name().contains("EMERALD")) {
                    matrixState.setRune(slot, runeId);
                    cursor.setAmount(cursor.getAmount() - 1);
                    
                    SpellRune rune = manager.getRune(runeId);
                    if (rune != null) {
                        MessageUtils.send(player, module.getMessage("matrix_gui.messages.socket_equipped")
                            .replace("%rune_name%", rune.getDisplayName()));
                    }
                    
                    player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_FRAME_FILL, 1f, 1f);
                    initializeItems();
                } else {
                    MessageUtils.send(player, module.getMessage("matrix_gui.messages.invalid_rune"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                }
            }
        }
    }
}
