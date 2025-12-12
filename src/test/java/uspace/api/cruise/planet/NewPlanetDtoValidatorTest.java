package uspace.api.cruise.planet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import uspace.application.cruise.planet.dtos.NewPlanetDto;
import uspace.domain.exceptions.MissingParameterException;

import static org.junit.jupiter.api.Assertions.*;

class NewPlanetDtoValidatorTest {

    private static final String ANY_PLANET_NAME = "ANY_PLANET_NAME";
    private static final String ANY_LOCAL_DATE_TIME = "2084-04-08T12:30";
    private NewPlanetDtoValidator newPlanetDtoValidator;

    @BeforeEach
    void createNewPlanetDtoValidator() {
        newPlanetDtoValidator = new NewPlanetDtoValidator();
    }

    @Test
    void givenNewPlanetDtoWithoutName_whenValidate_thenThrowMissingParameterException() {
        NewPlanetDto newPlanetDto = new NewPlanetDto(null, ANY_LOCAL_DATE_TIME, ANY_LOCAL_DATE_TIME);

        Executable validate = () -> newPlanetDtoValidator.validate(newPlanetDto);

        assertThrows(MissingParameterException.class, validate);
    }

    @Test
    void givenNewPlanetDtoWithoutArrivalDateTime_whenValidate_thenThrowMissingParameterException() {
        NewPlanetDto newPlanetDto = new NewPlanetDto(ANY_PLANET_NAME, null, ANY_LOCAL_DATE_TIME);

        Executable validate = () -> newPlanetDtoValidator.validate(newPlanetDto);

        assertThrows(MissingParameterException.class, validate);
    }

    @Test
    void givenNewPlanetDtoWithoutDepartureDateTime_whenValidate_thenThrowMissingParameterException() {
        NewPlanetDto newPlanetDto = new NewPlanetDto(ANY_PLANET_NAME, ANY_LOCAL_DATE_TIME, null);

        Executable validate = () -> newPlanetDtoValidator.validate(newPlanetDto);

        assertThrows(MissingParameterException.class, validate);
    }

    @Test
    void givenNewPlanetDtoWithAllParameters_whenValidate_thenDoNothing() {
        NewPlanetDto newPlanetDto = new NewPlanetDto(ANY_PLANET_NAME, ANY_LOCAL_DATE_TIME, ANY_LOCAL_DATE_TIME);

        Executable validate = () -> newPlanetDtoValidator.validate(newPlanetDto);

        assertDoesNotThrow(validate);
    }
}