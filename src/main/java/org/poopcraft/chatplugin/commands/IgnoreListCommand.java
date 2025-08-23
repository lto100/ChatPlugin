package org.poopcraft.chatplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.poopcraft.chatplugin.IgnoreSystem;

import java.util.UUID;

public class IgnoreListCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            sender.sendMessage(ChatColor.DARK_RED + command.getUsage());
            return true;
        }

        Player player = (Player) sender;

        if (IgnoreSystem.ignoreList.get(player.getUniqueId()).isEmpty()) {
            sender.sendMessage(ChatColor.GOLD + "You aren't ignoring anyone");
            return true;
        }

        String message = ChatColor.GOLD + "ignore list:\n";

        for (UUID ignoredUUID : IgnoreSystem.ignoreList.get(player.getUniqueId())) {
            String name = Bukkit.getOfflinePlayer(ignoredUUID).getName();

            message += (ChatColor.YELLOW + name + "\n");
        }

        sender.sendMessage(message);

        return true;
    }
}
