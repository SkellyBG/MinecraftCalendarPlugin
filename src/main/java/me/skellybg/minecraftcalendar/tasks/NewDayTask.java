package me.skellybg.minecraftcalendar.tasks;

import me.skellybg.minecraftcalendar.events.NewDayEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitRunnable;

public class NewDayTask extends BukkitRunnable {
    private final static Event event = new NewDayEvent();
    private static NewDayTask task;

    public NewDayTask() {
        task = this;
    }

    @Override
    public void run() {
        Bukkit.getPluginManager().callEvent(event);
    }

    public static void cancelTask() {
        Bukkit.getScheduler().cancelTask(task.getTaskId());
    }
}
