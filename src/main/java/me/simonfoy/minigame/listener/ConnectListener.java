package me.simonfoy.minigame.listener;

import me.simonfoy.minigame.GameState;
import me.simonfoy.minigame.Minigame;
import me.simonfoy.minigame.manager.ConfigManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectListener implements Listener {

    private Minigame minigame;

    public ConnectListener(Minigame minigame) {
        this.minigame = minigame;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {

        if (minigame.getArena().getState() == GameState.LIVE) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You cannot join this game right now as it is currently live.");
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player player = e.getPlayer();

        player.setGameMode(GameMode.ADVENTURE);

        minigame.getArena().addPlayer(e.getPlayer());

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {

        minigame.getArena().removePlayer();

    }
}
