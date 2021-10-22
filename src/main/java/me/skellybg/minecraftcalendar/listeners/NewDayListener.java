package me.skellybg.minecraftcalendar.listeners;

import me.skellybg.minecraftcalendar.MinecraftCalendarPlugin;
import me.skellybg.minecraftcalendar.events.NewDayEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class NewDayListener implements Listener {
    private static FileConfiguration dataFileConfig;
    private static Calendar calendar;
    private static final String[] months = new String[]
            {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    public NewDayListener() {
        dataFileConfig = ((MinecraftCalendarPlugin) Bukkit.getPluginManager().getPlugin("MinecraftCalendarPlugin")).getDataFileConfig();
        calendar = new GregorianCalendar(dataFileConfig.getInt("year"), dataFileConfig.getInt("month"), dataFileConfig.getInt("day"));
    }

    public static int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    @EventHandler
    public void onNewDay(NewDayEvent event) {
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle(months[calendar.get(Calendar.MONTH)] + " " + formatDate(calendar.get(Calendar.DAY_OF_MONTH)) +
                            ", Year " + calendar.get(Calendar.YEAR), "", 10 , 70, 20);
        }
    }

    public static void saveData(File file) {
        dataFileConfig.set("day", calendar.get(Calendar.DAY_OF_MONTH));
        dataFileConfig.set("month", calendar.get(Calendar.MONTH));
        dataFileConfig.set("year", calendar.get(Calendar.YEAR));
        try {
            dataFileConfig.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String formatDate(int day) {
        switch (day % 10) {
            case 1: return day + "st";
            case 2: return day + "nd";
            case 3: return day + "rd";
            default: return day + "th";
        }

    }
}
