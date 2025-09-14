package org.poopcraft.chatplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.poopcraft.chatplugin.IgnoreManager;

public class ChatEventListener implements Listener {
    @EventHandler
    private void onChat(AsyncPlayerChatEvent event) {
        Player sender = event.getPlayer();

        if (event.getMessage().charAt(0) == '>') {
            event.setMessage(ChatColor.GREEN + event.getMessage());
        }

        for (Player recipient : Bukkit.getOnlinePlayers()) {
            if (recipient != sender && IgnoreManager.getIgnoreList().get(recipient.getUniqueId()).contains(sender.getUniqueId())) {
                event.getRecipients().remove(recipient);
            }
        }
    }
}
