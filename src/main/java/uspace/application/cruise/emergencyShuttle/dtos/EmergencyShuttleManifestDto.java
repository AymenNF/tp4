package uspace.application.cruise.emergencyShuttle.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class EmergencyShuttleManifestDto {
    @JsonProperty("cost")
    private int cost;

    @JsonProperty("emergencyShuttles")
    private List<EmergencyShuttleDto> emergencyShuttles;

    public EmergencyShuttleManifestDto() {}

    public EmergencyShuttleManifestDto(int cost, List<EmergencyShuttleDto> emergencyShuttles) {
        this.cost = cost;
        this.emergencyShuttles = emergencyShuttles;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public List<EmergencyShuttleDto> getEmergencyShuttles() {
        return emergencyShuttles;
    }

    public void setEmergencyShuttles(List<EmergencyShuttleDto> emergencyShuttles) {
        this.emergencyShuttles = emergencyShuttles;
    }
}