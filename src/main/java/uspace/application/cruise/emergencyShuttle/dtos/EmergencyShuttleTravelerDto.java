package uspace.application.cruise.emergencyShuttle.dtos;

public class EmergencyShuttleTravelerDto {
    public String travelerId;
    public String travelerName;
    
    public EmergencyShuttleTravelerDto() {
    }
    
    public EmergencyShuttleTravelerDto(String travelerId, String travelerName) {
        this.travelerId = travelerId;
        this.travelerName = travelerName;
    }
}

