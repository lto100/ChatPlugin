package org.poopcraft.chatplugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.poopcraft.chatplugin.commands.*;

public final class ChatPlugin extends JavaPlugin {
    public static ChatPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        if (!this.getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        IgnoreSystem.load();

        this.getCommand("whisper").setExecutor(new WhisperCommand());
        this.getCommand("reply").setExecutor(new ReplyCommand());
        this.getCommand("ignore").setExecutor(new IgnoreCommand());
        this.getCommand("ignorelist").setExecutor(new IgnoreListCommand());
        this.getCommand("kill").setExecutor(new KillCommand());
        this.getCommand("help").setExecutor(new HelpCommand());

        getServer().getPluginManager().registerEvents(new IgnoreSystem(), this);
    }

    @Override
    public void onDisable() {
        IgnoreSystem.save();
    }
}