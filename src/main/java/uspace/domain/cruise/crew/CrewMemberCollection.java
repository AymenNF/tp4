package uspace.domain.cruise.crew;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import uspace.domain.cruise.crew.crewMember.CrewMember;
import uspace.domain.cruise.crew.crewMember.employeeId.EmployeeId;
import uspace.domain.cruise.crew.exceptions.CrewMemberAlreadyInCruiseException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Embeddable
public class CrewMemberCollection {

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Map<EmployeeId, CrewMember> crewMembers;

    public CrewMemberCollection() {
        this.crewMembers = new HashMap<>();
    }

    public CrewMemberCollection(Map<EmployeeId, CrewMember> crewMembers) {
        this.crewMembers = crewMembers;
    }

    public void add(CrewMember crewMember) {
        if (crewMembers.containsKey(crewMember.getEmployeeId())) {
            throw new CrewMemberAlreadyInCruiseException();
        }

        crewMembers.put(crewMember.getEmployeeId(), crewMember);
    }

    public CrewMember findByEmployeeId(EmployeeId employeeId) {
        return crewMembers.get(employeeId);
    }

    public List<CrewMember> findAll() {
        return List.copyOf(crewMembers.values());
    }
}
