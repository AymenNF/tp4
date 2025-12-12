package uspace.domain.cruise.booking.newBooking;

import uspace.domain.cruise.booking.exceptions.InvalidBookingDateException;
import uspace.domain.cruise.cabin.CabinType;
import uspace.domain.cruise.dateTime.CruiseDateTime;
import uspace.domain.cruise.exceptions.NoTravelerToBookException;

import java.util.List;

public class NewBooking {
    private List<NewBookingTraveler> travelers;
    private CabinType cabinType;
    private CruiseDateTime bookingDateTime;
    public NewBooking(List<NewBookingTraveler> travelers, CabinType cabinType, CruiseDateTime bookingDateTime) {
        this.travelers = travelers;
        this.cabinType = cabinType;
        this.bookingDateTime = bookingDateTime;
    }

    public List<NewBookingTraveler> getTravelers() {
        return travelers;
    }

    public CabinType getCabinType() {
        return cabinType;
    }

    public CruiseDateTime getBookingDateTime() {
        return bookingDateTime;
    }

    public void validate(CruiseDateTime cruiseDepartureDateTime) {
        if (travelers.isEmpty()) {
            throw new NoTravelerToBookException();
        }

        if (!bookingDateIsBeforeCruiseDeparture(cruiseDepartureDateTime)) {
            throw new InvalidBookingDateException();
        }
    }

    public int calculateDaysBefore(CruiseDateTime cruiseDepartureDateTime) {
        return bookingDateTime.calculateDaysBefore(cruiseDepartureDateTime);
    }

    public int getTravelersNumber() {
        return travelers.size();
    }

    private boolean bookingDateIsBeforeCruiseDeparture(CruiseDateTime cruiseDepartureDateTime) {
        return bookingDateTime.isBefore(cruiseDepartureDateTime);
    }
}
