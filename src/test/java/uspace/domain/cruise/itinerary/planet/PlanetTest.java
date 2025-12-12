package uspace.domain.cruise.itinerary.planet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uspace.domain.cruise.dateTime.CruiseDateTime;
import uspace.domain.cruise.itinerary.planet.excursion.Excursion;
import uspace.domain.cruise.itinerary.planet.excursion.ExcursionName;
import uspace.domain.cruise.itinerary.planet.excursion.exceptions.InvalidExcursionException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlanetTest {

    private static final PlanetName ANY_PLANET_NAME = new PlanetName("Earth");
    private static final PlanetName ANY_SAME_PLANET_NAME = new PlanetName("Earth");
    private static final PlanetName ANY_OTHER_PLANET_NAME = new PlanetName("Mars");
    private static final LocalDateTime ANY_ARRIVAL_LOCAL_DATE_TIME = LocalDateTime.now();
    private static final int ANY_DURATION_IN_DAYS = 4;
    private static final LocalDateTime ANY_DEPARTURE_LOCAL_DATE_TIME = ANY_ARRIVAL_LOCAL_DATE_TIME.plusDays(ANY_DURATION_IN_DAYS);
    private static final CruiseDateTime ANY_ARRIVAL_DATE_TIME = new CruiseDateTime(ANY_ARRIVAL_LOCAL_DATE_TIME);
    private static final CruiseDateTime ANY_DEPARTURE_DATE_TIME = new CruiseDateTime(ANY_DEPARTURE_LOCAL_DATE_TIME);
    private static final CruiseDateTime ANY_ARRIVAL_DATE_TIME_OUTSIDE_TIME_SLOT = new CruiseDateTime(ANY_ARRIVAL_LOCAL_DATE_TIME.minusDays(1));
    private static final CruiseDateTime ANY_ARRIVAL_DATE_TIME_IN_TIME_SLOT = new CruiseDateTime(ANY_ARRIVAL_LOCAL_DATE_TIME.plusDays(1));
    private static final CruiseDateTime ANY_DEPARTURE_DATE_TIME_OUTSIDE_TIME_SLOT = new CruiseDateTime(ANY_DEPARTURE_LOCAL_DATE_TIME.plusDays(1));
    private static final CruiseDateTime ANY_DEPARTURE_DATE_TIME_IN_TIME_SLOT = new CruiseDateTime(ANY_DEPARTURE_LOCAL_DATE_TIME.minusDays(1));
    private static final ExcursionName ANY_EXCURSION_NAME = new ExcursionName("ANY_EXCURSION");
    private Planet planet;
    @Mock
    private Excursion excursionMock;

    @BeforeEach
    void createPlanet() {
        planet = new Planet(ANY_PLANET_NAME, ANY_ARRIVAL_DATE_TIME, ANY_DEPARTURE_DATE_TIME);
    }

    @Test
    void givenPlanetWithSameName_whenHasSameNameThan_thenReturnTrue() {
        Planet otherPlanetWithSameName = new Planet(ANY_SAME_PLANET_NAME, ANY_ARRIVAL_DATE_TIME, ANY_DEPARTURE_DATE_TIME);

        boolean hasSameName = planet.hasSameNameThan(otherPlanetWithSameName);

        assertTrue(hasSameName);
    }

    @Test
    void givenPlanetWithDifferentName_whenHasSameNameThan_thenReturnFalse() {
        Planet otherPlanetWithDifferentName = new Planet(ANY_OTHER_PLANET_NAME, ANY_ARRIVAL_DATE_TIME, ANY_DEPARTURE_DATE_TIME);

        boolean hasSameName = planet.hasSameNameThan(otherPlanetWithDifferentName);

        assertFalse(hasSameName);
    }
    @Test
    void givenSameName_whenHasSameNameThan_thenReturnTrue() {
        boolean hasSameName = planet.hasSameNameThan(ANY_SAME_PLANET_NAME);

        assertTrue(hasSameName);
    }

    @Test
    void givenDifferentName_whenHasSameNameThan_thenReturnFalse() {
        boolean hasSameName = planet.hasSameNameThan(ANY_OTHER_PLANET_NAME);

        assertFalse(hasSameName);
    }

    @Test
    void givenCruiseDateTimeAfterArrivalDateTime_whenArriveBefore_thenReturnTrue() {
        CruiseDateTime dateTimeAfterArrival = new CruiseDateTime(ANY_ARRIVAL_LOCAL_DATE_TIME.plusDays(1));

        boolean arriveBefore = planet.arriveBefore(dateTimeAfterArrival);

        assertTrue(arriveBefore);
    }

    @Test
    void givenCruiseDateTimeBeforeArrivalDateTime_whenArriveBefore_thenReturnFalse() {
        CruiseDateTime dateTimeBeforeArrival = new CruiseDateTime(ANY_ARRIVAL_LOCAL_DATE_TIME.minusDays(1));

        boolean arriveBefore = planet.arriveBefore(dateTimeBeforeArrival);

        assertFalse(arriveBefore);
    }

    @Test
    void givenArrivalDateTime_whenArriveBefore_thenReturnFalse() {
        CruiseDateTime arrivalDateTime = new CruiseDateTime(ANY_ARRIVAL_LOCAL_DATE_TIME);

        boolean arriveBefore = planet.arriveBefore(arrivalDateTime);

        assertFalse(arriveBefore);
    }

    @Test
    void givenPlanetWithSameArrivalAndDepartureDateTime_whenHasSameTimeSlotThan_thenReturnTrue() {
        Planet otherPlanetWithSameArrivalAndDepartureDateTime = new Planet(ANY_PLANET_NAME, ANY_ARRIVAL_DATE_TIME, ANY_DEPARTURE_DATE_TIME);

        boolean hasSameTimeSlot = planet.hasSameTimeSlotThan(otherPlanetWithSameArrivalAndDepartureDateTime);

        assertTrue(hasSameTimeSlot);
    }

    @Test
    void givenPlanetWithArrivalDateTimeInTimeSlotAndDepartureInTimeSlot_whenHasSameTimeSlotThan_thenReturnTrue() {
        Planet otherPlanetWithArrivalAndDepartureDateTimeInTimeSlot = new Planet(ANY_PLANET_NAME, ANY_ARRIVAL_DATE_TIME_IN_TIME_SLOT, ANY_DEPARTURE_DATE_TIME_IN_TIME_SLOT);

        boolean hasSameTimeSlot = planet.hasSameTimeSlotThan(otherPlanetWithArrivalAndDepartureDateTimeInTimeSlot);

        assertTrue(hasSameTimeSlot);
    }

    @Test
    void givenPlanetWithArrivalDateTimeInTimeSlotAndDepartureOutsideTimeSLot_whenHasSameTimeSlotThan_thenReturnTrue() {
        Planet otherPlanetWithArrivalDateTimeInTimeSlot = new Planet(ANY_PLANET_NAME, ANY_ARRIVAL_DATE_TIME_IN_TIME_SLOT, ANY_DEPARTURE_DATE_TIME_OUTSIDE_TIME_SLOT);

        boolean hasSameTimeSlot = planet.hasSameTimeSlotThan(otherPlanetWithArrivalDateTimeInTimeSlot);

        assertTrue(hasSameTimeSlot);
    }

    @Test
    void givenPlanetWithArrivalDateTimeOutsideTimeSlotAndDepartureInTimeSlot_whenHasSameTimeSlotThan_thenReturnTrue() {
        Planet otherPlanetWithDepartureDateTimeInTimeSlot = new Planet(ANY_PLANET_NAME, ANY_ARRIVAL_DATE_TIME_OUTSIDE_TIME_SLOT, ANY_DEPARTURE_DATE_TIME_IN_TIME_SLOT);

        boolean hasSameTimeSlot = planet.hasSameTimeSlotThan(otherPlanetWithDepartureDateTimeInTimeSlot);

        assertTrue(hasSameTimeSlot);
    }

    @Test
    void givenPlanetWithArrivalDateTimeOutsideTimeSlotAndDepartureOutsideTimeSlot_whenHasSameTimeSlotThan_thenReturnFalse() {
        Planet otherPlanetWithArrivalAndDepartureDateTimeOutsideTimeSlot = new Planet(ANY_PLANET_NAME, ANY_ARRIVAL_DATE_TIME_OUTSIDE_TIME_SLOT, ANY_DEPARTURE_DATE_TIME_OUTSIDE_TIME_SLOT);

        boolean hasSameTimeSlot = planet.hasSameTimeSlotThan(otherPlanetWithArrivalAndDepartureDateTimeOutsideTimeSlot);

        assertFalse(hasSameTimeSlot);
    }

    @Test
    void whenComputeExcursionDurationInDays_thenReturnDurationInDays() {
        int durationInDays = planet.computeExcursionDurationInDays();

        assertEquals(ANY_DURATION_IN_DAYS, durationInDays);
    }

    @Test
    void givenExcursionNotDuringTimeSlot_whenAddExcursion_thenThrowInvalidExcursionException() {
        when(excursionMock.isDuringTimeSlot(ANY_ARRIVAL_DATE_TIME, ANY_DEPARTURE_DATE_TIME)).thenReturn(false);

        Executable addExcursion = () -> planet.addExcursion(excursionMock);

        assertThrows(InvalidExcursionException.class, addExcursion);
    }

    @Test
    void givenExcursionDuringTimeSlot_whenAddExcursion_thenAddExcursion() {
        when(excursionMock.hasSameNameThan(ANY_EXCURSION_NAME)).thenReturn(true);
        when(excursionMock.isDuringTimeSlot(ANY_ARRIVAL_DATE_TIME, ANY_DEPARTURE_DATE_TIME)).thenReturn(true);

        planet.addExcursion(excursionMock);

        assertTrue(planet.containsExcursion(ANY_EXCURSION_NAME));
    }
}