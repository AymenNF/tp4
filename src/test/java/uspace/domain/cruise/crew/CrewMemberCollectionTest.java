package uspace.domain.cruise.crew;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import uspace.domain.cruise.crew.crewMember.CrewMember;
import uspace.domain.cruise.crew.crewMember.CrewMemberName;
import uspace.domain.cruise.crew.crewMember.employeeId.EmployeeId;
import uspace.domain.cruise.crew.exceptions.CrewMemberAlreadyInCruiseException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CrewMemberCollectionTest {

    private CrewMemberCollection crewMemberCollection;
    private static final String ANY_EMPLOYEE_ID_STR = "ANY_EMPLOYEE_ID";
    private static final EmployeeId ANY_EMPLOYEE_ID = new EmployeeId(ANY_EMPLOYEE_ID_STR);
    private static final EmployeeId ANY_SAME_EMPLOYEE_ID = new EmployeeId(ANY_EMPLOYEE_ID_STR);
    private static final EmployeeId ANY_OTHER_EMPLOYEE_ID = new EmployeeId("ANY_OTHER_EMPLOYEE_ID");
    private static final CrewMemberName ANY_CREW_MEMBER_NAME = new CrewMemberName("ANY_CREW_MEMBER_NAME");
    private static final CrewMember ANY_CREW_MEMBER = new CrewMember(ANY_EMPLOYEE_ID, ANY_CREW_MEMBER_NAME);
    private static final CrewMember ANY_OTHER_CREW_MEMBER = new CrewMember(ANY_OTHER_EMPLOYEE_ID, ANY_CREW_MEMBER_NAME);

    @BeforeEach
    void createCrewMemberCollection() {
        crewMemberCollection = new CrewMemberCollection();
    }

    @Test
    void givenEmptyCollection_whenFindById_thenReturnNull() {
        CrewMember foundCrewMember = crewMemberCollection.findByEmployeeId(ANY_EMPLOYEE_ID);

        assertNull(foundCrewMember);
    }

    @Test
    void givenCrewMemberInCollection_whenFindById_thenReturnCrewMember() {
        crewMemberCollection.add(ANY_CREW_MEMBER);

        CrewMember foundCrewMember = crewMemberCollection.findByEmployeeId(ANY_SAME_EMPLOYEE_ID);

        assertEquals(ANY_CREW_MEMBER, foundCrewMember);
    }

    @Test
    void givenCrewMemberNotInCollection_whenFindById_thenReturnNull() {
        crewMemberCollection.add(ANY_CREW_MEMBER);

        CrewMember foundCrewMember = crewMemberCollection.findByEmployeeId(ANY_OTHER_EMPLOYEE_ID);

        assertNull(foundCrewMember);
    }

    @Test
    void givenEmptyCollection_whenFindAll_thenReturnEmptyList() {
        List<CrewMember> allCrewMembers = crewMemberCollection.findAll();

        assertTrue(allCrewMembers.isEmpty());
    }

    @Test
    void givenMultipleCrewMembersInCollection_whenFindAll_thenReturnAllCrewMembers() {
        crewMemberCollection.add(ANY_CREW_MEMBER);
        crewMemberCollection.add(ANY_OTHER_CREW_MEMBER);
        int crewMembersNumber = 2;

        List<CrewMember> allCrewMembers = crewMemberCollection.findAll();

        assertEquals(crewMembersNumber, allCrewMembers.size());
        assertTrue(allCrewMembers.contains(ANY_CREW_MEMBER));
        assertTrue(allCrewMembers.contains(ANY_OTHER_CREW_MEMBER));
    }

    @Test
    void givenCrewMemberInCollection_whenAddCrewMemberWithSameEmployeeId_thenThrowCrewMemberAlreadyInCruiseException() {
        CrewMember anyCrewMemberWithSameEmployeeId = new CrewMember(ANY_SAME_EMPLOYEE_ID, ANY_CREW_MEMBER_NAME);
        crewMemberCollection.add(ANY_CREW_MEMBER);

        Executable addCrewMember = () -> crewMemberCollection.add(anyCrewMemberWithSameEmployeeId);

        assertThrows(CrewMemberAlreadyInCruiseException.class, addCrewMember);
    }

}