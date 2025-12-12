package uspace.domain.cruise.booking.invoice;

import uspace.domain.cruise.money.Money;

public class InvoiceFactory {

    public Invoice create(Money cost) {
        return new Invoice(new InvoiceId(), cost);
    }
}
