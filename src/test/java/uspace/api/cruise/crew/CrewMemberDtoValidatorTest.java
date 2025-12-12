package uspace.api.cruise.crew;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import uspace.application.cruise.crew.dtos.CrewMemberDto;
import uspace.domain.exceptions.MissingParameterException;

import static org.junit.jupiter.api.Assertions.*;

class CrewMemberDtoValidatorTest {

    private static final String ANY_EMPLOYEE_ID_STR = "ANY_EMPLOYEE_ID";
    private static final String ANY_CREW_MEMBER_NAME_STR = "ANY_EMPLOYEE_ID";
    private CrewMemberDtoValidator crewMemberDtoValidator;

    @BeforeEach
    void createCrewMemberDtoValidator() {
        crewMemberDtoValidator = new CrewMemberDtoValidator();
    }

    @Test
    void givenCrewMemberDtoWithoutName_whenValidate_thenThrowMissingParameterException() {
        CrewMemberDto crewMemberDto = new CrewMemberDto(ANY_EMPLOYEE_ID_STR, null);

        Executable validate = () -> crewMemberDtoValidator.validate(crewMemberDto);

        assertThrows(MissingParameterException.class, validate);
    }

    @Test
    void givenCrewMemberDtoWithoutEmployeeId_whenValidate_thenThrowMissingParameterException() {
        CrewMemberDto crewMemberDto = new CrewMemberDto(null, ANY_CREW_MEMBER_NAME_STR);

        Executable validate = () -> crewMemberDtoValidator.validate(crewMemberDto);

        assertThrows(MissingParameterException.class, validate);
    }

    @Test
    void givenCrewMemberDtoWithAllParameters_whenValidate_thenDoNothing() {
        CrewMemberDto crewMemberDto = new CrewMemberDto(ANY_EMPLOYEE_ID_STR, ANY_CREW_MEMBER_NAME_STR);

        Executable validate = () -> crewMemberDtoValidator.validate(crewMemberDto);

        assertDoesNotThrow(validate);
    }
}