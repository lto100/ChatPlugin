package org.poopcraft.chatplugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.poopcraft.chatplugin.commands.*;
import org.poopcraft.chatplugin.listeners.*;

public final class ChatPlugin extends JavaPlugin {
    private static ChatPlugin instance;

    public static ChatPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        IgnoreManager.load();

        this.getCommand("whisper").setExecutor(new WhisperCommand());
        this.getCommand("reply").setExecutor(new ReplyCommand());
        this.getCommand("ignore").setExecutor(new IgnoreCommand());
        this.getCommand("ignorelist").setExecutor(new IgnoreListCommand());
        this.getCommand("kill").setExecutor(new KillCommand());
        this.getCommand("help").setExecutor(new HelpCommand());

        getServer().getPluginManager().registerEvents(new ChatEventListener(), this);
        getServer().getPluginManager().registerEvents(new JoinEventListener(), this);
    }

    @Override
    public void onDisable() {
        IgnoreManager.save();
        instance = null;
    }
}