package uspace.application.cruise.emergencyShuttle.dtos;

import java.util.List;

public class EmergencyShuttleManifestDto {
    public int cost;
    public List<EmergencyShuttleDto> emergencyShuttles;
    
    public EmergencyShuttleManifestDto() {
    }
    
    public EmergencyShuttleManifestDto(int cost, List<EmergencyShuttleDto> emergencyShuttles) {
        this.cost = cost;
        this.emergencyShuttles = emergencyShuttles;
    }
}

