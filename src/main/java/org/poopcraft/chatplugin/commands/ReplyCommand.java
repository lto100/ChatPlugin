package org.poopcraft.chatplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.poopcraft.chatplugin.IgnoreSystem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class ReplyCommand implements CommandExecutor {
    public static HashMap<UUID, UUID> reply = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.DARK_RED + command.getUsage());
            return true;
        }

        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(ReplyCommand.reply.get(player.getUniqueId()));

        if (target == null) {
            player.sendMessage(ChatColor.DARK_RED + "You have no one to reply to");
            return true;
        } else if (IgnoreSystem.ignoreList.get(player.getUniqueId()).contains(target.getUniqueId())) {
            sender.sendMessage(ChatColor.DARK_RED + target.getName() + "is ignoring you");
            return true;
        }

        String message = String.join(" ", Arrays.copyOfRange(args, 0, args.length));

        player.sendMessage(ChatColor.LIGHT_PURPLE + "To " + target.getName() + ": " + message);
        target.sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + " whispers: " + message);

        ReplyCommand.reply.put(player.getUniqueId(), target.getUniqueId());
        ReplyCommand.reply.put(target.getUniqueId(), player.getUniqueId());

        return true;
    }
}
