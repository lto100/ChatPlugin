package org.poopcraft.chatplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Map;

public class HelpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            sender.sendMessage(ChatColor.DARK_RED + command.getUsage());
            return true;
        }

        String message = ChatColor.GOLD + "commands:\n" + ChatColor.YELLOW;

        Map<String, Map<String, Object>> commands = Bukkit.getPluginManager().getPlugin("ChatPlugin").getDescription().getCommands();
        for (Map.Entry<String, Map<String, Object>> entry : commands.entrySet()) {
            message += entry.getKey() + " - " + entry.getValue().get("description") + "\n";
        }

        sender.sendMessage(message);

        return true;
    }
}
