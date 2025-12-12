package uspace.api.cruise.booking.traveler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import uspace.application.cruise.booking.traveler.dtos.SportBookingDto;
import uspace.domain.exceptions.MissingParameterException;

import static org.junit.jupiter.api.Assertions.*;

class SportBookingDtoValidatorTest {

    private SportBookingDtoValidator sportBookingDtoValidator;

    @BeforeEach
    void createSportBookingDtoValidator() {
        sportBookingDtoValidator = new SportBookingDtoValidator();
    }

    @Test
    void givenNullSport_whenValidate_thenThrowMissingParameterException() {
        SportBookingDto sportBookingDto = new SportBookingDto();
        sportBookingDto.sport = null;

        Executable validate = () -> sportBookingDtoValidator.validate(sportBookingDto);

        assertThrows(MissingParameterException.class, validate);
    }

    @Test
    void givenEmptySport_whenValidate_thenThrowMissingParameterException() {
        SportBookingDto sportBookingDto = new SportBookingDto();
        sportBookingDto.sport = "";

        Executable validate = () -> sportBookingDtoValidator.validate(sportBookingDto);

        assertThrows(MissingParameterException.class, validate);
    }
}