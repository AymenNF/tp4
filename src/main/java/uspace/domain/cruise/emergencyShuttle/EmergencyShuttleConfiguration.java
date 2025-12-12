package uspace.domain.cruise.emergencyShuttle;

import uspace.domain.cruise.CruiseId;
import uspace.domain.cruise.money.Money;

import java.util.HashMap;
import java.util.Map;

public class EmergencyShuttleConfiguration {
    private static final int RESCUE_SHIP_CAPACITY = 50;
    private static final int STANDARD_SHUTTLE_CAPACITY = 20;
    private static final Money RESCUE_SHIP_COST = new Money(150000);
    private static final Money STANDARD_SHUTTLE_COST = new Money(50000);

    private static final Map<CruiseId, Integer> RESCUE_SHIP_COUNT_BY_CRUISE = new HashMap<>();

    static {
        RESCUE_SHIP_COUNT_BY_CRUISE.put(new CruiseId("JUPITER_MOONS_EXPLORATION_2085"), 5);
    }

    public static int getRescueShipCapacity() {
        return RESCUE_SHIP_CAPACITY;
    }

    public static int getStandardShuttleCapacity() {
        return STANDARD_SHUTTLE_CAPACITY;
    }

    public static Money getRescueShipCost() {
        return RESCUE_SHIP_COST;
    }

    public static Money getStandardShuttleCost() {
        return STANDARD_SHUTTLE_COST;
    }

    public static int getRescueShipCountForCruise(CruiseId cruiseId) {
        return RESCUE_SHIP_COUNT_BY_CRUISE.getOrDefault(cruiseId, 0);
    }

    public static int getCapacity(EmergencyShuttleType type) {
        return switch (type) {
            case RESCUE_SHIP -> RESCUE_SHIP_CAPACITY;
            case STANDARD_SHUTTLE -> STANDARD_SHUTTLE_CAPACITY;
        };
    }

    public static Money getCost(EmergencyShuttleType type) {
        return switch (type) {
            case RESCUE_SHIP -> RESCUE_SHIP_COST;
            case STANDARD_SHUTTLE -> STANDARD_SHUTTLE_COST;
        };
    }
}
