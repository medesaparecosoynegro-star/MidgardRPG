package me.ray.midgard.core.command;

import me.ray.midgard.core.text.MessageUtils;
import me.ray.midgard.core.MidgardCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public abstract class MidgardCommand implements CommandExecutor, TabCompleter {

    private final String name;
    private final String permission;
    private final boolean playerOnly;

    public MidgardCommand(String name, String permission, boolean playerOnly) {
        this.name = name;
        this.permission = permission;
        this.playerOnly = playerOnly;
    }

    public List<String> getAliases() {
        return Collections.emptyList();
    }
    
    public String getName() {
        return name;
    }
    
    public String getPermission() {
        return permission;
    }

    public abstract void execute(CommandSender sender, String[] args);

    public List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (playerOnly && !(sender instanceof Player)) {
            MessageUtils.send(sender, MidgardCore.getLanguageManager().getMessage("core.general.players_only"));
            return true;
        }

        if (permission != null && !sender.hasPermission(permission)) {
            if (sender instanceof Player) {
                MessageUtils.send(sender, MidgardCore.getLanguageManager().getMessage("core.general.no-permission"));
            } else {
                MessageUtils.send(sender, MidgardCore.getLanguageManager().getMessage("core.general.no-permission"));
            }
            return true;
        }

        try {
            execute(sender, args);
        } catch (Exception e) {
            sender.sendMessage("§cOcorreu um erro interno ao executar este comando.");
            MidgardCore.getInstance().getLogger().log(java.util.logging.Level.SEVERE, "Erro ao executar comando '" + name + "': " + e.getMessage(), e);
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (permission != null && !sender.hasPermission(permission)) {
            return Collections.emptyList();
        }
        try {
            return tabComplete(sender, args);
        } catch (Exception e) {
            // Silently fail tab complete to avoid console spam during typing
            return Collections.emptyList();
        }
    }
}
