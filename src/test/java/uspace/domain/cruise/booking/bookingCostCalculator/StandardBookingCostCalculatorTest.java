package uspace.domain.cruise.booking.bookingCostCalculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uspace.domain.cruise.booking.traveler.Traveler;
import uspace.domain.cruise.money.Money;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StandardBookingCostCalculatorTest {

    private static final Money ANY_CABIN_BASE_COST_PER_PERSON = new Money(100);
    private static final Money ANY_TRAVELER_STANDARD_BOOKING_COST = new Money(100);
    private static final Money ANOTHER_TRAVELER_STANDARD_BOOKING_COST = new Money(200);

    private StandardBookingCostCalculator standardBookingCostCalculator;
    @Mock
    private Traveler travelerMock;
    @Mock
    private Traveler anotherTravelerMock;

    @BeforeEach
    void createStandardBookingCostCalculator() {
        standardBookingCostCalculator = new StandardBookingCostCalculator(ANY_CABIN_BASE_COST_PER_PERSON);
    }

    @Test
    void whenCalculateBookingCost_thenReturnTotalOfTravelersStandardBookingCost() {
        when(travelerMock.calculateStandardBookingCost(ANY_CABIN_BASE_COST_PER_PERSON)).thenReturn(ANY_TRAVELER_STANDARD_BOOKING_COST);
        when(anotherTravelerMock.calculateStandardBookingCost(ANY_CABIN_BASE_COST_PER_PERSON)).thenReturn(ANOTHER_TRAVELER_STANDARD_BOOKING_COST);
        List<Traveler> multipleTravelers = List.of(travelerMock, anotherTravelerMock);
        Money bookingCostExpected = new Money(300);

        Money bookingCost = standardBookingCostCalculator.calculateBookingCost(multipleTravelers);

        assertEquals(bookingCostExpected, bookingCost);
    }
}