package me.skellybg.minecraftcalendar.listeners;

import me.skellybg.minecraftcalendar.events.NewDayEvent;
import me.skellybg.minecraftcalendar.tasks.NewDayTask;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.TimeSkipEvent;

public class TimeSkipListener implements Listener {
    private World world = Bukkit.getServer().getWorld("world");

    @EventHandler
    public void onTimeSkip(TimeSkipEvent event) {
        if (event.getWorld() == world) {
            return;
        }
        NewDayTask.cancelTask();
        long newTime = Math.floorMod(world.getTime() + event.getSkipAmount(), 24000);
        if (event.getSkipReason() == TimeSkipEvent.SkipReason.NIGHT_SKIP) {
            Bukkit.getPluginManager().callEvent(new NewDayEvent());
        } else {
            if (newTime < world.getTime() || event.getSkipAmount() >= 24000) {
                Bukkit.getPluginManager().callEvent(new NewDayEvent());
            }
        }
        NewDayTask task = new NewDayTask();
        task.runTaskTimer(Bukkit.getPluginManager().getPlugin("MinecraftCalendarPlugin"), 24000 - newTime, 24000);

    }
}
