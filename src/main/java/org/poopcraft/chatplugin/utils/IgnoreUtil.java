package org.poopcraft.chatplugin.utils;

import org.bukkit.configuration.file.YamlConfiguration;
import org.poopcraft.chatplugin.ChatPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class IgnoreUtil {
    private static final File ignoreFile = new File(ChatPlugin.getInstance().getDataFolder(), "ignorelist.yml");
    private static final YamlConfiguration ignoreConfig = YamlConfiguration.loadConfiguration(ignoreFile);

    private static final Map<UUID, Set<UUID>> ignoreList = new ConcurrentHashMap<>();

    public static Map<UUID, Set<UUID>> getIgnoreList() {
        return ignoreList;
    }

    public static void save() {
        if (!ChatPlugin.getInstance().getDataFolder().exists()) {
            ChatPlugin.getInstance().getDataFolder().mkdir();
        }

        for (Map.Entry<UUID, Set<UUID>> entry : ignoreList.entrySet()) {
            Set<UUID> ignoredIds = ignoreList.get(entry.getKey());
            List<String> ignoredIdStrings = new ArrayList<>();

            for (UUID ignoredId : ignoredIds) {
                ignoredIdStrings.add(ignoredId.toString());
            }

            ignoreConfig.set(entry.getKey().toString(), ignoredIdStrings);
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

                for (String ignoredIdString : ignoredIdStrings) {
                    ignoredIds.add(UUID.fromString(ignoredIdString));
                }

                ignoreList.put(playerId, ignoredIds);
            }
        }
    }
}