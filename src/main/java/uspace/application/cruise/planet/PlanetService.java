package uspace.application.cruise.planet;

import jakarta.inject.Inject;
import uspace.application.cruise.planet.dtos.NewPlanetDto;
import uspace.application.cruise.planet.excursion.ExcursionAssembler;
import uspace.application.cruise.planet.excursion.dtos.ExcursionDto;
import uspace.application.utils.dateTimeParser.LocalDateTimeParser;
import uspace.domain.cruise.Cruise;
import uspace.domain.cruise.CruiseId;
import uspace.domain.cruise.CruiseRepository;
import uspace.domain.cruise.exceptions.CruiseNotFoundException;
import uspace.domain.cruise.itinerary.planet.Planet;
import uspace.domain.cruise.itinerary.planet.PlanetName;
import uspace.domain.cruise.itinerary.planet.PlanetValidator;
import uspace.domain.cruise.itinerary.planet.exceptions.InvalidPlanetException;
import uspace.domain.cruise.itinerary.planet.excursion.Excursion;

import java.time.LocalDateTime;

public class PlanetService {
    private final CruiseRepository cruiseRepository;
    private final LocalDateTimeParser localDateTimeParser;
    private final PlanetAssembler planetAssembler;
    private final PlanetValidator planetValidator;
    private final ExcursionAssembler excursionAssembler;

    @Inject
    public PlanetService(CruiseRepository cruiseRepository, LocalDateTimeParser localDateTimeParser, PlanetAssembler planetAssembler,
                         PlanetValidator planetValidator, ExcursionAssembler excursionAssembler) {
        this.cruiseRepository = cruiseRepository;
        this.localDateTimeParser = localDateTimeParser;
        this.planetAssembler = planetAssembler;
        this.planetValidator = planetValidator;
        this.excursionAssembler = excursionAssembler;
    }

    public void addPlanetToItinerary(String cruiseId, NewPlanetDto newPlanetDto) {
        Cruise cruise = cruiseRepository.findById(new CruiseId(cruiseId));
        if (cruise == null) {
            throw new CruiseNotFoundException();
        }

        LocalDateTime arrivalDateTime = localDateTimeParser.parse(newPlanetDto.arrivalDateTime);
        LocalDateTime departureDateTime = localDateTimeParser.parse(newPlanetDto.departureDateTime);

        if (!planetValidator.isPlanetValid(newPlanetDto.name)) {
            throw new InvalidPlanetException();
        }

        Planet planet = planetAssembler.toPlanet(newPlanetDto.name, arrivalDateTime, departureDateTime);
        cruise.addPlanetToItinerary(planet);

        cruiseRepository.save(cruise);
    }

    public void addExcursionToPlanet(String cruiseId, String planetName, ExcursionDto excursionDto) {
        Cruise cruise = cruiseRepository.findById(new CruiseId(cruiseId));
        if (cruise == null) {
            throw new CruiseNotFoundException();
        }

        LocalDateTime excursionStartDateTime = localDateTimeParser.parse(excursionDto.startDateTime);
        Excursion excursion = excursionAssembler.toExcursion(excursionDto, excursionStartDateTime);

        cruise.addExcursionToPlanet(new PlanetName(planetName), excursion);

        cruiseRepository.save(cruise);
    }
}
