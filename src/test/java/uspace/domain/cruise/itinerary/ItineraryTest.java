package uspace.domain.cruise.itinerary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uspace.domain.cruise.dateTime.CruiseDateTime;
import uspace.domain.cruise.itinerary.exceptions.ExcursionAlreadyExistsException;
import uspace.domain.cruise.itinerary.exceptions.InvalidPlanetDateException;
import uspace.domain.cruise.itinerary.exceptions.PlanetAlreadyExistsException;
import uspace.domain.cruise.itinerary.planet.Planet;
import uspace.domain.cruise.itinerary.planet.PlanetName;
import uspace.domain.cruise.itinerary.planet.exceptions.PlanetNotFoundException;
import uspace.domain.cruise.itinerary.planet.excursion.Excursion;
import uspace.domain.cruise.itinerary.planet.excursion.ExcursionName;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItineraryTest {
    private static final ItineraryId ANY_ITINERARY_ID = new ItineraryId();
    private static final int ANY_MINIMAL_DAYS_FOR_PLANET_EXCURSION = 6;
    private static final PlanetName ANY_PLANET_NAME = new PlanetName("Earth");
    private static final CruiseDateTime ANY_DATE_TIME = new CruiseDateTime(LocalDateTime.now());
    private static final Planet ANY_PLANET = new Planet(ANY_PLANET_NAME, ANY_DATE_TIME, ANY_DATE_TIME);
    private static final ExcursionName ANY_EXCURSION_NAME = new ExcursionName("ANY_EXCURSION");
    private static final Excursion ANY_EXCURSION = new Excursion(ANY_EXCURSION_NAME, null, null, null, null);
    private Itinerary itineraryWithAPlanet;
    private Itinerary itineraryWithAPlanetMock;
    @Mock
    private Planet planetMock;
    @Mock
    private Planet planetInItineraryMock;

    @BeforeEach
    void createItinerary() {
        List<Planet> anyPlanets = new ArrayList<>(List.of(ANY_PLANET));
        itineraryWithAPlanet = new Itinerary(ANY_ITINERARY_ID, anyPlanets, ANY_MINIMAL_DAYS_FOR_PLANET_EXCURSION);

        List<Planet> anyPlanetMock = new ArrayList<>(List.of(planetInItineraryMock));
        itineraryWithAPlanetMock = new Itinerary(ANY_ITINERARY_ID, anyPlanetMock, ANY_MINIMAL_DAYS_FOR_PLANET_EXCURSION);
    }

    @Test
    void givenPlanetWithSameNameInItinerary_whenAddPlanet_thenThrowPlanetAlreadyExistsException() {
        when(planetMock.hasSameNameThan(ANY_PLANET)).thenReturn(true);

        Executable addPlanetWithSameName = () -> itineraryWithAPlanet.addPlanet(planetMock);

        assertThrows(PlanetAlreadyExistsException.class, addPlanetWithSameName);
    }

    @Test
    void givenPlanetNotAlreadyInItineraryWithLessThanMinimalDaysForExcursion_whenAddPlanet_thenThrowInvalidPlanetDateException() {
        int lessThanMinimalDaysForExcursion = ANY_MINIMAL_DAYS_FOR_PLANET_EXCURSION - 1;
        when(planetMock.computeExcursionDurationInDays()).thenReturn(lessThanMinimalDaysForExcursion);
        Itinerary itineraryWithNoPlanet = new Itinerary(ANY_ITINERARY_ID, new ArrayList<>(), ANY_MINIMAL_DAYS_FOR_PLANET_EXCURSION);

        Executable addPlanetWithLessThanMinimalDaysForExcursion = () -> itineraryWithNoPlanet.addPlanet(planetMock);

        assertThrows(InvalidPlanetDateException.class, addPlanetWithLessThanMinimalDaysForExcursion);
    }

    @Test
    void givenPlanetNotAlreadyInItineraryWithMinimalDaysForExcursionAndOtherPlanetInItineraryWithSameTimeSlot_whenAddPlanet_thenThrowInvalidPlanetDateException() {
        when(planetMock.hasSameNameThan(ANY_PLANET)).thenReturn(false);
        when(planetMock.computeExcursionDurationInDays()).thenReturn(ANY_MINIMAL_DAYS_FOR_PLANET_EXCURSION);
        when(planetMock.hasSameTimeSlotThan(ANY_PLANET)).thenReturn(true);

        Executable addPlanetWithSameTimeSlot = () -> itineraryWithAPlanet.addPlanet(planetMock);

        assertThrows(InvalidPlanetDateException.class, addPlanetWithSameTimeSlot);
    }

    @Test
    void givenPlanetNotAlreadyInItineraryWithMinimalDaysForExcursionAndNoOtherPlanetInItineraryWithSameTimeSlot_whenAddPlanet_thenPlanetAdded() {
        when(planetMock.hasSameNameThan(ANY_PLANET)).thenReturn(false);
        when(planetMock.computeExcursionDurationInDays()).thenReturn(ANY_MINIMAL_DAYS_FOR_PLANET_EXCURSION);
        when(planetMock.hasSameTimeSlotThan(ANY_PLANET)).thenReturn(false);

        itineraryWithAPlanet.addPlanet(planetMock);

        assertTrue(itineraryWithAPlanet.getPlanets().contains(planetMock));
    }

    @Test
    void givenExcursionAlreadyExisting_whenAddExcursion_thenThrowExcursionAlreadyExistsException() {
        when(planetInItineraryMock.containsExcursion(ANY_EXCURSION_NAME)).thenReturn(true);

        Executable addExcursionWithSameName = () -> itineraryWithAPlanetMock.addExcursion(ANY_PLANET_NAME, ANY_EXCURSION);

        assertThrows(ExcursionAlreadyExistsException.class, addExcursionWithSameName);
    }

    @Test
    void givenExcursionNotAlreadyExistingAndPlanetNameNotInItinerary_whenAddExcursion_thenThrowPlanetNotFoundException() {
        when(planetInItineraryMock.containsExcursion(ANY_EXCURSION_NAME)).thenReturn(false);
        when(planetInItineraryMock.hasSameNameThan(ANY_PLANET_NAME)).thenReturn(false);

        Executable addExcursionWithPlanetNameNotInItinerary = () -> itineraryWithAPlanetMock.addExcursion(
                ANY_PLANET_NAME, ANY_EXCURSION);

        assertThrows(PlanetNotFoundException.class, addExcursionWithPlanetNameNotInItinerary);
    }

    @Test
    void givenExcursionNotAlreadyExistingAndPlanetNameInItinerary_whenAddExcursion_thenAddExcursionToPlanet() {
        when(planetInItineraryMock.containsExcursion(ANY_EXCURSION_NAME)).thenReturn(false);
        when(planetInItineraryMock.hasSameNameThan(ANY_PLANET_NAME)).thenReturn(true);

        itineraryWithAPlanetMock.addExcursion(ANY_PLANET_NAME, ANY_EXCURSION);

        verify(planetInItineraryMock).addExcursion(ANY_EXCURSION);
    }
}