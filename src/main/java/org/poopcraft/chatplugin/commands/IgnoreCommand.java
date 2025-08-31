package org.poopcraft.chatplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.poopcraft.chatplugin.IgnoreSystem;

public class IgnoreCommand implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.DARK_RED + command.getUsage());
            return true;
        }

        OfflinePlayer target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            target = Bukkit.getOfflinePlayer(args[0]);
            if (!target.hasPlayedBefore()) {
                sender.sendMessage(ChatColor.DARK_RED + "Player not found");
                return true;
            }
        } else if (target.getName().equals(sender.getName())) {
            sender.sendMessage(ChatColor.DARK_RED + "You can't ignore yourself");
            return true;
        }

        Player player = (Player) sender;

        if (IgnoreSystem.ignoreList.get(player.getUniqueId()).contains(target.getUniqueId())) {
            IgnoreSystem.ignoreList.get(player.getUniqueId()).remove(target.getUniqueId());
            sender.sendMessage(ChatColor.GOLD + target.getName() + " is no longer ignored");
        } else {
            IgnoreSystem.ignoreList.get(player.getUniqueId()).add(target.getUniqueId());
            sender.sendMessage(ChatColor.GOLD + "Now ignoring " + target.getName());
        }

        return true;
    }
}
