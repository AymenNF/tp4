package uspace.api.cruise.planet;

import uspace.application.cruise.planet.dtos.NewPlanetDto;
import uspace.domain.exceptions.MissingParameterException;

public class NewPlanetDtoValidator {
    public void validate(NewPlanetDto newPlanetDto) {
        if (newPlanetDto.name == null) {
            throw new MissingParameterException("name");
        }
        if (newPlanetDto.arrivalDateTime == null) {
            throw new MissingParameterException("arrivalDateTime");
        }
        if (newPlanetDto.departureDateTime == null) {
            throw new MissingParameterException("departureDateTime");
        }
    }
}
