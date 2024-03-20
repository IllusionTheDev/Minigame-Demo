package me.illusion.minigamedemo.game.tracker;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GamePlayerTracker {

    private final Collection<UUID> playerIds = Sets.newConcurrentHashSet();

    public void addPlayer(UUID playerId) {
        this.playerIds.add(playerId);
    }

    public void removePlayer(UUID playerId) {
        this.playerIds.remove(playerId);
    }

    public boolean isPlayerInGame(UUID playerId) {
        return this.playerIds.contains(playerId);
    }

    public Collection<UUID> getPlayerIds() {
        return Set.copyOf(this.playerIds);
    }
    // Player methods

    public void addPlayer(Player player) {
        this.addPlayer(player.getUniqueId());
    }

    public void removePlayer(Player player) {
        this.removePlayer(player.getUniqueId());
    }

    public boolean isPlayerInGame(Player player) {
        return this.isPlayerInGame(player.getUniqueId());
    }

    public Collection<Player> getPlayers() {
        Collection<Player> players = Sets.newHashSet();

        for (UUID playerId : this.playerIds) {
            Player player = Bukkit.getPlayer(playerId);
            if (player != null) {
                players.add(player);
            }
        }

        return players;
    }

    public int getPlayerCount() {
        return this.playerIds.size();
    }
}
