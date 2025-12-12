package uspace.domain.cruise.booking;

import jakarta.inject.Inject;
import uspace.domain.cruise.booking.bookingCostCalculator.BookingCostCalculator;
import uspace.domain.cruise.booking.invoice.Invoice;
import uspace.domain.cruise.booking.invoice.InvoiceFactory;
import uspace.domain.cruise.booking.newBooking.NewBooking;
import uspace.domain.cruise.booking.newBooking.NewBookingTraveler;
import uspace.domain.cruise.booking.traveler.TravelerCategory;
import uspace.domain.cruise.booking.traveler.TravelerFactory;
import uspace.domain.cruise.booking.traveler.Traveler;
import uspace.domain.cruise.money.Money;

import java.util.List;

public class BookingFactory {
    private final TravelerFactory travelerFactory;
    private final InvoiceFactory invoiceFactory;

    @Inject
    public BookingFactory(TravelerFactory travelerFactory, InvoiceFactory invoiceFactory) {
        this.travelerFactory = travelerFactory;
        this.invoiceFactory = invoiceFactory;
    }

    public Booking create(NewBooking newBooking, BookingCostCalculator bookingCostCalculator) {
        List<Traveler> travelers = createTravelers(newBooking.getTravelers());
        Money bookingCost = bookingCostCalculator.calculateBookingCost(travelers);
        Invoice invoice = invoiceFactory.create(bookingCost);

        return new Booking(new BookingId(),
                           travelers,
                           newBooking.getCabinType(),
                           newBooking.getBookingDateTime(),
                           invoice);
    }

    private List<Traveler> createTravelers(List<NewBookingTraveler> travelers) {
        return travelers.stream().map(traveler -> travelerFactory.create(traveler.name(), TravelerCategory.fromString(traveler.category()))).toList();
    }
}
