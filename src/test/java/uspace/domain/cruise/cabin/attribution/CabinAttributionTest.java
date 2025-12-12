package uspace.domain.cruise.cabin.attribution;

import org.junit.jupiter.api.Test;
import uspace.domain.cruise.booking.Booking;
import uspace.domain.cruise.booking.BookingId;
import uspace.domain.cruise.booking.invoice.Invoice;
import uspace.domain.cruise.booking.traveler.AdultTraveler;
import uspace.domain.cruise.booking.traveler.ChildTraveler;
import uspace.domain.cruise.booking.traveler.SeniorTraveler;
import uspace.domain.cruise.booking.traveler.Traveler;
import uspace.domain.cruise.booking.traveler.TravelerId;
import uspace.domain.cruise.booking.traveler.TravelerName;
import uspace.domain.cruise.booking.traveler.StandardCostMultiplier;
import uspace.domain.cruise.cabin.CabinType;
import uspace.domain.cruise.dateTime.CruiseDateTime;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CabinAttributionTest {

    @Test
    void givenBookings_whenAssignCabinsByBookingDateTime_thenReturnCabinsInChronologicalOrder() {
        Map<CabinType, List<String>> cabinsByType = createCabinsByType();
        Map<BookingId, Booking> bookings = createBookingsForDateTimeTest();
        
        List<CabinAttribution> attributions = CabinAttribution.assignCabins(
            cabinsByType,
            bookings,
            CabinAttributionCriteria.BOOKING_DATE_TIME
        );
        
        assertEquals(3, attributions.size());
        assertEquals("A1", attributions.get(0).getCabinId());
        assertEquals("A2", attributions.get(1).getCabinId());
        assertEquals("A3", attributions.get(2).getCabinId());
    }

    @Test
    void givenBookings_whenAssignCabinsByTravelers_thenReturnCabinsSortedByTravelerCountThenDate() {
        Map<CabinType, List<String>> cabinsByType = createCabinsByType();
        Map<BookingId, Booking> bookings = createBookingsForTravelersTest();
        
        List<CabinAttribution> attributions = CabinAttribution.assignCabins(
            cabinsByType,
            bookings,
            CabinAttributionCriteria.TRAVELERS
        );
        
        assertEquals(4, attributions.size());
        // R4 (3 voyageurs) devrait être en premier
        assertEquals("A1", attributions.get(0).getCabinId());
        // R2 et R3 (2 voyageurs chacun) devraient être ensuite, triés par date
        assertEquals("A2", attributions.get(1).getCabinId());
        assertEquals("A3", attributions.get(2).getCabinId());
        // R1 (1 voyageur) devrait être en dernier
        assertEquals("A4", attributions.get(3).getCabinId());
    }

    @Test
    void givenBookingsWithDifferentCabinTypes_whenAssignCabins_thenReturnCabinsGroupedByType() {
        Map<CabinType, List<String>> cabinsByType = new HashMap<>();
        cabinsByType.put(CabinType.STANDARD, Arrays.asList("A1", "A2"));
        cabinsByType.put(CabinType.DELUXE, Arrays.asList("K1", "K2"));
        cabinsByType.put(CabinType.SUITE, Arrays.asList("R1"));
        
        Map<BookingId, Booking> bookings = new HashMap<>();
        BookingId booking1 = new BookingId();
        BookingId booking2 = new BookingId();
        BookingId booking3 = new BookingId();
        
        bookings.put(booking1, createBooking(CabinType.STANDARD, LocalDateTime.of(2084, 1, 1, 10, 0), 
            List.of(createAdultTraveler())));
        bookings.put(booking2, createBooking(CabinType.DELUXE, LocalDateTime.of(2084, 1, 2, 10, 0), 
            List.of(createAdultTraveler())));
        bookings.put(booking3, createBooking(CabinType.SUITE, LocalDateTime.of(2084, 1, 3, 10, 0), 
            List.of(createAdultTraveler())));
        
        List<CabinAttribution> attributions = CabinAttribution.assignCabins(
            cabinsByType,
            bookings,
            CabinAttributionCriteria.BOOKING_DATE_TIME
        );
        
        assertEquals(3, attributions.size());
        assertEquals("A1", attributions.get(0).getCabinId());
        assertEquals(CabinType.STANDARD, attributions.get(0).getCategory());
        assertEquals("K1", attributions.get(1).getCabinId());
        assertEquals(CabinType.DELUXE, attributions.get(1).getCategory());
        assertEquals("R1", attributions.get(2).getCabinId());
        assertEquals(CabinType.SUITE, attributions.get(2).getCategory());
    }

    @Test
    void givenNoBookings_whenAssignCabins_thenReturnEmptyList() {
        Map<CabinType, List<String>> cabinsByType = createCabinsByType();
        Map<BookingId, Booking> bookings = new HashMap<>();
        
        List<CabinAttribution> attributions = CabinAttribution.assignCabins(
            cabinsByType,
            bookings,
            CabinAttributionCriteria.BOOKING_DATE_TIME
        );
        
        assertTrue(attributions.isEmpty());
    }

    private Map<CabinType, List<String>> createCabinsByType() {
        Map<CabinType, List<String>> cabinsByType = new HashMap<>();
        cabinsByType.put(CabinType.STANDARD, Arrays.asList("A1", "A2", "A3", "A4", "A5"));
        return cabinsByType;
    }

    private Map<BookingId, Booking> createBookingsForDateTimeTest() {
        Map<BookingId, Booking> bookings = new HashMap<>();
        BookingId booking1 = new BookingId();
        BookingId booking2 = new BookingId();
        BookingId booking3 = new BookingId();
        
        bookings.put(booking1, createBooking(CabinType.STANDARD, LocalDateTime.of(2084, 1, 1, 10, 0), 
            List.of(createAdultTraveler())));
        bookings.put(booking2, createBooking(CabinType.STANDARD, LocalDateTime.of(2084, 2, 1, 10, 0), 
            List.of(createAdultTraveler())));
        bookings.put(booking3, createBooking(CabinType.STANDARD, LocalDateTime.of(2084, 3, 1, 10, 0), 
            List.of(createAdultTraveler())));
        
        return bookings;
    }

    private Map<BookingId, Booking> createBookingsForTravelersTest() {
        Map<BookingId, Booking> bookings = new HashMap<>();
        BookingId booking1 = new BookingId(); // 1 voyageur, date ancienne
        BookingId booking2 = new BookingId(); // 2 voyageurs, date moyenne
        BookingId booking3 = new BookingId(); // 2 voyageurs, date récente
        BookingId booking4 = new BookingId(); // 3 voyageurs, date très récente
        
        bookings.put(booking1, createBooking(CabinType.STANDARD, LocalDateTime.of(2084, 1, 1, 10, 0), 
            List.of(createAdultTraveler())));
        bookings.put(booking2, createBooking(CabinType.STANDARD, LocalDateTime.of(2084, 2, 1, 10, 0), 
            List.of(createAdultTraveler(), createAdultTraveler())));
        bookings.put(booking3, createBooking(CabinType.STANDARD, LocalDateTime.of(2084, 3, 1, 10, 0), 
            List.of(createAdultTraveler(), createChildTraveler())));
        bookings.put(booking4, createBooking(CabinType.STANDARD, LocalDateTime.of(2084, 4, 1, 10, 0), 
            List.of(createAdultTraveler(), createChildTraveler(), createSeniorTraveler())));
        
        return bookings;
    }

    private Booking createBooking(CabinType cabinType, LocalDateTime bookingDateTime, List<Traveler> travelers) {
        return new Booking(
            new BookingId(),
            travelers,
            cabinType,
            new CruiseDateTime(bookingDateTime),
            new Invoice()
        );
    }

    private AdultTraveler createAdultTraveler() {
        return new AdultTraveler(new TravelerId(), new TravelerName("Adult"), 
            new ArrayList<>(), new StandardCostMultiplier(2), new ArrayList<>());
    }

    private ChildTraveler createChildTraveler() {
        return new ChildTraveler(new TravelerId(), new TravelerName("Child"), 
            new ArrayList<>(), new StandardCostMultiplier(1), new ArrayList<>());
    }

    private SeniorTraveler createSeniorTraveler() {
        return new SeniorTraveler(new TravelerId(), new TravelerName("Senior"), 
            new ArrayList<>(), new StandardCostMultiplier(1.5f), new ArrayList<>());
    }
}

