package uspace.application.cruise.emergencyShuttle;

import jakarta.inject.Inject;
import uspace.application.cruise.emergencyShuttle.dtos.EmergencyShuttleManifestDto;
import uspace.domain.cruise.Cruise;
import uspace.domain.cruise.CruiseId;
import uspace.domain.cruise.CruiseRepository;
import uspace.domain.cruise.emergencyShuttle.EmergencyShuttleAssigner;
import uspace.domain.cruise.emergencyShuttle.EmergencyShuttleManifest;
import uspace.domain.cruise.exceptions.CruiseNotFoundException;

public class EmergencyShuttleService {
    
    private final CruiseRepository cruiseRepository;
    private final EmergencyShuttleAssigner assigner;
    private final EmergencyShuttleAssembler assembler;
    
    @Inject
    public EmergencyShuttleService(CruiseRepository cruiseRepository, EmergencyShuttleAssigner assigner, EmergencyShuttleAssembler assembler) {
        this.cruiseRepository = cruiseRepository;
        this.assigner = assigner;
        this.assembler = assembler;
    }
    
    public EmergencyShuttleManifestDto getEmergencyShuttleManifest(String cruiseId) {
        Cruise cruise = cruiseRepository.findById(new CruiseId(cruiseId));
        if (cruise == null) {
            throw new CruiseNotFoundException();
        }
        
        EmergencyShuttleManifest manifest = assigner.assignShuttles(cruise);
        return assembler.toDto(manifest);
    }
}

