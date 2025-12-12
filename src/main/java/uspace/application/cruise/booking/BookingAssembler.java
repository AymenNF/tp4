package uspace.application.cruise.booking;

import jakarta.inject.Inject;
import uspace.application.cruise.booking.dtos.BookingDto;
import uspace.application.cruise.booking.dtos.BookingTravelerDto;
import uspace.application.cruise.booking.dtos.newBooking.NewBookingDto;
import uspace.application.cruise.booking.dtos.newBooking.NewBookingTravelerDto;
import uspace.domain.cruise.booking.Booking;
import uspace.domain.cruise.booking.newBooking.NewBooking;
import uspace.domain.cruise.booking.newBooking.NewBookingTraveler;
import uspace.domain.cruise.booking.traveler.Traveler;
import uspace.domain.cruise.cabin.CabinType;
import uspace.domain.cruise.dateTime.CruiseDateTime;

import java.time.LocalDateTime;
import java.util.List;

public class BookingAssembler {

    private final BookingTravelerAssembler bookingTravelerAssembler;

    @Inject
    public BookingAssembler(BookingTravelerAssembler bookingTravelerAssembler) {
        this.bookingTravelerAssembler = bookingTravelerAssembler;
    }

    public BookingDto toDto(String cruiseId, Booking booking) {
        List<BookingTravelerDto> travelerDtos = createBookingTravelerDtos(booking.getTravelers());

        return new BookingDto(
                    cruiseId,
                    booking.getId().toString(),
                    travelerDtos,
                    booking.getCabinType().toString(),
                    booking.getBookingDateTime().toString(),
                    roundToTwoDecimal(booking.getCost().toFloat()));
    }

    public NewBooking toNewBooking(NewBookingDto newBookingDto, LocalDateTime bookingDateTime) {
        List<NewBookingTraveler> newBookingTravelers = createNewBookingTravelers(newBookingDto.travelers);
        CabinType cabinType = CabinType.fromString(newBookingDto.cabinType);
        CruiseDateTime bookingCruiseDateTime = new CruiseDateTime(bookingDateTime);
        return new NewBooking(newBookingTravelers, cabinType, bookingCruiseDateTime);
    }

    private List<BookingTravelerDto> createBookingTravelerDtos(List<Traveler> travelers) {
        return travelers.stream().map(bookingTravelerAssembler::toDto).toList();
    }

    private List<NewBookingTraveler> createNewBookingTravelers(List<NewBookingTravelerDto> travelers) {
        return travelers.stream().map(bookingTravelerAssembler::toNewBookingTraveler).toList();
    }
    private float roundToTwoDecimal(float value) {
        return (float) (Math.round(value * 100.0) / 100.0);
    }
}
