package me.simonfoy.minigame.instance;

import me.simonfoy.minigame.GameState;
import me.simonfoy.minigame.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Game {

    private Minigame minigame;
    private Arena arena;
    private HashMap<UUID, Integer> lives;

    public Game(Arena arena) {
        this.arena = arena;
        lives = new HashMap<>();
    }

    public void start(Minigame minigame) {
        this.minigame = minigame;

        arena.setState(GameState.LIVE);
        arena.sendMessage(ChatColor.GREEN + "GAME HAS STARTED! Your objective is this: KILL THE OTHER PLAYER 4 TIMES.");

        for (Player player : Bukkit.getOnlinePlayers()) {
            FileConfiguration config = this.minigame.getConfig();
            player.teleport(new Location(
                    Bukkit.getWorld(config.getString("arena.world")),
                    config.getDouble("arena.x"),
                    config.getDouble("arena.y"),
                    config.getDouble("arena.z"),
                    (float) config.getDouble("arena.yaw"),
                    (float) config.getDouble("arena.pitch")));
            lives.put(player.getUniqueId(), 4);
        }
    }

    public void removeLife(Player player) {
        int playerLives = lives.get(player.getUniqueId()) - 1;
        if (playerLives == 0) {
            arena.sendMessage(ChatColor.RED + player.getName() + " has been eliminated!");
            arena.reset();
            return;
        }
        player.sendMessage(ChatColor.RED + "You have lost a life!");
        lives.replace(player.getUniqueId(), playerLives);
    }
}
