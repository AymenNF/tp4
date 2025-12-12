package uspace.application.cruise.emergencyShuttle.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class EmergencyShuttleDto {
    private String type;
    private List<EmergencyShuttleTravelerDto> travelers;
    private List<EmergencyShuttleCrewMemberDto> crewMembers;

    public EmergencyShuttleDto() {
    }

    public EmergencyShuttleDto(String type, List<EmergencyShuttleTravelerDto> travelers,
            List<EmergencyShuttleCrewMemberDto> crewMembers) {
        this.type = type;
        this.travelers = travelers;
        this.crewMembers = crewMembers;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("travelers")
    public List<EmergencyShuttleTravelerDto> getTravelers() {
        return travelers;
    }

    public void setTravelers(List<EmergencyShuttleTravelerDto> travelers) {
        this.travelers = travelers;
    }

    @JsonProperty("crewMembers")
    public List<EmergencyShuttleCrewMemberDto> getCrewMembers() {
        return crewMembers;
    }

    public void setCrewMembers(List<EmergencyShuttleCrewMemberDto> crewMembers) {
        this.crewMembers = crewMembers;
    }
}