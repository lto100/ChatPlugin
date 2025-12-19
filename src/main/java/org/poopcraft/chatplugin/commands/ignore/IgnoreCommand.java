package org.poopcraft.chatplugin.commands.ignore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.poopcraft.chatplugin.ChatPlugin;
import org.poopcraft.chatplugin.utils.IgnoreUtil;

import java.util.Set;
import java.util.UUID;

public class IgnoreCommand implements CommandExecutor, Listener {
    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            ChatPlugin.getInstance().getLogger().info("You must be a player to run this command");
            return true;
        } else if (args.length != 1) {
            sender.sendMessage(ChatColor.DARK_RED + command.getUsage());
            return true;
        }

        Player player = (Player) sender;
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
        
        String targetName = target.getName();
        UUID targetUUID = target.getUniqueId();
        
        Set<UUID> ignoreList = IgnoreUtil.getIgnoreList().get(player.getUniqueId());
        if (ignoreList.contains(targetUUID)) {
            ignoreList.remove(targetUUID);
            sender.sendMessage(ChatColor.YELLOW + targetName + " is no longer ignored");
        } else {
            ignoreList.add(targetUUID);
            sender.sendMessage(ChatColor.YELLOW + "Now ignoring " + targetName);
        }

        return true;
    }
}
