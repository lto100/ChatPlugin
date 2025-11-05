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
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ReplyCommand implements CommandExecutor {
    private static Map<UUID, UUID> replyMap = new ConcurrentHashMap<>();

    public static Map<UUID, UUID> getReplyMap() {
        return replyMap;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            ChatPlugin.getInstance().getLogger().info("You must be a player to run this command");
            return true;
        } else if (args.length < 1) {
            sender.sendMessage(ChatColor.DARK_RED + command.getUsage());
            return true;
        }

        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(ReplyCommand.replyMap.get(player.getUniqueId()));

        if (target == null) {
            player.sendMessage(ChatColor.DARK_RED + "You have no one to reply to");
            return true;
        } else if (IgnoreManager.getIgnoreList().get(player.getUniqueId()).contains(target.getUniqueId())) {
            sender.sendMessage(ChatColor.DARK_RED + target.getName() + "is ignoring you");
            return true;
        }

        String message = String.join(" ", Arrays.copyOfRange(args, 0, args.length));

        player.sendMessage(ChatColor.LIGHT_PURPLE + "To " + target.getName() + ": " + message);
        target.sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + " whispers: " + message);

        ChatPlugin.getInstance().getLogger().info(player.getName() + " whispers to " + target.getName() + ": " + message);

        ReplyCommand.replyMap.put(player.getUniqueId(), target.getUniqueId());
        ReplyCommand.replyMap.put(target.getUniqueId(), player.getUniqueId());

        return true;
    }
}
