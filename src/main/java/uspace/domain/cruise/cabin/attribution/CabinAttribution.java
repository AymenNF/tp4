package uspace.domain.cruise.cabin.attribution;

import uspace.domain.cruise.booking.Booking;
import uspace.domain.cruise.booking.BookingId;
import uspace.domain.cruise.cabin.CabinType;

import java.util.*;

public class CabinAttribution {
    private final String cabinId;
    private final BookingId bookingId;
    private final CabinType category;

    public CabinAttribution(String cabinId, BookingId bookingId, CabinType category) {
        this.cabinId = cabinId;
        this.bookingId = bookingId;
        this.category = category;
    }

    public String getCabinId() {
        return cabinId;
    }

    public BookingId getBookingId() {
        return bookingId;
    }

    public CabinType getCategory() {
        return category;
    }

    public static List<CabinAttribution> assignCabins(
            Map<CabinType, List<String>> cabinsByType,
            Map<BookingId, Booking> bookings,
            CabinAttributionCriteria criteria) {
        
        List<CabinAttribution> attributions = new ArrayList<>();
        
        // Trier les bookings selon le critère
        List<Map.Entry<BookingId, Booking>> sortedBookings = sortBookings(bookings, criteria);
        
        // Créer des indexeurs pour chaque type de cabine
        Map<CabinType, Integer> cabinIndices = new HashMap<>();
        for (CabinType cabinType : CabinType.values()) {
            cabinIndices.put(cabinType, 0);
        }
        
        // Attribuer les cabines dans l'ordre des bookings triés
        for (Map.Entry<BookingId, Booking> entry : sortedBookings) {
            Booking booking = entry.getValue();
            CabinType cabinType = booking.getCabinType();
            List<String> cabins = cabinsByType.get(cabinType);
            
            if (cabins != null) {
                int currentIndex = cabinIndices.get(cabinType);
                if (currentIndex < cabins.size()) {
                    attributions.add(new CabinAttribution(
                        cabins.get(currentIndex),
                        entry.getKey(),
                        cabinType
                    ));
                    cabinIndices.put(cabinType, currentIndex + 1);
                }
            }
        }
        
        return attributions;
    }

    private static List<Map.Entry<BookingId, Booking>> sortBookings(
            Map<BookingId, Booking> bookings,
            CabinAttributionCriteria criteria) {
        
        List<Map.Entry<BookingId, Booking>> bookingList = new ArrayList<>(bookings.entrySet());
        
        switch (criteria) {
            case BOOKING_DATE_TIME:
                bookingList.sort(Comparator.comparing(
                    (Map.Entry<BookingId, Booking> entry) -> entry.getValue().getBookingDateTime().getLocalDateTime()
                ));
                break;
            case TRAVELERS:
                bookingList.sort(Comparator
                    .<Map.Entry<BookingId, Booking>, Integer>comparing(entry -> 
                        entry.getValue().getTravelers().size())
                    .reversed()
                    .thenComparing(entry -> 
                        entry.getValue().getBookingDateTime().getLocalDateTime())
                );
                break;
        }
        
        return bookingList;
    }
}

