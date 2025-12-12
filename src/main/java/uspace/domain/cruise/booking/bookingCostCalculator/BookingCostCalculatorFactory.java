package uspace.domain.cruise.booking.bookingCostCalculator;

import uspace.Configuration;
import uspace.domain.cruise.money.Money;

public class BookingCostCalculatorFactory
{
    private final int lateFeeBookingMinimumDaysBeforeDeparture;
    private final float lateFeeMultiplier;

    public BookingCostCalculatorFactory()
    {
        this.lateFeeMultiplier = Configuration.LATE_FEE_MULTIPLIER;
        this.lateFeeBookingMinimumDaysBeforeDeparture = Configuration.LATE_FEE_BOOKING_MINIMUM_DAYS_BEFORE_DEPARTURE;
    }

    public BookingCostCalculatorFactory(int lateFeeBookingMinimumDaysBeforeDeparture, float lateFeeMultiplier)
    {
        this.lateFeeBookingMinimumDaysBeforeDeparture = lateFeeBookingMinimumDaysBeforeDeparture;
        this.lateFeeMultiplier = lateFeeMultiplier;
    }

    public BookingCostCalculator createBookingCostCalculator(int daysBeforeDeparture, Money cabinBaseCostPerPerson)
    {
        if (daysBeforeDeparture < lateFeeBookingMinimumDaysBeforeDeparture)
        {
            return new LateBookingCostCalculator(cabinBaseCostPerPerson, lateFeeMultiplier);
        }

        return new StandardBookingCostCalculator(cabinBaseCostPerPerson);
    }
}
