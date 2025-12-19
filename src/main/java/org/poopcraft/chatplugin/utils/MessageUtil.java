package org.poopcraft.chatplugin.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.poopcraft.chatplugin.ChatPlugin;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MessageUtil {
    private static final Map<UUID, UUID> replyMap = new ConcurrentHashMap<>();

    public static Map<UUID, UUID> getReplyMap() {
        return replyMap;
    }

    public static void sendWhisper(Player sender, Player target, String message) {
        String senderName = sender.getName();
        String targetName = target.getName();

        sender.sendMessage(ChatColor.LIGHT_PURPLE + "You whisper to " + targetName + ": " + message);
        target.sendMessage(ChatColor.LIGHT_PURPLE + senderName + " whispers to you: " + message);

        MessageUtil.getReplyMap().put(sender.getUniqueId(), target.getUniqueId());

        ChatPlugin.getInstance().getLogger().info(senderName + " whispers to " + targetName + ": " + message);
    }
}
