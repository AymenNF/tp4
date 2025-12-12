package uspace.domain.cruise.booking.traveler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uspace.domain.cruise.money.Money;

import static org.junit.jupiter.api.Assertions.*;

class StandardCostMultiplierTest {

    private static final float ANY_STANDARD_COST_MULTIPLIER = 1.5f;
    private StandardCostMultiplier standardCostMultiplier;

    @BeforeEach
    void createStandardCostMultiplier() {
        standardCostMultiplier = new StandardCostMultiplier(ANY_STANDARD_COST_MULTIPLIER);
    }

    @Test
    void whenApplyToMoney_thenReturnMoneyMultipliedByStandardCostMultiplier() {
        Money money = new Money(100);
        Money moneyExpected = new Money(150);

        Money moneyResult = standardCostMultiplier.applyToMoney(money);

        assertEquals(moneyExpected, moneyResult);
    }
}