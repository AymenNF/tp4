package uspace.domain.cruise.booking.bookingCostCalculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uspace.domain.cruise.money.Money;

import static org.junit.jupiter.api.Assertions.*;

class BookingCostCalculatorFactoryTest {

    private static final int ANY_LATE_FEE_BOOKING_MINIMUM_DAYS_BEFORE_DEPARTURE = 7;
    private static final float ANY_LATE_FEE_MULTIPLIER = 1.5f;
    private static final Money ANY_CABIN_BASE_COST_PER_PERSON = new Money(100);
    private BookingCostCalculatorFactory bookingCostCalculatorFactory;

    @BeforeEach
    void createBookingCostCalculatorFactory() {
        bookingCostCalculatorFactory = new BookingCostCalculatorFactory(ANY_LATE_FEE_BOOKING_MINIMUM_DAYS_BEFORE_DEPARTURE, ANY_LATE_FEE_MULTIPLIER);
    }

    @Test
    void givenDaysBeforeDepartureLessThanLateFeeBookingMinimumDaysBeforeDeparture_whenCreateBookingCostCalculator_thenCreateLateBookingCostCalculator() {
        int daysBeforeDepartureLessThanLateFeeBookingMinimumDaysBeforeDeparture = 6;

        BookingCostCalculator bookingCostCalculator =
                bookingCostCalculatorFactory.createBookingCostCalculator(daysBeforeDepartureLessThanLateFeeBookingMinimumDaysBeforeDeparture, ANY_CABIN_BASE_COST_PER_PERSON);

        assertTrue(bookingCostCalculator instanceof LateBookingCostCalculator);
    }

    @Test
    void givenDaysBeforeDepartureEqualToLateFeeBookingMinimumDaysBeforeDeparture_whenCreateBookingCostCalculator_thenCreateStandardBookingCostCalculator() {
        BookingCostCalculator bookingCostCalculator = bookingCostCalculatorFactory.createBookingCostCalculator(ANY_LATE_FEE_BOOKING_MINIMUM_DAYS_BEFORE_DEPARTURE, ANY_CABIN_BASE_COST_PER_PERSON);

        assertTrue(bookingCostCalculator instanceof StandardBookingCostCalculator);
    }

    @Test
    void givenDaysBeforeDepartureGreaterThanLateFeeBookingMinimumDaysBeforeDeparture_whenCreateBookingCostCalculator_thenCreateStandardBookingCostCalculator() {
        int daysBeforeDepartureGreaterThanLateFeeBookingMinimumDaysBeforeDeparture = 8;

        BookingCostCalculator bookingCostCalculator =
                bookingCostCalculatorFactory.createBookingCostCalculator(daysBeforeDepartureGreaterThanLateFeeBookingMinimumDaysBeforeDeparture, ANY_CABIN_BASE_COST_PER_PERSON);

        assertTrue(bookingCostCalculator instanceof StandardBookingCostCalculator);
    }
}