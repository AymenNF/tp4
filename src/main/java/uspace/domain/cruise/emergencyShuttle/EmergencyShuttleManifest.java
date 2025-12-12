package uspace.domain.cruise.emergencyShuttle;

import uspace.domain.cruise.money.Money;

import java.util.List;

public class EmergencyShuttleManifest {
    private final List<EmergencyShuttle> shuttles;
    private final Money totalCost;
    
    public EmergencyShuttleManifest(List<EmergencyShuttle> shuttles) {
        // Filter out empty shuttles
        this.shuttles = shuttles.stream()
                .filter(shuttle -> !shuttle.isEmpty())
                .toList();
        this.totalCost = calculateTotalCost(this.shuttles);
    }
    
    private Money calculateTotalCost(List<EmergencyShuttle> shuttles) {
        float totalCost = 0;
        for (EmergencyShuttle shuttle : shuttles) {
            totalCost += EmergencyShuttleConfiguration.getCost(shuttle.getType()).toFloat();
        }
        return new Money(totalCost);
    }
    
    public List<EmergencyShuttle> getShuttles() {
        return List.copyOf(shuttles);
    }
    
    public Money getTotalCost() {
        return totalCost;
    }
}

