package me.simonfoy.minigame.instance;

import me.simonfoy.minigame.GameState;
import me.simonfoy.minigame.Minigame;
import me.simonfoy.minigame.manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.Objects;

public class Arena {

    private Minigame minigame;

    private Location spawn;

    private GameState state;
    private Countdown countdown;
    private Game game;

    public Arena(Minigame minigame) {
        this.minigame = minigame;
        FileConfiguration config = this.minigame.getConfig();
        spawn = new Location(
                Bukkit.getWorld(config.getString("lobby-spawn.world")),
                config.getDouble("lobby-spawn.x"),
                config.getDouble("lobby-spawn.y"),
                config.getDouble("lobby-spawn.z"),
                (float) config.getDouble("lobby-spawn.yaw"),
                (float) config.getDouble("lobby-spawn.pitch"));

        this.state = GameState.RECRUITING;
        this.countdown = new Countdown(this.minigame, this);
        this.game = new Game(this);
    }

    /* GAME */

    public void start() { game.start(minigame); }

    public void reset() {
        sendTitle("", "");
        state = GameState.RECRUITING;
        countdown.cancel();
        countdown = new Countdown(minigame, this);
        game = new Game(this);
    }

    /* TOOLS */

    public void sendMessage(String message) {
        for (Player player : Bukkit.getOnlinePlayers() ) {
            player.sendMessage(message);
        }
    }

    public void sendTitle(String title, String subtitle) {
        for (Player player : Bukkit.getOnlinePlayers() ) {
            player.sendTitle(title, subtitle);
        }
    }

    /* PLAYERS */
    public void addPlayer(Player player){
        player.teleport(spawn);

        if (state.equals(GameState.RECRUITING) && Bukkit.getOnlinePlayers().size() >= ConfigManager.getRequiredPlayers()) {
            countdown.start();
        }
    }

    public void removePlayer(){
        if (state == GameState.COUNTDOWN && Bukkit.getOnlinePlayers().size() - 1 < ConfigManager.getRequiredPlayers()) {
            sendMessage(ChatColor.RED + "There is not enough players. Countdown stopped.");
            reset();
            return;
        }

        if (state == GameState.LIVE && Bukkit.getOnlinePlayers().size() - 1 < ConfigManager.getRequiredPlayers()) {
            sendMessage(ChatColor.RED + "The game has ended as too many players have left.");
            reset();

        }
    }


    /* INFO */

    public GameState getState() { return state; }
    public Game getGame() { return game; }

    public void setState(GameState state) { this.state = state; }

}
