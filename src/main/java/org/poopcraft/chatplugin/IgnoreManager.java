package org.poopcraft.chatplugin;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class IgnoreManager {
    private static final File ignoreFile = new File(ChatPlugin.getInstance().getDataFolder(), "ignorelist.yml");
    private static final YamlConfiguration ignoreConfig = YamlConfiguration.loadConfiguration(ignoreFile);

    public static HashMap<UUID, Set<UUID>> ignoreList = new HashMap<>();

    public static void save() {
        if (!ChatPlugin.getInstance().getDataFolder().exists())
            ChatPlugin.getInstance().getDataFolder().mkdir();

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
}