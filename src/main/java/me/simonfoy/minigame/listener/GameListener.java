package me.simonfoy.minigame.listener;

import me.simonfoy.minigame.GameState;
import me.simonfoy.minigame.Minigame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class GameListener implements Listener {

    private Minigame minigame;

    public GameListener(Minigame minigame) {
        this.minigame = minigame;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {

        if (minigame.getArena().getState() == GameState.LIVE) {
            minigame.getArena().getGame().removeLife(e.getEntity().getPlayer());
        }
    }
}
