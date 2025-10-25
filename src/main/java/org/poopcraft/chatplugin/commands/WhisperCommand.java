package org.poopcraft.chatplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.poopcraft.chatplugin.ChatPlugin;
import org.poopcraft.chatplugin.IgnoreManager;

import java.util.Arrays;

public class WhisperCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.DARK_RED + command.getUsage());
            return true;
        }

        Player player = (Player) sender;
        Player target = (Player) Bukkit.getPlayerExact(args[0]);

        if (target == null) {
            sender.sendMessage(ChatColor.DARK_RED + args[0] + " is not online");
            return true;
        } else if (target.getName().equals(player.getName())) {
            sender.sendMessage(ChatColor.DARK_RED + "You can't send a message to yourself");
            return true;
        }

        if (IgnoreManager.getIgnoreList().get(target.getUniqueId()).contains(player.getUniqueId())) {
            sender.sendMessage(ChatColor.DARK_RED + target.getName() + " is ignoring you");
            return true;
        }

        String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        sender.sendMessage(ChatColor.LIGHT_PURPLE + "To " + target.getName() + ": " + message);
        target.sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + " whispers: " + message);

        ChatPlugin.getInstance().getLogger().info(player.getName() + " whispers to " + target.getName() + ": " + message);

        ReplyCommand.getReplyMap().put(player.getUniqueId(), target.getUniqueId());
        ReplyCommand.getReplyMap().put(target.getUniqueId(), player.getUniqueId());

        return true;
    }
}
