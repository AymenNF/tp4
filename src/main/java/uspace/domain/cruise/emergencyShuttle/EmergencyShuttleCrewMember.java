package uspace.domain.cruise.emergencyShuttle;

import uspace.domain.cruise.crew.crewMember.CrewMember;
import uspace.domain.cruise.crew.crewMember.CrewMemberName;
import uspace.domain.cruise.crew.crewMember.employeeId.EmployeeId;

public class EmergencyShuttleCrewMember {
    private final EmployeeId employeeId;
    private final CrewMemberName crewMemberName;
    
    public EmergencyShuttleCrewMember(EmployeeId employeeId, CrewMemberName crewMemberName) {
        this.employeeId = employeeId;
        this.crewMemberName = crewMemberName;
    }
    
    public static EmergencyShuttleCrewMember fromCrewMember(CrewMember crewMember) {
        return new EmergencyShuttleCrewMember(crewMember.getEmployeeId(), crewMember.getName());
    }
    
    public EmployeeId getEmployeeId() {
        return employeeId;
    }
    
    public CrewMemberName getCrewMemberName() {
        return crewMemberName;
    }
}

