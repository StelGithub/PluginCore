package me.nv.playerData;

import me.nv.utility.GeneralUtility;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

import static me.nv.Core.*;

public class PlayerDataFileManager {

    public void loadDataFromFile(UUID playerUUID) {
        String playerName = PlayerDataFile.get().getString(playerUUID + ".playername");
        PlayerData playerData = getPlayerDataManager().getPlayerData(playerUUID);
        if (playerName == null) {
            GeneralUtility.log("Player name is null for UUID: " + playerUUID);
            return;
        }

        FileConfiguration playerDataFile = PlayerDataFile.get();
        playerData.setName(playerName);
        playerData.setDisplayName(playerDataFile.getString(playerUUID + ".displayname"));
        playerData.setLastOnline(playerDataFile.getString(playerUUID + ".lastOnline"));
        playerData.setFirstJoinDate(playerDataFile.getString(playerUUID + ".firstJoinDate"));
        playerData.setCanBeMessaged(playerDataFile.getBoolean(playerUUID + ".canBeMessaged"));
    }

    public void saveAllPlayerDataToFile() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            savePlayerData(getPlayerDataManager().getPlayerData(player));
        }
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            try {
                savePlayerData(getPlayerDataManager().getPlayerData(Bukkit.getPlayer(offlinePlayer.getUniqueId())));
            } catch (Exception exception) {
                // catch error here
            }
        }
    }

    public void savePlayerData(PlayerData playerData) {
        UUID playerUUID = playerData.getPlayerUUID();

        PlayerDataFile.get().set(playerUUID + ".playername", playerData.getName());
        PlayerDataFile.get().set(playerUUID + ".displayname", playerData.getDisplayName());
        PlayerDataFile.get().set(playerUUID + ".lastOnline", playerData.getLastOnline());
        PlayerDataFile.get().set(playerUUID + ".firstJoinDate", playerData.getFirstJoinDate());
        PlayerDataFile.get().set(playerUUID + ".canBeMessaged", playerData.isCanBeMessaged());

        PlayerDataFile.save();
    }
}
