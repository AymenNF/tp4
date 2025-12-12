package uspace.domain.cruise.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uspace.domain.cruise.booking.invoice.Invoice;
import uspace.domain.cruise.booking.traveler.Traveler;
import uspace.domain.cruise.booking.traveler.TravelerId;
import uspace.domain.cruise.booking.traveler.exceptions.TravelerNotFoundException;
import uspace.domain.cruise.cabin.CabinType;
import uspace.domain.cruise.dateTime.CruiseDateTime;
import uspace.domain.cruise.sport.Sport;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingTest {

    private static final BookingId ANY_BOOKING_ID = new BookingId("ANY_BOOKING_ID");
    private static final TravelerId ANY_TRAVELER_ID = new TravelerId();
    private static final CabinType ANY_CABIN_TYPE = CabinType.SUITE;
    private static final CruiseDateTime ANY_BOOKING_DATE_TIME = new CruiseDateTime(LocalDateTime.now());
    private static final Invoice ANY_INVOICE = new Invoice();
    private static final Sport ANY_SPORT = new Sport();
    private Booking booking;
    @Mock
    private Traveler travelerMock;

    @Test
    void givenNonExistingTraveler_whenBookSport_thenThrowTravelerNotFoundException() {
        givenNonExistingTraveler();

        Executable action = () -> booking.bookSport(ANY_TRAVELER_ID, ANY_SPORT);

        assertThrows(TravelerNotFoundException.class, action);
    }

    @Test
    void givenExistingTraveler_whenBookSport_thenTravelerBookSport() {
        givenExistingTraveler();

        booking.bookSport(ANY_TRAVELER_ID, ANY_SPORT);

        verify(travelerMock).bookSport(ANY_SPORT);
    }

    private void givenNonExistingTraveler() {
        booking = new Booking(ANY_BOOKING_ID, List.of(), ANY_CABIN_TYPE, ANY_BOOKING_DATE_TIME, ANY_INVOICE);
    }

    private void givenExistingTraveler() {
        when(travelerMock.getId()).thenReturn(ANY_TRAVELER_ID);
        booking = new Booking(ANY_BOOKING_ID, List.of(travelerMock), ANY_CABIN_TYPE, ANY_BOOKING_DATE_TIME, ANY_INVOICE);
    }
}