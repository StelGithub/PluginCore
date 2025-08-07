package me.nv.playerData;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager {
    private final Map<UUID, PlayerData> playerDataMap = new HashMap<>();

    public PlayerData getPlayerData(Player player) {
        return getPlayerData(player.getUniqueId());
    }

    public PlayerData getPlayerData(UUID playerUUID) {
        return this.playerDataMap.computeIfAbsent(playerUUID, uuid -> new PlayerData(uuid));
    }

    public void removePlayerData(UUID playerUUID) {
        this.playerDataMap.remove(playerUUID);
    }
}
