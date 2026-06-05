package org.poopcraft.chatplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.poopcraft.chatplugin.ChatPlugin;

public class RulesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            ChatPlugin.getInstance().getLogger().info("You must be a player to run this command");
            return true;
        } else if (args.length > 0) {
            sender.sendMessage(ChatColor.DARK_RED + command.getUsage());
            return true;
        }

        sender.sendMessage(ChatColor.GOLD + "rules:\n" + ChatColor.YELLOW + "No duping");

        return true;
    }
}
