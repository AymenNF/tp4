package uspace.application.cruise.planet.excursion;

import uspace.application.cruise.planet.excursion.dtos.ExcursionDto;
import uspace.domain.cruise.dateTime.CruiseDateTime;
import uspace.domain.cruise.itinerary.planet.excursion.*;

import java.time.LocalDateTime;

public class ExcursionAssembler {

    public Excursion toExcursion(ExcursionDto excursionDto, LocalDateTime startDateTime) {
        return new Excursion(
                new ExcursionName(excursionDto.name),
                new ExcursionDescription(excursionDto.description),
                new CruiseDateTime(startDateTime),
                new ExcursionDuration(excursionDto.duration),
                new ExcursionCapacity(excursionDto.capacity)
        );
    }

    public ExcursionDto toDto(Excursion excursion) {
        return new ExcursionDto(
                excursion.getName().toString(),
                excursion.getDescription().toString(),
                excursion.getStartDateTime().toString(),
                excursion.getDurationInHours(),
                excursion.getCapacity().toInt()
        );
    }
}
