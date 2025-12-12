package uspace.domain.cruise.booking.traveler;

import jakarta.persistence.Embeddable;
import uspace.domain.cruise.money.Money;

@Embeddable
public class StandardCostMultiplier {
    private float standardCostMultiplier;

    protected StandardCostMultiplier() {
    }

    public StandardCostMultiplier(float standardCostMultiplier) {
        this.standardCostMultiplier = standardCostMultiplier;
    }

    public Money applyToMoney(Money money) {
        return money.multiply(standardCostMultiplier);
    }
}
