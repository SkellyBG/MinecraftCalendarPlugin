package me.skellybg.minecraftcalendar;

import me.skellybg.minecraftcalendar.events.NewDayEvent;
import me.skellybg.minecraftcalendar.listeners.NewDayListener;
import me.skellybg.minecraftcalendar.listeners.TimeSkipListener;
import me.skellybg.minecraftcalendar.tasks.NewDayTask;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

public class MinecraftCalendarPlugin extends JavaPlugin {
    private File dataFile;
    private FileConfiguration dataFileConfig;

    @Override
    public void onEnable() {
        createDataConfig();
        runTask();
        loadListener();
    }

    public FileConfiguration getDataFileConfig() {
        return dataFileConfig;
    }

    private void createDataConfig() {
        dataFile = new File(getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            dataFile.getParentFile().mkdirs();
            saveResource("data.yml", false);
        }
        dataFileConfig = new YamlConfiguration();
        try {
            dataFileConfig.load(dataFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void runTask() {
        World world = this.getServer().getWorld("world");
        BukkitRunnable task = new NewDayTask();
        task.runTaskTimer(this, (world.getTime() == 0) ? 0 : (24000 - world.getTime()), 24000);
    }

    private void loadListener() {
        getServer().getPluginManager().registerEvents(new NewDayListener(), this);
        getServer().getPluginManager().registerEvents(new TimeSkipListener(), this);
    }


    @Override
    public void onDisable() {
        NewDayListener.saveData(dataFile);
    }
}
