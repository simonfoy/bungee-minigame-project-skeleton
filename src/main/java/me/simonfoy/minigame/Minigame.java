package me.simonfoy.minigame;

import me.simonfoy.minigame.instance.Arena;
import me.simonfoy.minigame.listener.ConnectListener;
import me.simonfoy.minigame.listener.GameListener;
import me.simonfoy.minigame.listener.worldEvents;
import me.simonfoy.minigame.manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Minigame extends JavaPlugin {

    private Arena arena;

    @Override
    public void onEnable() {
        // Plugin startup logic
        ConfigManager.setupConfig(this);
        arena = new Arena(this);

        Bukkit.getPluginManager().registerEvents(new ConnectListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GameListener(this), this);
        Bukkit.getPluginManager().registerEvents(new worldEvents(), this);

    }

    public Arena getArena() { return arena; }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
