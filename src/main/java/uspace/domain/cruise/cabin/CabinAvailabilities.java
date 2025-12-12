package uspace.domain.cruise.cabin;

import jakarta.persistence.*;
import uspace.domain.cruise.cabin.exceptions.CabinTypeNotAvailableException;

import java.util.Map;

@Entity
public class CabinAvailabilities {
    @Id
    private CabinAvailabilitiesId id;

    @ElementCollection(fetch = FetchType.EAGER)
    public Map<CabinType, Integer> availibilitiesByCabinType;

    public CabinAvailabilities() {

    }

    public CabinAvailabilities(CabinAvailabilitiesId id, Map<CabinType, Integer> availibilitiesByCabinType) {
        this.id = id;
        this.availibilitiesByCabinType = availibilitiesByCabinType;
    }

    public int getAvailabilitiesByCabinType(CabinType cabinType) {
        return availibilitiesByCabinType.getOrDefault(cabinType, 0);
    }

    public void bookCabin(CabinType cabinType) {
        if (availibilitiesByCabinType.getOrDefault(cabinType, 0) <= 0) {
            throw new CabinTypeNotAvailableException();
        }
        availibilitiesByCabinType.put(cabinType, availibilitiesByCabinType.get(cabinType) - 1);
    }
}
