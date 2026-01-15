package me.ray.midgard.modules.spells.gui.editor;

import me.ray.midgard.core.gui.BaseGui;
import me.ray.midgard.core.text.MessageUtils;
import me.ray.midgard.core.utils.ItemBuilder;
import me.ray.midgard.modules.spells.SpellsModule;
import me.ray.midgard.modules.spells.manager.MatrixTemplateManager;
import me.ray.midgard.modules.spells.obj.NodeType;
import me.ray.midgard.modules.spells.obj.template.MatrixTemplate;
import me.ray.midgard.modules.spells.obj.template.MatrixTemplate.TemplateNode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class MatrixEditorMenu extends BaseGui {

    private final SpellsModule module;
    private final MatrixTemplateManager manager;
    private final String templateId;
    
    // Estado de Edição
    private MatrixTemplate template; // Objeto sendo editado (em memória)
    private Integer selectedParentSlot = null; // Para ferramenta de Link
    private final Map<Integer, Integer> suggestionSlots = new HashMap<>(); // SlotSugestao -> SlotPai
    
    // Tools
    private static final int TOOL_CREATE_SLOT = 45;
    private static final int TOOL_LINK_SLOT = 46;
    private static final int TOOL_TYPE_SLOT = 47;
    private static final int TOOL_RANDOM_SLOT = 48;
    private static final int ACTION_SAVE_SLOT = 50;
    private static final int TOOL_DELETE_SLOT = 53;

    public MatrixEditorMenu(Player player, SpellsModule module, String templateId) {
        super(player, 6, module.getMessage("editor.title").replace("%id%", templateId));
        this.module = module;
        this.manager = module.getTemplateManager();
        this.templateId = templateId;
        
        // Carrega ou cria novo
        this.template = manager.getTemplate(templateId);
        if (this.template == null) {
            this.template = new MatrixTemplate(templateId, templateId);
        }
    }
    @Override
    public void initializeItems() {
        suggestionSlots.clear();
        
        // 1. Calcular Sugestões
        if (template.getNodes().isEmpty() && currentTool.equals("CREATE")) {
             suggestionSlots.put(22, null);
        }
        for (TemplateNode node : template.getNodes().values()) {
            if (currentTool.equals("CREATE")) {
                calculateSuggestions(node.getSlot());
            }
        }
        
        // 2. Renderizar Sugestões (Fantasmas)
        if (currentTool.equals("CREATE")) {
            for (Map.Entry<Integer, Integer> entry : suggestionSlots.entrySet()) {
                int slot = entry.getKey();
                if (template.getNode(slot) != null) continue; // Já existe
                
                ItemBuilder ghost = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE)
                    .setName("§a§l+ Click to Expand")
                    .addLore("§7Create new node here");
                
                if (entry.getValue() != null) {
                    ghost.addLore("§7Auto-link from slot " + entry.getValue());
                } else {
                    ghost.addLore("§eStart Root");
                }
                
                inventory.setItem(slot, ghost.build());
            }
        }
        
        // 3. Renderizar Nós
        for (TemplateNode node : template.getNodes().values()) {
            if (node.getSlot() >= inventory.getSize()) continue;
            
            Material mat = (node.getType() == NodeType.SOCKET) ? Material.EMERALD : Material.BOOK;
            if (node.getType() == NodeType.MUTATION) mat = Material.NETHER_STAR;
            if (node.getType() == NodeType.MYTHIC_SKILL) mat = Material.COMMAND_BLOCK;
            
            String typeDisplay = "";
            String typeCycle = "";
            switch (node.getType()) {
                case SOCKET: 
                    typeDisplay = module.getMessage("editor.node.types.socket"); 
                    typeCycle = module.getMessage("editor.node.cycle.socket");
                    break;
                case MUTATION: 
                    typeDisplay = module.getMessage("editor.node.types.mutation"); 
                    typeCycle = module.getMessage("editor.node.cycle.mutation");
                    break;
                case MYTHIC_SKILL: 
                    typeDisplay = module.getMessage("editor.node.types.mythic_skill"); 
                    typeCycle = module.getMessage("editor.node.cycle.mythic_skill");
                    break;
                case PASSIVE: 
                    typeDisplay = module.getMessage("editor.node.types.passive"); 
                    typeCycle = module.getMessage("editor.node.cycle.passive");
                    break;
                case ROOT:
                    typeDisplay = module.getMessage("editor.node.types.root"); 
                    typeCycle = module.getMessage("editor.node.cycle.root");
                    break;
                case CONNECTOR:
                    typeDisplay = module.getMessage("editor.node.types.connector");
                    typeCycle = module.getMessage("editor.node.cycle.connector");
                    break;
            }

            ItemBuilder builder = new ItemBuilder(mat)
                    .setName(module.getMessage("editor.node.name").replace("%slot%", String.valueOf(node.getSlot())));
                    
            for (String line : module.getMessageList("editor.node.lore")) {
                builder.addLore(line
                   .replace("%type%", typeDisplay)
                   .replace("%count%", String.valueOf(node.getParents().size()))
                   .replace("%parents%", node.getParents().toString()));
            }

            if (node.getExtraData() != null) {
                for (String line : module.getMessageList("editor.node.skill_display")) {
                    builder.addLore(line.replace("%id%", node.getExtraData()));
                }
            }
            if (node.getType() == NodeType.MUTATION || node.getType() == NodeType.MYTHIC_SKILL) {
                 builder.addLore(module.getMessage("editor.node.edit_hint"));
            }
            
            builder.addLore("");
            builder.addLore(module.getMessage("editor.node.cycle.title"));
            builder.addLore(typeCycle);
            builder.addLore(module.getMessage("editor.node.cycle.click_hint"));

            if (selectedParentSlot != null && selectedParentSlot == node.getSlot()) {
                builder.glow();
                for (String line : module.getMessageList("editor.node.selected")) {
                    builder.addLore(line);
                }
            }
            
            inventory.setItem(node.getSlot(), builder.build());
        }
        
        // Background linha 6
        for(int i=45; i<54; i++) {
            inventory.setItem(i, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build());
        }
        
        // --- TOOLS ---
        createToolItem(45, Material.LIME_DYE, "editor.tools.create", currentTool.equals("CREATE"));
        createToolItem(46, Material.IRON_BARS, "editor.tools.link", currentTool.equals("LINK"));
        createToolItem(47, Material.REPEATER, "editor.tools.type", currentTool.equals("TYPE"));
        createToolItem(48, Material.END_CRYSTAL, "editor.tools.random", false);
        createToolItem(49, Material.STRUCTURE_VOID, "editor.tools.info", false);
        
        if (!currentTool.equals("NONE")) {
             String keyMap = "";
             if (currentTool.equals("CREATE")) keyMap = "create_select";
             if (currentTool.equals("LINK")) keyMap = "link_select";
             if (currentTool.equals("TYPE")) keyMap = "type_select";
             if (currentTool.equals("DELETE")) keyMap = "delete_select";
             
             if (!keyMap.isEmpty()) {
                 ItemStack info = inventory.getItem(49);
                 if (info != null) {
                    ItemBuilder ib = new ItemBuilder(info);
                    ib.addLore("");
                    ib.addLore(module.getMessage("editor.messages.instruction_title"));
                    ib.addLore(module.getMessage("editor.messages." + keyMap));
                    if (currentTool.equals("LINK") && selectedParentSlot != null) {
                         ib.addLore(module.getMessage("editor.messages.link_hint"));
                    }
                    inventory.setItem(49, ib.build());
                 }
             }
        }      

        createToolItem(50, Material.WRITABLE_BOOK, "editor.tools.save", false);
        createToolItem(53, Material.BARRIER, "editor.tools.delete", currentTool.equals("DELETE"));
    }
    
    private void createToolItem(int slot, Material material, String path, boolean isActive) {
        ItemBuilder builder = new ItemBuilder(material)
            .setName(module.getMessage(path + ".name"));
            
        String status = isActive ? module.getMessage("editor.messages.tool_active") : module.getMessage("editor.messages.tool_inactive");
        
        for (String line : module.getMessageList(path + ".lore")) {
            builder.addLore(line
                .replace("%status%", status)
                .replace("%id%", templateId));
        }
        
        builder.glowIf(isActive);
        inventory.setItem(slot, builder.build());
    }

    private String currentTool = "NONE";

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        int slot = event.getSlot();
        
        if (slot == 49 && currentTool.equals("NONE")) {
            player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);
            new MatrixHelpMenu(player, module, this).open();
            return;
        }
        
        if (slot >= 45) {
            handleToolClick(slot);
            return;
        }
        
        if (slot < 45) {
            handleCanvasClick(slot, event.isRightClick());
        }
    }
    
    private void handleToolClick(int slot) {
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
        
        currentTool = "NONE";
        boolean refresh = true;
        
        if (slot == TOOL_CREATE_SLOT) {
            currentTool = "CREATE"; 
        } else if (slot == TOOL_LINK_SLOT) {
            currentTool = "LINK"; 
            selectedParentSlot = null;
        } else if (slot == TOOL_TYPE_SLOT) {
            currentTool = "TYPE"; 
        } else if (slot == TOOL_DELETE_SLOT) {
            currentTool = "DELETE";
        } else if (slot == TOOL_RANDOM_SLOT) {
            generateRandomTemplate();
        } else if (slot == ACTION_SAVE_SLOT) {
            manager.saveTemplate(template);
            MessageUtils.send(player, module.getMessage("editor.messages.save_success").replace("%id%", templateId));
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            player.closeInventory();
            refresh = false;
        }
        
        if (refresh) {
            initializeItems();
        }
    }
    
    private void handleCanvasClick(int slot, boolean isRightClick) {
        TemplateNode existingNode = template.getNode(slot);

        switch (currentTool) {
            case "CREATE":
                if (existingNode == null) {
                    // Verifica se é sugestão validada
                    if (!suggestionSlots.containsKey(slot) && !template.getNodes().isEmpty()) {
                         MessageUtils.send(player, "§cTip: Try to expand from existing nodes (white glass).");
                    }
                    
                    TemplateNode newNode = new TemplateNode(NodeType.PASSIVE, slot);
                    template.addNode(slot, newNode);
                    
                    // Auto-Link
                    if (suggestionSlots.containsKey(slot)) {
                        Integer parent = suggestionSlots.get(slot);
                        if (parent != null) {
                            newNode.getParents().add(parent);
                            MessageUtils.send(player, "§aNode created & linked to " + parent + "!");
                        } else {
                            // Se era sugestão nula (Root), vira ROOT automaticamente
                            newNode.setType(NodeType.ROOT);
                            MessageUtils.send(player, "§aRoot node created!");
                        }
                    } else {
                        MessageUtils.send(player, "§aNode created (detached).");
                    }
                    
                    player.playSound(player.getLocation(), Sound.BLOCK_STONE_PLACE, 1, 1);
                    initializeItems();
                } else {
                    MessageUtils.send(player, module.getMessage("editor.messages.node_exists"));
                }
                break;
                
            case "DELETE":
                if (existingNode != null) {
                    template.getNodes().remove(slot);
                    // Remove references in parents? No, parents are IDs. Remove references in children list?
                    // We store "parents" list in children. We need to iterate all nodes and remove this slot from their parents list.
                    for(TemplateNode n : template.getNodes().values()) {
                        n.getParents().remove((Integer)slot);
                    }
                    player.playSound(player.getLocation(), Sound.BLOCK_STONE_BREAK, 1, 1);
                    initializeItems();
                }
                break;
                
            case "TYPE":
                if (existingNode != null) {
                    NodeType next = nextType(existingNode.getType());
                    existingNode.setType(next);
                    player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_IRON, 1, 1);
                    initializeItems();
                }
                break;
                
            case "LINK":
                if (existingNode == null) return;
                
                if (selectedParentSlot == null) {
                    selectedParentSlot = slot;
                    player.playSound(player.getLocation(), Sound.ITEM_SPYGLASS_USE, 1, 1);
                    initializeItems(); // Atualiza pra mostrar brilho
                } else {
                    if (selectedParentSlot == slot) {
                        MessageUtils.send(player, module.getMessage("editor.messages.link_self"));
                        selectedParentSlot = null;
                        initializeItems();
                        return;
                    }
                    // Adicionar Parent
                    if (!existingNode.getParents().contains(selectedParentSlot)) {
                        existingNode.getParents().add(selectedParentSlot);
                        MessageUtils.send(player, module.getMessage("editor.messages.link_created")
                             .replace("%parent%", String.valueOf(selectedParentSlot))
                             .replace("%child%", String.valueOf(slot)));
                    } else {
                         existingNode.getParents().remove(selectedParentSlot);
                         MessageUtils.send(player, module.getMessage("editor.messages.link_removed"));
                    }
                    player.playSound(player.getLocation(), Sound.BLOCK_CHAIN_PLACE, 1, 1);
                    selectedParentSlot = null; // Reset após linkar
                    initializeItems();
                }
                break;
                
             case "NONE":
                 if (isRightClick && existingNode != null && (existingNode.getType() == NodeType.MUTATION || existingNode.getType() == NodeType.MYTHIC_SKILL)) {
                     player.closeInventory();
                     MessageUtils.send(player, "§aType the MythicSkill ID in chat (or 'null' to clear):");
                     module.requestInput(player, (input) -> {
                         if (input.equalsIgnoreCase("null")) {
                             existingNode.setExtraData(null);
                             MessageUtils.send(player, "§aCleared skill ID.");
                         } else {
                             existingNode.setExtraData(input);
                             MessageUtils.send(player, "§aSet skill ID to: " + input);
                         }
                         
                         // Re-open menu
                         new MatrixEditorMenu(player, module, templateId).open();
                     });
                     return;
                 }
                 break;
                 
            default:
                 // player.sendMessage(module.getMessage("editor.messages.select_tool"));
                 break;
        }
    }
    
    private void calculateSuggestions(int originSlot) {
        // Left
        if (originSlot % 9 != 0) addSuggestion(originSlot - 1, originSlot);
        // Right
        if (originSlot % 9 != 8) addSuggestion(originSlot + 1, originSlot);
        // Up
        if (originSlot >= 9) addSuggestion(originSlot - 9, originSlot);
        // Down
        if (originSlot < 45) addSuggestion(originSlot + 9, originSlot);
    }
    
    private void addSuggestion(int targetSlot, int parentSlot) {
        // Se já existe um nó lá, ignora
        if (template.getNode(targetSlot) != null) return;
        
        // Se já foi sugerido por outro pai, mantém o primeiro ou sobrepõe? 
        // Vamos manter o primeiro encontrado por enquanto
        suggestionSlots.putIfAbsent(targetSlot, parentSlot);
    }

    private void generateRandomTemplate() {
        template.getNodes().clear();
        java.util.Random random = new java.util.Random();
        
        // 1. Create Root at Center (22)
        int rootSlot = 22;
        TemplateNode root = new TemplateNode(NodeType.ROOT, rootSlot);
        template.addNode(rootSlot, root);
        
        // 2. Expand N times
        int nodesToCreate = 8 + random.nextInt(10); // 8 to 17 nodes
        
        for (int i = 0; i < nodesToCreate; i++) {
            // Find valid expansion slots
            java.util.List<Integer> potentials = new java.util.ArrayList<>();
            java.util.Map<Integer, Integer> parents = new java.util.HashMap<>();
            
            for (TemplateNode node : template.getNodes().values()) {
                int[] neighbors = {node.getSlot() - 1, node.getSlot() + 1, node.getSlot() - 9, node.getSlot() + 9};
                for (int n : neighbors) {
                    if (n >= 0 && n < 45 && template.getNode(n) == null) {
                         // Check constraints (e.g. dont wrap lines for left/right)
                         if (n == node.getSlot() - 1 && node.getSlot() % 9 == 0) continue;
                         if (n == node.getSlot() + 1 && node.getSlot() % 9 == 8) continue;
                         
                         potentials.add(n);
                         parents.put(n, node.getSlot());
                    }
                }
            }
            
            if (potentials.isEmpty()) break;
            
            int slot = potentials.get(random.nextInt(potentials.size()));
            int parent = parents.get(slot);
            
            NodeType type = NodeType.PASSIVE;
            int r = random.nextInt(100);
            if (r < 20) type = NodeType.SOCKET;
            else if (r < 30) type = NodeType.CONNECTOR;
            else if (r < 40) type = NodeType.MUTATION;
            else if (r < 45) type = NodeType.MYTHIC_SKILL;
            
            TemplateNode newNode = new TemplateNode(type, slot);
            newNode.getParents().add(parent);
            template.addNode(slot, newNode);
        }
        
        MessageUtils.send(player, "Generated random template with " + template.getNodes().size() + " nodes!");
        player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 1, 1);
        initializeItems();
    }
    
    private NodeType nextType(NodeType current) {
        switch (current) {
            case PASSIVE: return NodeType.SOCKET;
            case SOCKET: return NodeType.MUTATION;
            case MUTATION: return NodeType.MYTHIC_SKILL;
            case MYTHIC_SKILL: return NodeType.CONNECTOR;
            case CONNECTOR: return NodeType.ROOT;
            case ROOT: return NodeType.PASSIVE;
            default: return NodeType.PASSIVE;
        }
    }
}
