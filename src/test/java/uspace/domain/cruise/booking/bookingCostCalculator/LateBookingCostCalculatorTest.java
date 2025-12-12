package uspace.domain.cruise.booking.bookingCostCalculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uspace.domain.cruise.booking.traveler.AdultTraveler;
import uspace.domain.cruise.booking.traveler.ChildTraveler;
import uspace.domain.cruise.booking.traveler.Traveler;
import uspace.domain.cruise.money.Money;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LateBookingCostCalculatorTest {

    private static final float ANY_LATE_FEE_MULTIPLIER = 1.5f;
    private static final Money ANY_CABIN_BASE_COST_PER_PERSON = new Money(100);
    private LateBookingCostCalculator lateBookingCostCalculator;

    @BeforeEach
    void createLateBookingCostCalculator() {
        lateBookingCostCalculator = new LateBookingCostCalculator(ANY_CABIN_BASE_COST_PER_PERSON, ANY_LATE_FEE_MULTIPLIER);
    }

    @Test
    void whenCalculateBookingCost_thenReturnCabinBaseCOstMultiplyByNumberOfTravelersMultiplyByLateFeeMultiplier() {
        List<Traveler> multipleTravelers = List.of(new AdultTraveler(), new ChildTraveler());
        Money bookingCostExpected = new Money(300);

        Money bookingCost = lateBookingCostCalculator.calculateBookingCost(multipleTravelers);

        assertEquals(bookingCostExpected, bookingCost);
    }
}