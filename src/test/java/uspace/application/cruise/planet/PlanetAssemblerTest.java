package uspace.application.cruise.planet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uspace.application.cruise.planet.excursion.ExcursionAssembler;
import uspace.domain.cruise.dateTime.CruiseDateTime;
import uspace.domain.cruise.itinerary.planet.Planet;
import uspace.domain.cruise.itinerary.planet.PlanetName;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PlanetAssemblerTest {
    private static final String ANY_PLANET_NAME_STR = "ANY_PLANET_NAME";
    private static final PlanetName ANY_PLANET_NAME = new PlanetName(ANY_PLANET_NAME_STR);
    private static final LocalDateTime ANY_ARRIVAL_LOCAL_DATE_TIME = LocalDateTime.of(2084, 4, 8, 12, 30);
    private static final LocalDateTime ANY_DEPARTURE_LOCAL_DATE_TIME = LocalDateTime.of(2084, 6, 8, 13, 30);
    private static final CruiseDateTime ANY_ARRIVAL_DATE_TIME = new CruiseDateTime(ANY_ARRIVAL_LOCAL_DATE_TIME);
    private static final CruiseDateTime ANY_DEPARTURE_DATE_TIME = new CruiseDateTime(ANY_DEPARTURE_LOCAL_DATE_TIME);

    private PlanetAssembler planetAssembler;

    @BeforeEach
    void createPlanetAssembler() {
        ExcursionAssembler excursionAssembler = new ExcursionAssembler();
        planetAssembler = new PlanetAssembler(excursionAssembler);
    }

    @Test
    void whenToPlanet_thenConvertPlanetDtoToPlanet() {
        Planet planet = planetAssembler.toPlanet(ANY_PLANET_NAME_STR, ANY_ARRIVAL_LOCAL_DATE_TIME, ANY_DEPARTURE_LOCAL_DATE_TIME);

        assertEquals(ANY_PLANET_NAME, planet.getName());
        assertEquals(ANY_ARRIVAL_DATE_TIME, planet.getArrivalDate());
        assertEquals(ANY_DEPARTURE_DATE_TIME, planet.getDepartureDate());
    }
}