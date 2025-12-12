package uspace.domain.cruise.money;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    private static final float ANY_AMOUNT = 100;
    private Money money;

    @BeforeEach
    void createMoney() {
        money = new Money(ANY_AMOUNT);
    }

    @Test
    void whenAdd_thenReturnMoneyWithAmountSum() {
        Money otherMoney = new Money(50);
        Money moneyExpected = new Money(150);

        Money moneyResult = money.add(otherMoney);

        assertEquals(moneyExpected, moneyResult);
    }

    @Test
    void whenMultiply_thenReturnMoneyWithAmountMultiplied() {
        float multiplier = 2;
        Money moneyExpected = new Money(200);

        Money moneyResult = money.multiply(multiplier);

        assertEquals(moneyExpected, moneyResult);
    }

    @Test
    void givenDifferentClassObject_whenEquals_thenReturnFalse() {
        Object obj = new Object();

        boolean result = money.equals(obj);

        assertFalse(result);
    }

    @Test
    void givenDifferentAmount_whenEquals_thenReturnFalse() {
        Money other = new Money(50);

        boolean result = money.equals(other);

        assertFalse(result);
    }

    @Test
    void givenSameAmount_whenEquals_thenReturnTrue() {
        Money other = new Money(100);

        boolean result = money.equals(other);

        assertTrue(result);
    }
}