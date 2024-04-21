package fr.iban.survivalcore.utils;

import fr.iban.survivalcore.SurvivalCorePlugin;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public final class Scheduler {

    private static final boolean isFolia = Bukkit.getVersion().contains("Folia");

    public static void run(Runnable runnable) {
        if (isFolia)
            Bukkit.getGlobalRegionScheduler().execute(SurvivalCorePlugin.getInstance(), runnable);
        else
            Bukkit.getScheduler().runTask(SurvivalCorePlugin.getInstance(), runnable);
    }

    public static void runAsync(Runnable runnable) {
        if (isFolia)
            Bukkit.getGlobalRegionScheduler().execute(SurvivalCorePlugin.getInstance(), runnable);
        else
            Bukkit.getScheduler().runTaskAsynchronously(SurvivalCorePlugin.getInstance(), runnable);
    }

    public static void runLater(Runnable runnable, long delayTicks) {
        if (isFolia) {
            new Task(Bukkit.getGlobalRegionScheduler()
                    .runDelayed(SurvivalCorePlugin.getInstance(), t -> runnable.run(), delayTicks));
        } else {
            new Task(Bukkit.getScheduler().runTaskLater(SurvivalCorePlugin.getInstance(), runnable, delayTicks));
        }
    }

    public static Task runTimer(Runnable runnable, long delayTicks, long periodTicks) {
        if (isFolia)
            return new Task(Bukkit.getGlobalRegionScheduler()
                    .runAtFixedRate(SurvivalCorePlugin.getInstance(), t -> runnable.run(), delayTicks < 1 ? 1 : delayTicks, periodTicks));
        else
            return new Task(Bukkit.getScheduler().runTaskTimer(SurvivalCorePlugin.getInstance(), runnable, delayTicks, periodTicks));
    }


    public static Task runTimerAsync(Runnable runnable, long delayTicks, long periodTicks) {
        if (isFolia)
            return new Task(Bukkit.getGlobalRegionScheduler()
                    .runAtFixedRate(SurvivalCorePlugin.getInstance(), t -> runnable.run(), delayTicks < 1 ? 1 : delayTicks, periodTicks));
        else
            return new Task(Bukkit.getScheduler().runTaskTimerAsynchronously(SurvivalCorePlugin.getInstance(), runnable, delayTicks, periodTicks));
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
