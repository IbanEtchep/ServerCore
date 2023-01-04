package fr.iban.bukkitcore.plan;

import com.djrapitops.plan.capability.CapabilityService;
import com.djrapitops.plan.query.QueryService;

import java.util.Optional;

public class PlanHook {

    public PlanHook() {
    }

    public Optional<PlanQueryAccessor> hookIntoPlan() {
        if (!areAllCapabilitiesAvailable()) return Optional.empty();
        return Optional.ofNullable(createQueryAPIAccessor());
    }

    private boolean areAllCapabilitiesAvailable() {
        CapabilityService capabilities = CapabilityService.getInstance();
        return capabilities.hasCapability("QUERY_API");
    }

    private PlanQueryAccessor createQueryAPIAccessor() {
        try {
            return new PlanQueryAccessor(QueryService.getInstance());
        } catch (IllegalStateException planIsNotEnabled) {
            return null;  // Plan is not enabled, handle exception
        }
    }
}