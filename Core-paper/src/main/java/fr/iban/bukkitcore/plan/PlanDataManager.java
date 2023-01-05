package fr.iban.bukkitcore.plan;

import com.djrapitops.plan.query.QueryService;
import fr.iban.bukkitcore.CoreBukkitPlugin;

import java.time.Instant;
import java.util.*;

public class PlanDataManager {
    private static long lastPlanFetch;
    private static boolean usePlan = false;
    private static Map<String, Long> planPlayTimes = new HashMap<>();

    public static Map<String,Long> getPlanPlayTimes() {
        return planPlayTimes;
    }

    public static boolean usePlanIntegration() {
        return usePlan;
    }

    public static String getServerWithLowestPlayTime(Collection<String> servers) {
        String bestServer = null;
        long lowestPlayTime = Long.MAX_VALUE;
        for (String servername : servers) {
            if (planPlayTimes.containsKey(servername)) {
                long playTime = planPlayTimes.get(servername);
                System.out.println(servername + " " + playTime);
                if (playTime < lowestPlayTime) {
                    bestServer = servername;
                }
            } else {
                CoreBukkitPlugin.getInstance().getLogger().warning("A server in a RTP group failed to return Plan playtime data.");
            }
        }
        return bestServer;
    }

    public static void updatePlanPlayTimes() {
        try {
            Optional<PlanQueryAccessor> planHook = new PlanHook().hookIntoPlan();
            planHook.ifPresent(hook -> {
                usePlan = true;
                planPlayTimes = hook.getPlayTimes();
                CoreBukkitPlugin.getInstance().getLogger().info("Fetched latest playtime data from Plan");
            });
            if(!usePlan) {
                CoreBukkitPlugin.getInstance().getLogger().info("Failed to hook plan");
            }
            lastPlanFetch = Instant.now().getEpochSecond();
        } catch (NoClassDefFoundError ignored) {
        }
    }

    public static void fetchPlanIfNeeded() {
        if ((lastPlanFetch + (CoreBukkitPlugin.getInstance().getConfig().getInt("plan-data-update-frequency-minutes", 10) * 60L)) <= Instant.now().getEpochSecond()) {
            updatePlanPlayTimes();
        }
    }
}
