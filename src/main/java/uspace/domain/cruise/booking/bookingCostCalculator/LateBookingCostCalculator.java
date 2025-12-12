package uspace.domain.cruise.booking.bookingCostCalculator;

import uspace.domain.cruise.booking.traveler.Traveler;
import uspace.domain.cruise.money.Money;

import java.util.List;

public class LateBookingCostCalculator implements BookingCostCalculator {

    private final Money cabinBaseCostPerPerson;
    private final float lateBookingCostMultiplier;


    public LateBookingCostCalculator(Money cabinBaseCostPerPerson, float lateBookingCostMultiplier) {
        this.lateBookingCostMultiplier = lateBookingCostMultiplier;
        this.cabinBaseCostPerPerson = cabinBaseCostPerPerson;
    }

    public Money calculateBookingCost(List<Traveler> travelers) {
        return cabinBaseCostPerPerson.multiply(travelers.size()).multiply(lateBookingCostMultiplier);
    }
}
