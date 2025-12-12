package uspace.api.cruise.booking.traveler;

import uspace.application.cruise.booking.traveler.dtos.SportBookingDto;
import uspace.domain.exceptions.MissingParameterException;

public class SportBookingDtoValidator {
    public void validate(SportBookingDto sportBookingDto) {
        if (sportBookingDto.sport == null || sportBookingDto.sport.isEmpty()) {
            throw new MissingParameterException("sport");
        }
    }
}
