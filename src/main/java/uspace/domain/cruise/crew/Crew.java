package uspace.domain.cruise.crew;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import uspace.domain.cruise.cabin.CabinAvailabilities;
import uspace.domain.cruise.cabin.CabinType;
import uspace.domain.cruise.crew.crewMember.CrewMember;
import uspace.domain.cruise.crew.crewMember.employeeId.EmployeeIdValidator;
import uspace.domain.cruise.crew.exceptions.CrewMemberAlreadyInCruiseException;
import uspace.domain.cruise.crew.exceptions.InvalidEmployeeIdFormatException;

import java.util.List;

@Entity
public class Crew {

    @Id
    private CrewId id;

    private CrewMemberCollection crewMemberCollection;

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(CascadeType.SAVE_UPDATE)
    private EmployeeIdValidator employeeIdValidator;

    public Crew() {
    }

    public Crew(CrewId id, CrewMemberCollection crewMemberCollection, EmployeeIdValidator employeeIdValidator) {
        this.id = id;
        this.crewMemberCollection = crewMemberCollection;
        this.employeeIdValidator = employeeIdValidator;
    }

    public void addCrewMember(CrewMember crewMember, CabinAvailabilities cabinAvailabilities) {
        if (!employeeIdValidator.isValid(crewMember.getEmployeeId())) {
            throw new InvalidEmployeeIdFormatException();
        }

        if (crewMemberCollection.findByEmployeeId(crewMember.getEmployeeId()) != null) {
            throw new CrewMemberAlreadyInCruiseException();
        }

        cabinAvailabilities.bookCabin(CabinType.STANDARD);

        crewMemberCollection.add(crewMember);
    }

    public List<CrewMember> getAllCrewMembers() {
        return List.copyOf(crewMemberCollection.findAll());
    }
}
