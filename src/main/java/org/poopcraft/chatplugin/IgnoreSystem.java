package org.poopcraft.chatplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class IgnoreSystem implements Listener {
    public static HashMap<UUID, Set<UUID>> ignoreList = new HashMap<>();

    @EventHandler
    private void onChat(AsyncPlayerChatEvent event) {
        Player sender = event.getPlayer();

        for (Player recipient : Bukkit.getOnlinePlayers())
            if (recipient != sender && ignoreList.get(recipient.getUniqueId()).contains(sender.getUniqueId()))
                event.getRecipients().remove(recipient);
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!ignoreList.containsKey(player.getUniqueId()))
            ignoreList.put(player.getUniqueId(), new HashSet<>());
    }
}