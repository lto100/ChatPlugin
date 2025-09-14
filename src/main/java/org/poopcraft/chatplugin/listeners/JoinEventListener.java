package org.poopcraft.chatplugin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.poopcraft.chatplugin.IgnoreManager;

import java.util.HashSet;

public class JoinEventListener implements Listener {
    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        IgnoreManager.getIgnoreList().computeIfAbsent(player.getUniqueId(), uuid -> new HashSet<>());
    }
}
