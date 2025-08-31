package org.poopcraft.chatplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KillCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            sender.sendMessage(ChatColor.DARK_RED + command.getUsage());
            return true;
        }

        Player player = (Player) sender;
        player.setHealth(0);

        return true;
    }
}
