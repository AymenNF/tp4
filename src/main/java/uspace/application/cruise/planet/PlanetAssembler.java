package uspace.application.cruise.planet;

import jakarta.inject.Inject;
import uspace.application.cruise.planet.dtos.PlanetDto;
import uspace.application.cruise.planet.excursion.ExcursionAssembler;
import uspace.application.cruise.planet.excursion.dtos.ExcursionDto;
import uspace.domain.cruise.dateTime.CruiseDateTime;
import uspace.domain.cruise.itinerary.planet.Planet;
import uspace.domain.cruise.itinerary.planet.PlanetName;

import java.time.LocalDateTime;
import java.util.List;

public class PlanetAssembler {
    private final ExcursionAssembler excursionAssembler;

    @Inject
    public PlanetAssembler(ExcursionAssembler excursionAssembler) {
        this.excursionAssembler = excursionAssembler;
    }

    public PlanetDto toDto(Planet planet) {
        List<ExcursionDto> excursionDtos = getExcursionDtos(planet);

        return new PlanetDto(
            planet.getName().toString(),
            planet.getArrivalDate().toString(),
            planet.getDepartureDate().toString(),
            excursionDtos
        );
    }

    public Planet toPlanet(String name, LocalDateTime arrivalLocalDateTime, LocalDateTime departureLocalDateTime) {
        PlanetName planetName = new PlanetName(name);
        CruiseDateTime arrivalDateTime = new CruiseDateTime(arrivalLocalDateTime);
        CruiseDateTime departureDateTime = new CruiseDateTime(departureLocalDateTime);

        return new Planet(planetName, arrivalDateTime, departureDateTime);
    }

    private List<ExcursionDto> getExcursionDtos(Planet planet) {
        return planet.getExcursions().stream().map(excursionAssembler::toDto).toList();
    }
}
