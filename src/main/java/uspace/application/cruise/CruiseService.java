package uspace.application.cruise;

import jakarta.inject.Inject;
import uspace.application.cruise.dtos.CruiseDto;
import uspace.domain.cruise.Cruise;
import uspace.domain.cruise.CruiseId;
import uspace.domain.cruise.CruiseRepository;
import uspace.domain.cruise.exceptions.CruiseNotFoundException;

public class CruiseService {

    private final CruiseRepository cruiseRepository;
    private final CruiseAssembler cruiseAssembler;

    @Inject
    public CruiseService(CruiseRepository cruiseRepository, CruiseAssembler cruiseAssembler) {
        this.cruiseRepository = cruiseRepository;
        this.cruiseAssembler = cruiseAssembler;
    }

    public CruiseDto findCruise(String cruiseId) {
        Cruise cruise = cruiseRepository.findById(new CruiseId(cruiseId));
        if (cruise == null) {
            throw new CruiseNotFoundException();
        }

        return cruiseAssembler.toDto(cruise);
    }
}
