package uspace.application.cruise.emergencyShuttle.dtos;

import java.util.List;

public class EmergencyShuttleDto {
    public String type;
    public List<EmergencyShuttleTravelerDto> travelers;
    public List<EmergencyShuttleCrewMemberDto> crewMembers;
    
    public EmergencyShuttleDto() {
    }
    
    public EmergencyShuttleDto(String type, List<EmergencyShuttleTravelerDto> travelers, List<EmergencyShuttleCrewMemberDto> crewMembers) {
        this.type = type;
        this.travelers = travelers;
        this.crewMembers = crewMembers;
    }
}

