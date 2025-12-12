package uspace.domain.cruise.booking.bookingCostCalculator;

import uspace.domain.cruise.booking.traveler.Traveler;
import uspace.domain.cruise.money.Money;

import java.util.List;

public interface BookingCostCalculator {
    Money calculateBookingCost(List<Traveler> travelers);
}
