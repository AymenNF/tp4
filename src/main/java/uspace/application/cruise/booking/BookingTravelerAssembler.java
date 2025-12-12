package uspace.application.cruise.booking;

import uspace.application.cruise.booking.dtos.BookingTravelerDto;
import uspace.application.cruise.booking.dtos.newBooking.NewBookingTravelerDto;
import uspace.domain.cruise.booking.newBooking.NewBookingTraveler;
import uspace.domain.cruise.booking.traveler.Traveler;

import java.util.List;

public class BookingTravelerAssembler {
    public BookingTravelerDto toDto(Traveler traveler) {
        List<String> badges = traveler.getBadges().stream().map(Enum::toString).toList();

        return new BookingTravelerDto(
                traveler.getId().toString(),
                traveler.getName().toString(),
                traveler.getCategory().toString(),
                badges);
    }

    public NewBookingTraveler toNewBookingTraveler(NewBookingTravelerDto newBookingTravelerDto) {
        return new NewBookingTraveler(newBookingTravelerDto.name, newBookingTravelerDto.category);
    }
}
