package uspace.application.cruise.emergencyShuttle.dtos;

public class EmergencyShuttleCrewMemberDto {
    public String employeeId;
    public String crewMemberName;
    
    public EmergencyShuttleCrewMemberDto() {
    }
    
    public EmergencyShuttleCrewMemberDto(String employeeId, String crewMemberName) {
        this.employeeId = employeeId;
        this.crewMemberName = crewMemberName;
    }
}

