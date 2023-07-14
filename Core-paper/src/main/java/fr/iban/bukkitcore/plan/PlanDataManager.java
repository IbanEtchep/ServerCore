package fr.iban.bukkitcore.plan;

import fr.iban.bukkitcore.CoreBukkitPlugin;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PlanDataManager {

    private final CoreBukkitPlugin plugin;
    private long lastPlanFetch;
    private boolean usePlan = false;
    private Map<String, Long> planPlayTimes = new HashMap<>();

    public PlanDataManager(CoreBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    public Map<String,Long> getPlanPlayTimes() {
        return planPlayTimes;
    }

    public boolean usePlanIntegration() {
        return usePlan;
    }

    public String getServerWithLowestPlayTime(Collection<String> servers) {
        fetchPlanIfNeeded();
        String bestServer = null;
        long lowestPlayTime = Long.MAX_VALUE;
        for (String servername : servers) {
            if (planPlayTimes.containsKey(servername)) {
                long playTime = planPlayTimes.get(servername);
                if (playTime < lowestPlayTime) {
                    bestServer = servername;
                }
            } else {
                plugin.getLogger().warning("A server in a RTP group failed to return Plan playtime data.");
            }
        }
        return bestServer;
    }

    public void updatePlanPlayTimes() {
        try {
            Optional<PlanQueryAccessor> planHook = new PlanHook().hookIntoPlan();
            planHook.ifPresent(hook -> {
                usePlan = true;
                planPlayTimes = hook.getPlayTimes();
                plugin.getLogger().info("Fetched latest playtime data from Plan");
            });
            if(!usePlan) {
                plugin.getLogger().info("Failed to hook plan");
            }
            lastPlanFetch = Instant.now().getEpochSecond();
        } catch (NoClassDefFoundError ignored) {
        }
    }

    public void fetchPlanIfNeeded() {
        if ((lastPlanFetch + (plugin.getConfig().getInt("plan-data-update-frequency-minutes", 10) * 60L)) <= Instant.now().getEpochSecond()) {
            updatePlanPlayTimes();
        }
    }
}
