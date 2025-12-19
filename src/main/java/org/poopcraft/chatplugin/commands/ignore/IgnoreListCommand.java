package org.poopcraft.chatplugin.commands.ignore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.poopcraft.chatplugin.ChatPlugin;
import org.poopcraft.chatplugin.utils.IgnoreUtil;

import java.util.UUID;

public class IgnoreListCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            ChatPlugin.getInstance().getLogger().info("You must be a player to run this command");
            return true;
        } else if (args.length > 1) {
            sender.sendMessage(ChatColor.DARK_RED + command.getUsage());
            return true;
        }

        Player player = (Player) sender;

        if (IgnoreUtil.getIgnoreList().get(player.getUniqueId()).isEmpty()) {
            sender.sendMessage(ChatColor.DARK_RED + "You aren't ignoring anyone");
            return true;
        }

        int pages = (int) Math.ceil(IgnoreUtil.getIgnoreList().get(player.getUniqueId()).size() / 10.0);
        int page = 1;

        if (args.length == 1) {
            int num;

            try {
                num = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.DARK_RED + command.getUsage());
                return true;
            }

            if (num < 1) {
                sender.sendMessage(ChatColor.DARK_RED + command.getUsage());
                return true;
            } else if (num > pages) {
                sender.sendMessage(ChatColor.DARK_RED + "Your ignore list is only " + pages + " page(s) long");
                return true;
            } else {
                page = num;
            }
        }

        String message = ChatColor.GOLD + "ignore list (page " + page + " of " + pages + "):\n";

        int start = (page - 1) * 10;
        int end = Math.min(start + 10, IgnoreUtil.getIgnoreList().get(player.getUniqueId()).size());
        for (int i = start; i < end; i++) {
            UUID ignoredId = IgnoreUtil.getIgnoreList().get(player.getUniqueId()).toArray(new UUID[0])[i];
            String name = Bukkit.getOfflinePlayer(ignoredId).getName();

            message += (ChatColor.YELLOW + name + "\n");
        }

        sender.sendMessage(message);

        return true;
    }
}
