package uspace.domain.cruise.crew.crewMember;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import uspace.domain.cruise.crew.crewMember.employeeId.EmployeeId;

@Entity
public class CrewMember {
    @Id
    private EmployeeId employeeId;

    private CrewMemberName name;

    public CrewMember() {
    }

    public CrewMember(EmployeeId employeeId, CrewMemberName name) {
        this.employeeId = employeeId;
        this.name = name;
    }

    public EmployeeId getEmployeeId() {
        return employeeId;
    }
    
    public CrewMemberName getName() {
        return name;
    }
}
