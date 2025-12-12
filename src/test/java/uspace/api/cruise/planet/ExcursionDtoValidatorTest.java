package uspace.api.cruise.planet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import uspace.application.cruise.planet.excursion.dtos.ExcursionDto;
import uspace.domain.exceptions.InvalidParameterException;
import uspace.domain.exceptions.MissingParameterException;

import static org.junit.jupiter.api.Assertions.*;

class ExcursionDtoValidatorTest {
    private ExcursionDtoValidator excursionDtoValidator;
    private static final String ANY_NAME = "ANY_EXCURSION";
    private static final String ANY_DESCRIPTION = "ANY_DESCRIPTION";
    private static final String ANY_START_DATE_TIME = "2084-04-08T12:30";
    private static final int ANY_POSITIVE_DURATION_IN_HOURS = 2;
    private static final int ANY_POSITIVE_CAPACITY = 11;

    @BeforeEach
    void createExcursionDtoValidator() {
        excursionDtoValidator = new ExcursionDtoValidator();
    }

    @Test
    void givenExcursionDtoWithoutName_whenValidate_thenThrowMissingParameterException() {
        ExcursionDto excursionDto = new ExcursionDto(null, ANY_DESCRIPTION, ANY_START_DATE_TIME,
                                                     ANY_POSITIVE_DURATION_IN_HOURS, ANY_POSITIVE_CAPACITY);

        Executable validate = () -> excursionDtoValidator.validate(excursionDto);

        assertThrows(MissingParameterException.class, validate);
    }

    @Test
    void givenExcursionDtoWithEmptyName_whenValidate_thenThrowMissingParameterException() {
        ExcursionDto excursionDto = new ExcursionDto("", ANY_DESCRIPTION, ANY_START_DATE_TIME,
                                                     ANY_POSITIVE_DURATION_IN_HOURS, ANY_POSITIVE_CAPACITY);

        Executable validate = () -> excursionDtoValidator.validate(excursionDto);

        assertThrows(MissingParameterException.class, validate);
    }

    @Test
    void givenExcursionDtoWithoutDescription_whenValidate_thenThrowMissingParameterException() {
        ExcursionDto excursionDto = new ExcursionDto(ANY_NAME, null, ANY_START_DATE_TIME,
                                                     ANY_POSITIVE_DURATION_IN_HOURS, ANY_POSITIVE_CAPACITY);

        Executable validate = () -> excursionDtoValidator.validate(excursionDto);

        assertThrows(MissingParameterException.class, validate);
    }

    @Test
    void givenExcursionDtoWithEmptyDescription_whenValidate_thenThrowMissingParameterException() {
        ExcursionDto excursionDto = new ExcursionDto(ANY_NAME, "", ANY_START_DATE_TIME, ANY_POSITIVE_DURATION_IN_HOURS,
                                                     ANY_POSITIVE_CAPACITY);

        Executable validate = () -> excursionDtoValidator.validate(excursionDto);

        assertThrows(MissingParameterException.class, validate);
    }

    @Test
    void givenExcursionDtoWithoutStartDate_whenValidate_thenThrowMissingParameterException() {
        ExcursionDto excursionDto = new ExcursionDto(ANY_NAME, ANY_DESCRIPTION, null, ANY_POSITIVE_DURATION_IN_HOURS,
                                                     ANY_POSITIVE_CAPACITY);

        Executable validate = () -> excursionDtoValidator.validate(excursionDto);

        assertThrows(MissingParameterException.class, validate);
    }

    @Test
    void givenExcursionDtoWithEmptyStartDate_whenValidate_thenThrowMissingParameterException() {
        ExcursionDto excursionDto = new ExcursionDto(ANY_NAME, ANY_DESCRIPTION, "", ANY_POSITIVE_DURATION_IN_HOURS,
                                                     ANY_POSITIVE_CAPACITY);

        Executable validate = () -> excursionDtoValidator.validate(excursionDto);

        assertThrows(MissingParameterException.class, validate);
    }

    @Test
    void givenExcursionDtoWithoutDuration_whenValidate_thenThrowMissingParameterException() {
        ExcursionDto excursionDto = new ExcursionDto(ANY_NAME, ANY_DESCRIPTION, ANY_START_DATE_TIME, 0,
                                                     ANY_POSITIVE_CAPACITY);

        Executable validate = () -> excursionDtoValidator.validate(excursionDto);

        assertThrows(MissingParameterException.class, validate);
    }

    @Test
    void givenExcursionDtoWithNegativeDuration_whenValidate_thenThrowInvalidParameterException() {
        ExcursionDto excursionDto = new ExcursionDto(ANY_NAME, ANY_DESCRIPTION, ANY_START_DATE_TIME, -1,
                                                     ANY_POSITIVE_CAPACITY);

        Executable validate = () -> excursionDtoValidator.validate(excursionDto);

        assertThrows(InvalidParameterException.class, validate);
    }

    @Test
    void givenExcursionDtoWithoutCapacity_whenValidate_thenThrowMissingParameterException() {
        ExcursionDto excursionDto = new ExcursionDto(ANY_NAME, ANY_DESCRIPTION, ANY_START_DATE_TIME,
                                                     ANY_POSITIVE_DURATION_IN_HOURS, 0);

        Executable validate = () -> excursionDtoValidator.validate(excursionDto);

        assertThrows(MissingParameterException.class, validate);
    }

    @Test
    void givenExcursionDtoWithNegativeCapacity_whenValidate_thenThrowInvalidParameterException() {
        ExcursionDto excursionDto = new ExcursionDto(ANY_NAME, ANY_DESCRIPTION, ANY_START_DATE_TIME,
                                                     ANY_POSITIVE_DURATION_IN_HOURS, -1);

        Executable validate = () -> excursionDtoValidator.validate(excursionDto);

        assertThrows(InvalidParameterException.class, validate);
    }

    @Test
    void givenExcursionDtoWithAllValidParameters_whenValidate_thenDoNothing() {
        ExcursionDto excursionDto = new ExcursionDto(ANY_NAME, ANY_DESCRIPTION, ANY_START_DATE_TIME,
                                                     ANY_POSITIVE_DURATION_IN_HOURS, ANY_POSITIVE_CAPACITY);

        Executable validate = () -> excursionDtoValidator.validate(excursionDto);

        assertDoesNotThrow(validate);
    }
}