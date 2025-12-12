package uspace.infra.persistence.inMemory;

import uspace.domain.cruise.Cruise;
import uspace.domain.cruise.CruiseId;
import uspace.domain.cruise.CruiseRepository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryCruiseRepository implements CruiseRepository {

    private static final Map<CruiseId, Cruise> cruises = new HashMap<>();

    public InMemoryCruiseRepository() {
        System.out.println("DEBUG: InMemoryCruiseRepository created. Instance: " + System.identityHashCode(this));
    }

    @Override
    public Cruise findById(CruiseId cruiseId) {
        System.out.println("DEBUG: InMemoryCruiseRepository.findById: " + cruiseId + ". Map keys: " + cruises.keySet());
        return cruises.get(cruiseId);
    }

    @Override
    public void save(Cruise cruise) {
        cruises.put(cruise.getId(), cruise);
    }
}
