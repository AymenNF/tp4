package uspace.application.cruise.crew;

import jakarta.inject.Inject;
import uspace.application.cruise.crew.dtos.CrewMemberDto;
import uspace.domain.cruise.Cruise;
import uspace.domain.cruise.CruiseId;
import uspace.domain.cruise.CruiseRepository;
import uspace.domain.cruise.crew.crewMember.CrewMember;
import uspace.domain.cruise.exceptions.CruiseNotFoundException;

public class CrewMemberService {

    private final CruiseRepository cruiseRepository;
    private final CrewMemberAssembler crewMemberAssembler;

    @Inject
    public CrewMemberService(CruiseRepository cruiseRepository, CrewMemberAssembler crewMemberAssembler) {
        this.cruiseRepository = cruiseRepository;
        this.crewMemberAssembler = crewMemberAssembler;
    }

    public void addCrewMember(String cruiseId, CrewMemberDto crewMemberDto) {
        Cruise cruise = cruiseRepository.findById(new CruiseId(cruiseId));
        if (cruise == null) {
            throw new CruiseNotFoundException();
        }

        CrewMember crewMember = crewMemberAssembler.toCrewMember(crewMemberDto);
        cruise.addCrewMember(crewMember);

        cruiseRepository.save(cruise);
    }
}
