package uspace.api.cruise.planet;

import uspace.application.cruise.planet.excursion.dtos.ExcursionDto;
import uspace.domain.exceptions.InvalidParameterException;
import uspace.domain.exceptions.MissingParameterException;

public class ExcursionDtoValidator {

    public void validate(ExcursionDto excursionDto) {
        if (excursionDto.name == null || excursionDto.name.isEmpty()) {
            throw new MissingParameterException("name");
        }
        if (excursionDto.description == null || excursionDto.description.isEmpty()) {
            throw new MissingParameterException("description");
        }
        if (excursionDto.startDateTime == null || excursionDto.startDateTime.isEmpty()) {
            throw new MissingParameterException("startDateTime");
        }
        if (excursionDto.duration == 0) {
            throw new MissingParameterException("duration");
        }
        if (excursionDto.capacity == 0) {
            throw new MissingParameterException("capacity");
        }
        if (excursionDto.duration < 0) {
            throw new InvalidParameterException("Excursion duration must be greater than 0.");
        }
        if (excursionDto.capacity < 0) {
            throw new InvalidParameterException("Excursion capacity must be greater than 0.");
        }
    }
}
