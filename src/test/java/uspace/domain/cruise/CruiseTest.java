package uspace.domain.cruise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uspace.domain.cruise.booking.Booking;
import uspace.domain.cruise.booking.BookingId;
import uspace.domain.cruise.booking.CruiseBookings;
import uspace.domain.cruise.booking.traveler.TravelerId;
import uspace.domain.cruise.booking.traveler.exceptions.TravelerNotFoundException;
import uspace.domain.cruise.cabin.CabinAvailabilities;
import uspace.domain.cruise.cabin.CabinPriceRegistry;
import uspace.domain.cruise.crew.Crew;
import uspace.domain.cruise.crew.crewMember.CrewMember;
import uspace.domain.cruise.dateTime.CruiseDateTime;
import uspace.domain.cruise.itinerary.Itinerary;
import uspace.domain.cruise.itinerary.exceptions.InvalidPlanetDateException;
import uspace.domain.cruise.itinerary.planet.Planet;
import uspace.domain.cruise.itinerary.planet.PlanetName;
import uspace.domain.cruise.itinerary.planet.excursion.Excursion;
import uspace.domain.cruise.sport.Sport;
import uspace.domain.cruise.zeroGravityExperience.ZeroGravityExperience;
import uspace.domain.cruise.zeroGravityExperience.exceptions.ZeroGravityExperienceBookingTimeException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CruiseTest {

    private static final CruiseId ANY_CRUISE_ID = new CruiseId("ANY_CRUISE_ID");
    private static final LocalDateTime ANY_LOCAL_DEPARTURE_DATE_TIME = LocalDateTime.now();
    private static final CruiseDateTime ANY_DEPARTURE_DATE_TIME = new CruiseDateTime(ANY_LOCAL_DEPARTURE_DATE_TIME);
    private static final CruiseDateTime ANY_END_DATE_TIME = new CruiseDateTime(ANY_LOCAL_DEPARTURE_DATE_TIME.plusDays(1));
    private static final CruiseDateTime ANY_DATE_TIME_BEFORE_DEPARTURE = new CruiseDateTime(ANY_LOCAL_DEPARTURE_DATE_TIME.minusHours(1));
    private static final CruiseDateTime ANY_DATE_TIME_AFTER_DEPARTURE = new CruiseDateTime(ANY_LOCAL_DEPARTURE_DATE_TIME.plusHours(1));
    private static final BookingId ANY_BOOKING_ID = new BookingId("ANY_BOOKING_ID");
    private static final PlanetName ANY_PLANET_NAME = new PlanetName("ANY_PLANET_NAME");
    private static final Excursion ANY_EXCURSION = new Excursion();
    private static final TravelerId ANY_TRAVELER_ID = new TravelerId();
    private static final CrewMember ANY_CREW_MEMBER = new CrewMember();
    private static final Sport ANY_SPORT = new Sport();
    private Cruise cruise;
    @Mock
    private CruiseBookings cruiseBookingsMock;
    @Mock
    private CabinAvailabilities cabinAvailabilitiesMock;
    @Mock
    private CabinPriceRegistry cabinPriceRegistryMock;
    @Mock
    private ZeroGravityExperience zeroGravityExperienceMock;
    @Mock
    private Itinerary itineraryMock;
    @Mock
    private Booking bookingMock;
    @Mock
    private Planet planetMock;
    @Mock
    private Crew crewMock;

    @BeforeEach
    void createCruise() {
        cruise = new Cruise(ANY_CRUISE_ID, ANY_DEPARTURE_DATE_TIME, ANY_END_DATE_TIME, cabinPriceRegistryMock, cabinAvailabilitiesMock,
                            cruiseBookingsMock, zeroGravityExperienceMock, itineraryMock, crewMock);
    }

    @Test
    void givenExperienceBookingDateTimeAfterDeparture_whenBookZeroGravityExperience_thenThrowZeroGravityExperienceBookingTimeException() {
        Executable bookZeroGravityExperience = () ->
                cruise.bookZeroGravityExperience(ANY_BOOKING_ID, ANY_TRAVELER_ID, ANY_DATE_TIME_AFTER_DEPARTURE);

        assertThrows(ZeroGravityExperienceBookingTimeException.class, bookZeroGravityExperience);
    }

    @Test
    void givenExperienceBookingDateTimeBeforeDepartureAndNonExistingBookingId_whenBookZeroGravityExperience_thenThrowTravelerNotFoundException() {
        Executable bookZeroGravityExperience = () ->
                cruise.bookZeroGravityExperience(ANY_BOOKING_ID, ANY_TRAVELER_ID, ANY_DATE_TIME_BEFORE_DEPARTURE);

        assertThrows(TravelerNotFoundException.class, bookZeroGravityExperience);
    }

    @Test
    void givenExperienceBookingDateTimeBeforeDepartureAndExistingBookingId_whenBookZeroGravityExperience_thenBookingBookZeroGravityExperience() {
        when(cruiseBookingsMock.findById(ANY_BOOKING_ID)).thenReturn(bookingMock);

        cruise.bookZeroGravityExperience(ANY_BOOKING_ID, ANY_TRAVELER_ID, ANY_DATE_TIME_BEFORE_DEPARTURE);

        verify(bookingMock).bookZeroGravityExperience(ANY_TRAVELER_ID, zeroGravityExperienceMock);
    }

    @Test
    void givenPlanetWithinCruiseDepartureAndEnd_whenAddPlanetToItinerary_thenAddPlanetToItinerary() {
        when(planetMock.arriveBefore(ANY_DEPARTURE_DATE_TIME)).thenReturn(false);
        when(planetMock.departAfter(ANY_END_DATE_TIME)).thenReturn(false);

        cruise.addPlanetToItinerary(planetMock);

        verify(itineraryMock).addPlanet(planetMock);
    }

    @Test
    void givenPlanetThatArrivesBeforeDeparture_whenAddPlanetToItinerary_thenThrowInvalidPlanetDateException() {
        when(planetMock.arriveBefore(ANY_DEPARTURE_DATE_TIME)).thenReturn(true);

        Executable addPlanetToItinerary = () -> cruise.addPlanetToItinerary(planetMock);

        assertThrows(InvalidPlanetDateException.class, addPlanetToItinerary);
    }

    @Test
    void givenPlanetThatDepartsAfterEnd_whenAddPlanetToItinerary_thenThrowInvalidPlanetDateException() {
        when(planetMock.arriveBefore(ANY_DEPARTURE_DATE_TIME)).thenReturn(false);
        when(planetMock.departAfter(ANY_END_DATE_TIME)).thenReturn(true);

        Executable addPlanetToItinerary = () -> cruise.addPlanetToItinerary(planetMock);

        assertThrows(InvalidPlanetDateException.class, addPlanetToItinerary);
    }

    @Test
    void whenAddCrewMember_thenCrewAddCrewMemberBasedOnCabinAvailabilities() {
        cruise.addCrewMember(ANY_CREW_MEMBER);

        verify(crewMock).addCrewMember(ANY_CREW_MEMBER, cabinAvailabilitiesMock);
    }

    @Test
    void whenAddExcursionToPlanet_thenItineraryAddExcursionToPlanet() {
        cruise.addExcursionToPlanet(ANY_PLANET_NAME, ANY_EXCURSION);

        verify(itineraryMock).addExcursion(ANY_PLANET_NAME, ANY_EXCURSION);
    }

    @Test
    void givenNonExistingBookingId_whenBookSport_thenThrowTravelerNotFoundException() {
        Executable bookSport = () -> cruise.bookSport(ANY_BOOKING_ID, ANY_TRAVELER_ID, ANY_SPORT);

        assertThrows(TravelerNotFoundException.class, bookSport);
    }

    @Test
    void givenExistingBookingId_whenBookSport_thenBookingBookSport() {
        when(cruiseBookingsMock.findById(ANY_BOOKING_ID)).thenReturn(bookingMock);

        cruise.bookSport(ANY_BOOKING_ID, ANY_TRAVELER_ID, ANY_SPORT);

        verify(bookingMock).bookSport(ANY_TRAVELER_ID, ANY_SPORT);
    }
}