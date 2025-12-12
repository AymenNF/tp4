package uspace.application.cruise.emergencyShuttle.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmergencyShuttleCrewMemberDto {
    @JsonProperty("employeeId")
    private String employeeId;

    @JsonProperty("crewMemberName")
    private String crewMemberName;

    public EmergencyShuttleCrewMemberDto() {}

    public EmergencyShuttleCrewMemberDto(String employeeId, String crewMemberName) {
        this.employeeId = employeeId;
        this.crewMemberName = crewMemberName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getCrewMemberName() {
        return crewMemberName;
    }

    public void setCrewMemberName(String crewMemberName) {
        this.crewMemberName = crewMemberName;
    }
}