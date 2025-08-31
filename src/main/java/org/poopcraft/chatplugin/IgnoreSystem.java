package org.poopcraft.chatplugin;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.poopcraft.chatplugin.ChatPlugin.plugin;

public class IgnoreSystem implements Listener {
    private static final File ignoreFile = new File(plugin.getDataFolder(), "ignorelist.yml");
    private static final YamlConfiguration ignoreConfig = YamlConfiguration.loadConfiguration(ignoreFile);

    public static HashMap<UUID, Set<UUID>> ignoreList = new HashMap<>();

    public static void save() {
        for (UUID playerId : ignoreList.keySet()) {
            Set<UUID> ignoredIds = ignoreList.get(playerId);
            List<String> ignoredIdStrings = new ArrayList<>();

            for (UUID ignoredId : ignoredIds)
                ignoredIdStrings.add(ignoredId.toString());

            ignoreConfig.set(playerId.toString(), ignoredIdStrings);
        }

        try {
            ignoreConfig.save(ignoreFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        if (ignoreFile.exists()) {
            for (String playerIdString : ignoreConfig.getKeys(false)) {
                UUID playerId = UUID.fromString(playerIdString);
                List<String> ignoredIdStrings = ignoreConfig.getStringList(playerIdString);
                Set<UUID> ignoredIds = new HashSet<>();

                for (String ignoredIdString : ignoredIdStrings)
                    ignoredIds.add(UUID.fromString(ignoredIdString));

                ignoreList.put(playerId, ignoredIds);
            }
        }
    }

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
        ignoreList.computeIfAbsent(player.getUniqueId(), uuid -> new HashSet<>());
    }
}