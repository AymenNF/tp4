package uspace.domain.cruise.money;

import jakarta.persistence.Embeddable;

@Embeddable
public class Money {

    private float amount;

    public Money(float amount) {
        this.amount = amount;
    }

    protected Money() {
    }

    public float toFloat() {
        return amount;
    }

    public Money add(Money otherMoney) {
        return new Money(amount + otherMoney.amount);
    }

    public Money multiply(float multiplier) {
        return new Money(amount * multiplier);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Money other = (Money) obj;
        return this.amount == other.amount;
    }
}
