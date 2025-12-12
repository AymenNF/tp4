package uspace.application.cruise;

import jakarta.inject.Inject;
import uspace.application.cruise.dtos.CruiseDto;
import uspace.application.cruise.planet.PlanetAssembler;
import uspace.application.cruise.planet.dtos.PlanetDto;
import uspace.domain.cruise.Cruise;
import uspace.domain.cruise.crew.crewMember.CrewMember;
import uspace.domain.cruise.crew.crewMember.employeeId.EmployeeId;
import uspace.domain.cruise.itinerary.planet.Planet;

import java.util.List;

public class CruiseAssembler {
    private final PlanetAssembler planetAssembler;

    @Inject
    public CruiseAssembler(PlanetAssembler planetAssembler) {
        this.planetAssembler = planetAssembler;
    }

    public CruiseDto toDto(Cruise cruise) {
        List<PlanetDto> itinerary = createItineraryPlanetDtos(cruise.getPlanetsInItinerary());
        List<String> crewMembersIds = getCrewMembersIds(cruise);

        return new CruiseDto(
            cruise.getId().toString(),
            cruise.getDepartureDateTime().toString(),
            cruise.getEndDateTime().toString(),
            itinerary,
            crewMembersIds
        );
    }

    private List<PlanetDto> createItineraryPlanetDtos(List<Planet> planetsInItinerary) {
        return planetsInItinerary.stream().map(planetAssembler::toDto).toList();
    }

    private List<String> getCrewMembersIds(Cruise cruise) {
        return cruise.getCrewMembers().stream().map(CrewMember::getEmployeeId).map(EmployeeId::toString).toList();
    }
}
