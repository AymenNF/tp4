package uspace.domain.cruise.cabin;

import jakarta.persistence.*;
import uspace.domain.cruise.money.Money;

import java.util.Map;

@Embeddable
public class CabinPriceRegistry {

    @ElementCollection(fetch = FetchType.EAGER)
    public Map<CabinType, Money> cabinTypes;

    public CabinPriceRegistry() {
    }

    public CabinPriceRegistry(Map<CabinType, Money> cabinTypes) {
        this.cabinTypes = cabinTypes;
    }

    public Money getCabinPrice(CabinType cabinType) {
        return cabinTypes.get(cabinType);
    }
}
