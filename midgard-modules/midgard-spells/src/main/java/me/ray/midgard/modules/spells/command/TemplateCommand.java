package me.ray.midgard.modules.spells.command;

import me.ray.midgard.core.command.MidgardCommand;
import me.ray.midgard.core.text.MessageUtils;
import me.ray.midgard.modules.spells.SpellsModule;
import me.ray.midgard.modules.spells.obj.template.MatrixTemplate;
import me.ray.midgard.modules.spells.gui.editor.MatrixTemplateEditor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TemplateCommand extends MidgardCommand {

    private final SpellsModule initialModule;

    public TemplateCommand(SpellsModule module) {
        super("template", "spells.admin", false);
        this.initialModule = module;
    }
    
    private SpellsModule getModule() {
        if (initialModule != null && initialModule.getTemplateManager() != null) {
            return initialModule;
        }
        try {
            return (SpellsModule) me.ray.midgard.core.MidgardCore.getModuleManager().getModule("Spells");
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        SpellsModule module = getModule();
        if (module == null) return Collections.emptyList();
        
        if (args.length == 1) {
            List<String> options = new ArrayList<>();
            options.add("edit");
            options.add("create");
            options.add("list");
            return StringUtil.copyPartialMatches(args[0], options, new ArrayList<>());
        }
        if (args.length == 2 && (args[0].equalsIgnoreCase("edit") || args[0].equalsIgnoreCase("create"))) {
            return StringUtil.copyPartialMatches(args[1], module.getTemplateManager().getTemplateIds(), new ArrayList<>());
        }
        return Collections.emptyList();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        SpellsModule module = getModule();
        
        if (module == null) {
            sender.sendMessage("§cSystem Error: SpellsModule not active.");
            return;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(module.getMessage("command.only_players"));
            return;
        }
        Player player = (Player) sender;
        
        if (module.getTemplateManager() == null) {
            MessageUtils.send(player, module.getMessage("command.error_load"));
            return;
        }
        
        if (args.length < 1) {
            sendHelp(player, module);
            return;
        }
        
        String action = args[0].toLowerCase();
        
        switch (action) {
            case "create":
            case "edit":
                if (args.length < 2) {
                    MessageUtils.send(player, module.getMessage("command.usage"));
                    return;
                }
                String id = args[1];
                
                MatrixTemplate template = module.getTemplateManager().getTemplate(id);
                if (template == null) {
                    if (action.equals("create")) {
                        template = new MatrixTemplate(id, id); 
                    } else {
                        MessageUtils.send(player, "<red>Template '" + id + "' not found. Use 'create' to make a new one.");
                        return;
                    }
                }
                
                new MatrixTemplateEditor(player, module, template).open();
                break;
                
            case "list":
                Collection<String> templates = module.getTemplateManager().getTemplateIds();
                MessageUtils.send(player, module.getMessage("command.list_header").replace("%count%", String.valueOf(templates.size())));
                for (String t : templates) {
                    MessageUtils.send(player, module.getMessage("command.list_item").replace("%id%", t));
                }
                break;
                
            default:
                sendHelp(player, module);
        }
    }
    
    private void sendHelp(Player p, SpellsModule module) {
        List<String> help = module.getMessageList("command.help");
        if (help == null || help.isEmpty()) {
            MessageUtils.send(p, "<yellow>Available Commands:");
            MessageUtils.send(p, " <gray>/template create <id>");
            MessageUtils.send(p, " <gray>/template edit <id>");
            MessageUtils.send(p, " <gray>/template list");
        } else {
            for (String line : help) {
                MessageUtils.send(p, line);
            }
        }
    }
}
