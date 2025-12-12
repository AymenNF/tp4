package uspace.application.cruise.crew;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uspace.application.cruise.crew.dtos.CrewMemberDto;
import uspace.domain.cruise.Cruise;
import uspace.domain.cruise.CruiseId;
import uspace.domain.cruise.CruiseRepository;
import uspace.domain.cruise.crew.crewMember.CrewMember;
import uspace.domain.cruise.crew.crewMember.CrewMemberName;
import uspace.domain.cruise.crew.crewMember.employeeId.EmployeeId;
import uspace.domain.cruise.exceptions.CruiseNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CrewMemberServiceTest {

    private static final String ANY_CRUISE_ID_STR = "ANY_CRUISE_ID";
    private static final CruiseId ANY_CRUISE_ID = new CruiseId(ANY_CRUISE_ID_STR);
    private static final String ANY_EMPLOYEE_ID_STR = "ANY_EMPLOYEE_ID";
    private static final String ANY_CREW_MEMBER_NAME_STR = "ANY_EMPLOYEE_ID";
    private static final CrewMemberDto ANY_CREW_MEMBER_DTO = new CrewMemberDto(ANY_EMPLOYEE_ID_STR, ANY_CREW_MEMBER_NAME_STR);
    private static final EmployeeId ANY_EMPLOYEE_ID = new EmployeeId(ANY_EMPLOYEE_ID_STR);
    private static final CrewMemberName ANY_CREW_MEMBER_NAME = new CrewMemberName(ANY_CREW_MEMBER_NAME_STR);
    private static final CrewMember ANY_CREW_MEMBER = new CrewMember(ANY_EMPLOYEE_ID, ANY_CREW_MEMBER_NAME);

    private CrewMemberService crewMemberService;
    @Mock
    private CruiseRepository cruiseRepositoryMock;
    @Mock
    private CrewMemberAssembler crewMemberAssemblerMock;
    @Mock
    private Cruise cruiseMock;

    @BeforeEach
    void createCrewMemberService() {
        crewMemberService = new CrewMemberService(cruiseRepositoryMock, crewMemberAssemblerMock);
    }

    @Test
    void givenNonExistingCruise_whenAddCrewMember_thenThrowCruiseNotFoundException() {
        givenNonExistingCruise();

        Executable addCrewMember = () -> crewMemberService.addCrewMember(ANY_CRUISE_ID_STR, ANY_CREW_MEMBER_DTO);

        assertThrows(CruiseNotFoundException.class, addCrewMember);
    }

    @Test
    void givenExistingCruise_whenAddCrewMember_thenAddCrewMemberToCruise() {
        givenExistingCruise();
        givenCrewMember();

        crewMemberService.addCrewMember(ANY_CRUISE_ID_STR, ANY_CREW_MEMBER_DTO);

        verify(cruiseMock).addCrewMember(ANY_CREW_MEMBER);
    }

    @Test
    void givenExistingCruise_whenAddCrewMember_thenSaveCruise() {
        givenExistingCruise();
        givenCrewMember();

        crewMemberService.addCrewMember(ANY_CRUISE_ID_STR, ANY_CREW_MEMBER_DTO);

        verify(cruiseRepositoryMock).save(cruiseMock);
    }

    private void givenNonExistingCruise() {
        when(cruiseRepositoryMock.findById(ANY_CRUISE_ID)).thenReturn(null);
    }

    private void givenExistingCruise() {
        when(cruiseRepositoryMock.findById(ANY_CRUISE_ID)).thenReturn(cruiseMock);
    }

    private void givenCrewMember() {
        when(crewMemberAssemblerMock.toCrewMember(ANY_CREW_MEMBER_DTO)).thenReturn(ANY_CREW_MEMBER);
    }
}