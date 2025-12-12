package uspace.domain.cruise.booking;

import jakarta.inject.Inject;
import uspace.domain.cruise.booking.bookingCostCalculator.BookingCostCalculator;
import uspace.domain.cruise.booking.bookingCostCalculator.BookingCostCalculatorFactory;
import uspace.domain.cruise.booking.newBooking.NewBooking;
import uspace.domain.cruise.dateTime.CruiseDateTime;
import uspace.domain.cruise.money.Money;

public class BookingCreator {
    private final BookingCostCalculatorFactory bookingCostCalculatorFactory;
    private final BookingFactory bookingFactory;

    @Inject
    public BookingCreator(BookingCostCalculatorFactory bookingCostCalculatorFactory, BookingFactory bookingFactory) {
        this.bookingCostCalculatorFactory = bookingCostCalculatorFactory;
        this.bookingFactory = bookingFactory;
    }

    public Booking createBooking(NewBooking newBooking, CruiseDateTime cruiseDepartureDateTime, Money cabinBaseCostPerPerson) {
        newBooking.validate(cruiseDepartureDateTime);

        BookingCostCalculator bookingCostCalculator = createBookingCostCalculator(newBooking, cruiseDepartureDateTime, cabinBaseCostPerPerson);
        return bookingFactory.create(newBooking, bookingCostCalculator);
    }

    private BookingCostCalculator createBookingCostCalculator(NewBooking newBooking, CruiseDateTime cruiseDepartureDateTime,
                           Money cabinBaseCostPerPerson) {
        int daysUntilDeparture = newBooking.calculateDaysBefore(cruiseDepartureDateTime);
        return  bookingCostCalculatorFactory.createBookingCostCalculator(daysUntilDeparture, cabinBaseCostPerPerson);
    }
}
