package uspace.application.cruise.emergencyShuttle.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmergencyShuttleTravelerDto {
    @JsonProperty("travelerId")
    private String travelerId;

    @JsonProperty("travelerName")
    private String travelerName;

    public EmergencyShuttleTravelerDto() {}

    public EmergencyShuttleTravelerDto(String travelerId, String travelerName) {
        this.travelerId = travelerId;
        this.travelerName = travelerName;
    }

    public String getTravelerId() {
        return travelerId;
    }

    public void setTravelerId(String travelerId) {
        this.travelerId = travelerId;
    }

    public String getTravelerName() {
        return travelerName;
    }

    public void setTravelerName(String travelerName) {
        this.travelerName = travelerName;
    }
}