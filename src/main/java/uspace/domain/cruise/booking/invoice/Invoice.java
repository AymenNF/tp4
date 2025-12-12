package uspace.domain.cruise.booking.invoice;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import uspace.domain.cruise.money.Money;

@Entity
public class Invoice {

    @Id
    private InvoiceId id;

    private Money cost;

    public Invoice() {
    }

    protected Invoice(InvoiceId id, Money cost) {
        this.id = id;
        this.cost = cost;
    }

    public Money getCost() {
        return cost;
    }
}
