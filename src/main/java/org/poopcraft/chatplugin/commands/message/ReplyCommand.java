package org.poopcraft.chatplugin.commands.message;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.poopcraft.chatplugin.ChatPlugin;
import org.poopcraft.chatplugin.utils.*;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class ReplyCommand implements CommandExecutor {
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
        Player target = null;

        for (Map.Entry<UUID, UUID> entry : MessageUtil.getReplyMap().entrySet()) {
            if (entry.getValue() == player.getUniqueId()) {
                target = Bukkit.getPlayer(entry.getKey());
            }
        }

        if (target == null) {
            player.sendMessage(ChatColor.DARK_RED + "You have no one to reply to");
            return true;
        } else if (IgnoreUtil.getIgnoreList().get(target.getUniqueId()).contains(player.getUniqueId())) {
            player.sendMessage(ChatColor.DARK_RED + target.getName() + " is ignoring you");
            return true;
        }

        String message = String.join(" ", Arrays.copyOfRange(args, 0, args.length));
        MessageUtil.sendWhisper(player, target, message);

        return true;
    }
}
