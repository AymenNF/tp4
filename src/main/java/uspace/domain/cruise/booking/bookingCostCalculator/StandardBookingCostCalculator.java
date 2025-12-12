package uspace.domain.cruise.booking.bookingCostCalculator;

import uspace.domain.cruise.booking.traveler.Traveler;
import uspace.domain.cruise.money.Money;

import java.util.List;


public class StandardBookingCostCalculator implements BookingCostCalculator {

    private final Money cabinBaseCostPerPerson;

    public StandardBookingCostCalculator(Money cabinBaseCostPerPerson) {
        this.cabinBaseCostPerPerson = cabinBaseCostPerPerson;
    }

    public Money calculateBookingCost(List<Traveler> travelers) {
        Money totalCost = new Money(0);

        for (Traveler traveler : travelers) {
            totalCost = totalCost.add(traveler.calculateStandardBookingCost(cabinBaseCostPerPerson));
        }

        return totalCost;
    }
}
