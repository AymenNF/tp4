package uspace.domain.cruise.emergencyShuttle;

import org.junit.jupiter.api.Test;
import uspace.domain.cruise.*;
import uspace.domain.cruise.booking.Booking;
import uspace.domain.cruise.booking.BookingId;
import uspace.domain.cruise.booking.CruiseBookings;
import uspace.domain.cruise.booking.CruiseBookingsId;
import uspace.domain.cruise.booking.invoice.Invoice;
import uspace.domain.cruise.booking.traveler.AdultTraveler;
import uspace.domain.cruise.booking.traveler.Traveler;
import uspace.domain.cruise.booking.traveler.TravelerId;
import uspace.domain.cruise.booking.traveler.TravelerName;
import uspace.domain.cruise.booking.traveler.StandardCostMultiplier;
import uspace.domain.cruise.cabin.CabinAvailabilities;
import uspace.domain.cruise.cabin.CabinAvailabilitiesId;
import uspace.domain.cruise.cabin.CabinPriceRegistry;
import uspace.domain.cruise.cabin.CabinType;
import uspace.domain.cruise.crew.Crew;
import uspace.domain.cruise.crew.CrewId;
import uspace.domain.cruise.crew.CrewMemberCollection;
import uspace.domain.cruise.crew.crewMember.CrewMember;
import uspace.domain.cruise.crew.crewMember.CrewMemberName;
import uspace.domain.cruise.crew.crewMember.employeeId.EmployeeId;
import uspace.domain.cruise.crew.crewMember.employeeId.EmployeeIdValidatorRegex;
import uspace.domain.cruise.dateTime.CruiseDateTime;
import uspace.domain.cruise.itinerary.Itinerary;
import uspace.domain.cruise.itinerary.ItineraryId;
import uspace.domain.cruise.money.Money;
import uspace.domain.cruise.zeroGravityExperience.ZeroGravityExperience;
import uspace.domain.cruise.zeroGravityExperience.ZeroGravityExperienceId;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class EmergencyShuttleAssignerTest {

    @Test
    void givenCruiseWithTravelers_whenAssignShuttles_thenAssignToRescueShipsFirst() {
        Cruise cruise = createCruiseWithTravelers(60); // 60 travelers, should fill 1 RESCUE_SHIP (50) and 10 to STANDARD_SHUTTLE
        
        EmergencyShuttleAssigner assigner = new EmergencyShuttleAssigner();
        EmergencyShuttleManifest manifest = assigner.assignShuttles(cruise);
        
        List<EmergencyShuttle> shuttles = manifest.getShuttles();
        assertFalse(shuttles.isEmpty());
        
        // First shuttle should be RESCUE_SHIP
        assertEquals(EmergencyShuttleType.RESCUE_SHIP, shuttles.get(0).getType());
        assertEquals(50, shuttles.get(0).getCurrentOccupancy());
        
        // Second shuttle should be STANDARD_SHUTTLE
        assertEquals(EmergencyShuttleType.STANDARD_SHUTTLE, shuttles.get(1).getType());
        assertEquals(10, shuttles.get(1).getCurrentOccupancy());
    }

    @Test
    void givenCruiseWithTravelersAndCrew_whenAssignShuttles_thenFillLastShuttleBeforeOpeningNew() {
        Cruise cruise = createCruiseWithTravelersAndCrew(45, 10); // 45 travelers + 10 crew = 55 total
        
        EmergencyShuttleAssigner assigner = new EmergencyShuttleAssigner();
        EmergencyShuttleManifest manifest = assigner.assignShuttles(cruise);
        
        List<EmergencyShuttle> shuttles = manifest.getShuttles();
        
        // Should have 1 RESCUE_SHIP with 50 people (45 travelers + 5 crew)
        // And 1 STANDARD_SHUTTLE with 5 crew
        assertEquals(2, shuttles.size());
        assertEquals(EmergencyShuttleType.RESCUE_SHIP, shuttles.get(0).getType());
        assertEquals(50, shuttles.get(0).getCurrentOccupancy());
        assertEquals(EmergencyShuttleType.STANDARD_SHUTTLE, shuttles.get(1).getType());
        assertEquals(5, shuttles.get(1).getCurrentOccupancy());
    }

    @Test
    void givenCruiseWithManyTravelers_whenAssignShuttles_thenRespectRescueShipLimit() {
        Cruise cruise = createCruiseWithTravelers(300); // 300 travelers, but only 5 RESCUE_SHIPs (250 capacity)
        
        EmergencyShuttleAssigner assigner = new EmergencyShuttleAssigner();
        EmergencyShuttleManifest manifest = assigner.assignShuttles(cruise);
        
        List<EmergencyShuttle> shuttles = manifest.getShuttles();
        
        // Should have 5 RESCUE_SHIPs (max) and some STANDARD_SHUTTLEs
        long rescueShipCount = shuttles.stream()
                .filter(s -> s.getType() == EmergencyShuttleType.RESCUE_SHIP)
                .count();
        assertEquals(5, rescueShipCount);
        
        // All RESCUE_SHIPs should be full
        shuttles.stream()
                .filter(s -> s.getType() == EmergencyShuttleType.RESCUE_SHIP)
                .forEach(s -> assertEquals(50, s.getCurrentOccupancy()));
    }

    @Test
    void givenEmptyCruise_whenAssignShuttles_thenReturnEmptyManifest() {
        Cruise cruise = createEmptyCruise();
        
        EmergencyShuttleAssigner assigner = new EmergencyShuttleAssigner();
        EmergencyShuttleManifest manifest = assigner.assignShuttles(cruise);
        
        assertTrue(manifest.getShuttles().isEmpty());
        assertEquals(0, manifest.getTotalCost().toFloat());
    }

    @Test
    void givenCruiseWithShuttles_whenCalculateCost_thenReturnCorrectTotalCost() {
        Cruise cruise = createCruiseWithTravelers(70); // 1 RESCUE_SHIP (150k) + 1 STANDARD_SHUTTLE (50k) = 200k
        
        EmergencyShuttleAssigner assigner = new EmergencyShuttleAssigner();
        EmergencyShuttleManifest manifest = assigner.assignShuttles(cruise);
        
        // 1 RESCUE_SHIP = 150000, 1 STANDARD_SHUTTLE = 50000, total = 200000
        assertEquals(200000, manifest.getTotalCost().toFloat());
    }

    @Test
    void givenCruiseWithShuttles_whenGetManifest_thenExcludeEmptyShuttles() {
        Cruise cruise = createCruiseWithTravelers(50); // Exactly 1 RESCUE_SHIP full
        
        EmergencyShuttleAssigner assigner = new EmergencyShuttleAssigner();
        EmergencyShuttleManifest manifest = assigner.assignShuttles(cruise);
        
        List<EmergencyShuttle> shuttles = manifest.getShuttles();
        // Should only have 1 shuttle (the full RESCUE_SHIP)
        assertEquals(1, shuttles.size());
        assertFalse(shuttles.get(0).isEmpty());
    }

    private Cruise createCruiseWithTravelers(int travelerCount) {
        CruiseId cruiseId = new CruiseId("JUPITER_MOONS_EXPLORATION_2085");
        CruiseDateTime departure = new CruiseDateTime(LocalDateTime.of(2085, 1, 25, 12, 0));
        CruiseDateTime end = new CruiseDateTime(LocalDateTime.of(2085, 2, 1, 12, 0));
        
        Map<CabinType, Money> prices = new HashMap<>();
        prices.put(CabinType.STANDARD, new Money(1000));
        CabinPriceRegistry priceRegistry = new CabinPriceRegistry(prices);
        
        Map<CabinType, Integer> availabilities = new HashMap<>();
        availabilities.put(CabinType.STANDARD, 100);
        CabinAvailabilities cabinAvailabilities = new CabinAvailabilities(new CabinAvailabilitiesId(), availabilities);
        
        CruiseBookings bookings = new CruiseBookings(new CruiseBookingsId(), new HashMap<>());
        
        // Create bookings with travelers
        for (int i = 0; i < travelerCount; i++) {
            BookingId bookingId = new BookingId();
            List<Traveler> travelers = List.of(createAdultTraveler());
            Booking booking = new Booking(
                    bookingId,
                    travelers,
                    CabinType.STANDARD,
                    departure,
                    new Invoice()
            );
            bookings.add(booking);
        }
        
        ZeroGravityExperience zeroG = new ZeroGravityExperience(new ZeroGravityExperienceId(), 10);
        Itinerary itinerary = new Itinerary(new ItineraryId(), new ArrayList<>(), 3);
        Crew crew = new Crew(new CrewId(), new CrewMemberCollection(new HashMap<>()), 
                EmployeeIdValidatorRegex.for3UppercaseLettersFollowedBy3Digits());
        
        return new Cruise(cruiseId, departure, end, priceRegistry, cabinAvailabilities, 
                bookings, zeroG, itinerary, crew);
    }

    private Cruise createCruiseWithTravelersAndCrew(int travelerCount, int crewCount) {
        Cruise cruise = createCruiseWithTravelers(travelerCount);
        
        // Add crew members
        for (int i = 0; i < crewCount; i++) {
            CrewMember crewMember = new CrewMember(
                    new EmployeeId("ABC" + String.format("%03d", i)),
                    new CrewMemberName("Crew Member " + i)
            );
            cruise.addCrewMember(crewMember);
        }
        
        return cruise;
    }

    private Cruise createEmptyCruise() {
        CruiseId cruiseId = new CruiseId("JUPITER_MOONS_EXPLORATION_2085");
        CruiseDateTime departure = new CruiseDateTime(LocalDateTime.of(2085, 1, 25, 12, 0));
        CruiseDateTime end = new CruiseDateTime(LocalDateTime.of(2085, 2, 1, 12, 0));
        
        Map<CabinType, Money> prices = new HashMap<>();
        prices.put(CabinType.STANDARD, new Money(1000));
        CabinPriceRegistry priceRegistry = new CabinPriceRegistry(prices);
        
        Map<CabinType, Integer> availabilities = new HashMap<>();
        availabilities.put(CabinType.STANDARD, 100);
        CabinAvailabilities cabinAvailabilities = new CabinAvailabilities(new CabinAvailabilitiesId(), availabilities);
        
        CruiseBookings bookings = new CruiseBookings(new CruiseBookingsId(), new HashMap<>());
        ZeroGravityExperience zeroG = new ZeroGravityExperience(new ZeroGravityExperienceId(), 10);
        Itinerary itinerary = new Itinerary(new ItineraryId(), new ArrayList<>(), 3);
        Crew crew = new Crew(new CrewId(), new CrewMemberCollection(new HashMap<>()), 
                EmployeeIdValidatorRegex.for3UppercaseLettersFollowedBy3Digits());
        
        return new Cruise(cruiseId, departure, end, priceRegistry, cabinAvailabilities, 
                bookings, zeroG, itinerary, crew);
    }

    private AdultTraveler createAdultTraveler() {
        return new AdultTraveler(
                new TravelerId(),
                new TravelerName("Traveler"),
                new ArrayList<>(),
                new StandardCostMultiplier(1.0f),
                new ArrayList<>()
        );
    }
}

