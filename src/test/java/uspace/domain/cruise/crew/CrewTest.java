package uspace.domain.cruise.crew;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uspace.domain.cruise.cabin.CabinAvailabilities;
import uspace.domain.cruise.cabin.CabinType;
import uspace.domain.cruise.crew.crewMember.CrewMember;
import uspace.domain.cruise.crew.crewMember.CrewMemberName;
import uspace.domain.cruise.crew.crewMember.employeeId.EmployeeId;
import uspace.domain.cruise.crew.crewMember.employeeId.EmployeeIdValidator;
import uspace.domain.cruise.crew.exceptions.CrewMemberAlreadyInCruiseException;
import uspace.domain.cruise.crew.exceptions.InvalidEmployeeIdFormatException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CrewTest {

    private Crew crew;
    private static final CrewId ANY_CREW_ID = new CrewId();
    private static final EmployeeId ANY_EMPLOYEE_ID = new EmployeeId();
    private static final CrewMemberName ANY_CREW_MEMBER_NAME = new CrewMemberName("ANY_CREW_MEMBER_NAME");
    private static final CrewMember ANY_CREW_MEMBER = new CrewMember(ANY_EMPLOYEE_ID, ANY_CREW_MEMBER_NAME);
    @Mock
    private CrewMemberCollection crewMemberCollectionMock;
    @Mock
    private EmployeeIdValidator employeeIdValidatorMock;
    @Mock
    private CabinAvailabilities cabinAvailabilitiesMock;

    @BeforeEach
    void createCrew() {
        crew = new Crew(ANY_CREW_ID, crewMemberCollectionMock, employeeIdValidatorMock);
    }

    @Test
    void givenEmployeeIdInvalid_whenAddCrewMember_thenThrowInvalidEmployeeIdFormatException() {
        givenEmployeeIdInvalid();

        Executable addCrewMember = () -> crew.addCrewMember(ANY_CREW_MEMBER, cabinAvailabilitiesMock);

        assertThrows(InvalidEmployeeIdFormatException.class, addCrewMember);
    }

    @Test
    void givenEmployeeIdValidAndCrewMemberAlreadyInCruise_whenAddCrewMember_thenThrowCrewMemberAlreadyInCruiseException() {
        givenEmployeeIdValid();
        givenCrewMemberInCruise();

        Executable addCrewMember = () -> crew.addCrewMember(ANY_CREW_MEMBER, cabinAvailabilitiesMock);

        assertThrows(CrewMemberAlreadyInCruiseException.class, addCrewMember);
    }

    @Test
    void givenEmployeeIdValidAndCrewMemberNotInCruise_whenAddCrewMember_thenBookStandardCabin() {
        givenEmployeeIdValid();
        givenCrewMemberNotInCruise();

        crew.addCrewMember(ANY_CREW_MEMBER, cabinAvailabilitiesMock);

        verify(cabinAvailabilitiesMock).bookCabin(CabinType.STANDARD);
    }

    @Test
    void givenEmployeeIdValidAndCrewMemberNotInCruise_whenAddCrewMember_thenAddCrewMemberToCollection() {
        givenEmployeeIdValid();
        givenCrewMemberNotInCruise();

        crew.addCrewMember(ANY_CREW_MEMBER, cabinAvailabilitiesMock);

        verify(crewMemberCollectionMock).add(ANY_CREW_MEMBER);
    }

    private void givenCrewMemberInCruise() {
        when(crewMemberCollectionMock.findByEmployeeId(ANY_EMPLOYEE_ID)).thenReturn(ANY_CREW_MEMBER);
    }

    private void givenCrewMemberNotInCruise() {
        when(crewMemberCollectionMock.findByEmployeeId(ANY_EMPLOYEE_ID)).thenReturn(null);
    }

    private void givenEmployeeIdInvalid() {
        when(employeeIdValidatorMock.isValid(ANY_EMPLOYEE_ID)).thenReturn(false);
    }

    private void givenEmployeeIdValid() {
        when(employeeIdValidatorMock.isValid(ANY_EMPLOYEE_ID)).thenReturn(true);
    }
}