package me.nv.listeners;

import me.nv.playerData.PlayerData;
import me.nv.playerData.PlayerDataFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Date;
import java.util.UUID;

import static me.nv.Core.*;
import static me.nv.utility.GeneralUtility.*;

public class ConnectionListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin (PlayerJoinEvent event){
        Player player = event.getPlayer();
        // Set display name with prefix
        //player.setDisplayName(translate(getGroupManagerHook().getPrefix(player) + player.getName() + getGroupManagerHook().getSuffix(player)));
        //This is what I use for applying group manager custom prefix and suffix, it must be run before every other line of code, yes including playerData
        PlayerData data = getPlayerDataManager().getPlayerData(player);
        // Load or initialize first join date asynchronously
        Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), () -> {
            String storedName = PlayerDataFile.get().getString(player.getUniqueId() + ".playername");
            if (storedName == null) {
                // Save new player data on main thread
                Bukkit.getScheduler().runTask(getPlugin(), () ->
                        getPlayerDataFileManager().savePlayerData(data)
                );
                // you can do other stuff here like welcome the new player, or tp them at spawn. or whatever.

            }
        });

        // Initialize first join date
        if (data.getFirstJoinDate() == null) {
            data.setFirstJoinDate(formatDate(new Date(), "dd/MM/yyyy"));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPreLogin (AsyncPlayerPreLoginEvent event){
        UUID uuid = event.getUniqueId();
        // Load or save player data before join
        Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), () -> {
            if (PlayerDataFile.get().getString(uuid + ".playername") == null) {
                getPlayerDataFileManager().savePlayerData(
                        getPlayerDataManager().getPlayerData(uuid)
                );
            } else {
                getPlayerDataFileManager().loadDataFromFile(uuid);
            }
        });
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit (PlayerQuitEvent event){
        Player player = event.getPlayer();
        PlayerData data = getPlayerDataManager().getPlayerData(player);
        data.setLastOnline(formatDate(new Date(), "dd/MM/yyyy HH:mm:ss"));

        Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), () -> getPlayerDataFileManager().savePlayerData(data));

        //must remove player data afterwards, since it takes ram and is useless to stay since we save it in storage
        getPlayerDataManager().removePlayerData(player.getUniqueId());
    }
}
