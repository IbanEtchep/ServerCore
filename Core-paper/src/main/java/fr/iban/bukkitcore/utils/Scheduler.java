package fr.iban.bukkitcore.utils;

import fr.iban.bukkitcore.CoreBukkitPlugin;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public final class Scheduler {

    private static final boolean isFolia = Bukkit.getVersion().contains("Folia");

    public static void run(Runnable runnable) {
        if (isFolia)
            Bukkit.getGlobalRegionScheduler()
                    .execute(CoreBukkitPlugin.getInstance(), runnable);

        else
            Bukkit.getScheduler().runTask(CoreBukkitPlugin.getInstance(), runnable);
    }

    public static void runAsync(Runnable runnable) {
        if (isFolia)
            Bukkit.getGlobalRegionScheduler()
                    .execute(CoreBukkitPlugin.getInstance(), runnable);

        else
            Bukkit.getScheduler().runTaskAsynchronously(CoreBukkitPlugin.getInstance(), runnable);
    }

    public static void runLater(Runnable runnable, long delayTicks) {
        if (isFolia) {
            new Task(Bukkit.getGlobalRegionScheduler()
                    .runDelayed(CoreBukkitPlugin.getInstance(), t -> runnable.run(), delayTicks));
        } else {
            new Task(Bukkit.getScheduler().runTaskLater(CoreBukkitPlugin.getInstance(), runnable, delayTicks));
        }
    }

    public static Task runTimer(Runnable runnable, long delayTicks, long periodTicks) {
        if (isFolia)
            return new Task(Bukkit.getGlobalRegionScheduler()
                    .runAtFixedRate(CoreBukkitPlugin.getInstance(), t -> runnable.run(), delayTicks < 1 ? 1 : delayTicks, periodTicks));

        else
            return new Task(Bukkit.getScheduler().runTaskTimer(CoreBukkitPlugin.getInstance(), runnable, delayTicks, periodTicks));
    }

    public static boolean isFolia() {
        return isFolia;
    }

    public static class Task {

        private Object foliaTask;
        private BukkitTask bukkitTask;

        Task(Object foliaTask) {
            this.foliaTask = foliaTask;
        }

        Task(BukkitTask bukkitTask) {
            this.bukkitTask = bukkitTask;
        }

        public void cancel() {
            if (foliaTask != null)
                ((ScheduledTask) foliaTask).cancel();
            else
                bukkitTask.cancel();
        }
    }
}