package uspace.domain.cruise.cabin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uspace.domain.cruise.money.Money;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CabinPriceRegistryTest {

    private static final CabinType ANY_CABIN_TYPE_IN_REGISTRY = CabinType.DELUXE;
    private static final Money ANY_AMOUNT = new Money(100);
    private static final Map<CabinType, Money> ANY_CABIN_TYPES = new HashMap<>(Map.of(ANY_CABIN_TYPE_IN_REGISTRY, ANY_AMOUNT));
    private CabinPriceRegistry cabinPriceRegistry;

    @BeforeEach
    void createCabinPriceRegistry() {
        cabinPriceRegistry = new CabinPriceRegistry(ANY_CABIN_TYPES);
    }

    @Test
    void givenCabinTypeInRegistry_whenGetCabinPrice_thenReturnCabinPrice() {
        Money moneyExpected = new Money(100);
        CabinType cabinTypeInRegistry = CabinType.DELUXE;

        Money moneyResult = cabinPriceRegistry.getCabinPrice(cabinTypeInRegistry);

        assertEquals(moneyExpected, moneyResult);
    }

    @Test
    void givenCabinTypeNotInRegistry_whenGetCabinPrice_thenReturnNull() {
        CabinType cabinTypeNotInRegistry = CabinType.STANDARD;

        Money moneyResult = cabinPriceRegistry.getCabinPrice(cabinTypeNotInRegistry);

        assertNull(moneyResult);
    }
}