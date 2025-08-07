package me.nv;

import lombok.Getter;
import lombok.Setter;
import me.nv.commands.adminCommands.Gamemode;
import me.nv.commands.normalCommands.Messaging;
import me.nv.listeners.ConnectionListener;
import me.nv.managers.ExampleGameManager;
import me.nv.managers.RamManager;
import me.nv.playerData.PlayerDataFile;
import me.nv.playerData.PlayerDataFileManager;
import me.nv.playerData.PlayerDataManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.Objects;

import static me.nv.utility.GeneralUtility.log;
import static me.nv.utility.GeneralUtility.translate;

public class Core extends JavaPlugin {

    //All instance references are on top of the class, if there's a better way to do this please let me know
    @Getter
    @Setter
    private static Core plugin;
    @Getter
    @Setter
    public static PlayerDataManager playerDataManager;
    @Getter
    @Setter
    public static PlayerDataFileManager playerDataFileManager;
    @Getter
    @Setter
    public static ExampleGameManager exampleGameManager;
    @Getter
    @Setter
    public static RamManager ramManager;

    @Override
    public void onEnable(){
        long startTime = System.currentTimeMillis();
        try {
            plugin = this;
            setupInstances();
            setupFiles();
            setupListeners();
            setupCommands();

            long endTime = System.currentTimeMillis();
            log(translate("&b=====&e===========================================&b====="));
            log(translate("&aCore &ahas been successfully enabled."));
            //you could also have something like this in case you need a dependency to check whether that dependency was loaded or not
            //you may need to use Postworld load to see this properly
            //log("");
            //log((Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) ? translate("&ePlaceholderAPI&7: &aEnabled") : translate("&ePlaceholderAPI&7: &cDisabled"));
            log("");
            log(translate("&eVersion&7: &3v" + getDescription().getVersion()));
            log("");
            log(translate("&aCore &floaded in &a" + (endTime - startTime) + "ms"));
            log(translate("&b=====&e===========================================&b====="));
        } catch (Exception exception) {
            log(translate("&cCore had an error while loading: \n"));
            exception.printStackTrace();
        }
    }

    //the functions below are used to have a cleaner code layout, optional basically.

    public void setupInstances(){
        playerDataManager = new PlayerDataManager();
        playerDataFileManager = new PlayerDataFileManager();
        exampleGameManager = new ExampleGameManager();
    }

    //i am not sure if loading files should be first, that should depend on your project

    public void setupFiles() {
        PlayerDataFile.setup();
        PlayerDataFile.save();
    }

    public void setupListeners() {
        getServer().getPluginManager().registerEvents(new ConnectionListener(), this);
    }

    public void setupCommands() {
        Objects.requireNonNull(getCommand("reply")).setExecutor(new Messaging());
        Objects.requireNonNull(getCommand("message")).setExecutor(new Messaging());
        // i am unsure if this is the best way to register the aliases (must be along side the plugin.yml).
        // and i don't think this is the best way to make a single item list, but oh well
        // and no i am not gonna use chat gpt for this lol
        Objects.requireNonNull(getCommand("message")).setAliases(Collections.singletonList("msg"));
        Objects.requireNonNull(getCommand("reply")).setAliases(Collections.singletonList("r"));


        Objects.requireNonNull(getCommand("gamemode")).setExecutor(new Gamemode());
    }

    @Override
    public void onDisable() {
        long startTime = System.currentTimeMillis();
        try {
            // not much else to run here
            saveData();

            long endTime = System.currentTimeMillis();
            log(translate("&b=====&e===========================================&b====="));
            log(translate("&cCore &ahas been successfully disabled."));
            //log("");
            //log((Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) ? translate("&ePlaceholderAPI&7: &aEnabled") : translate("&ePlaceholderAPI&7: &cDisabled"));
            log("");
            log(translate("&eVersion&7: &3v" + getDescription().getVersion()));
            log("");
            log(translate("&aCore &fshutdown in &a" + (endTime - startTime) + "ms"));
            log(translate("&b=====&e===========================================&b====="));
        } catch (Exception exception) {
            log(translate("&cCore had an error while shutting down: \n"));
            exception.printStackTrace();
        }
    }

    public void saveData() {
        try {
            getPlayerDataFileManager().saveAllPlayerDataToFile();

            log("[" + getPlugin().getName() + "] Saved player data");
        }
        catch (Exception exception) {
            log("[" + getPlugin().getName() + "] Error while saving player data...");
            exception.printStackTrace();
        }
    }
}
