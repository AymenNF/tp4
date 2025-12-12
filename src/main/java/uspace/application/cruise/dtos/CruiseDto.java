package uspace.application.cruise.dtos;

import uspace.application.cruise.planet.dtos.PlanetDto;

import java.util.List;

public class CruiseDto {
    public String id;
    public String departureDateTime;
    public String endDateTime;
    public List<PlanetDto> planets;
    public List<String> crewMembersIds;

    public CruiseDto(String id, String departureDateTime, String endDateTime, List<PlanetDto> planets, List<String> crewMembersIds) {
        this.id = id;
        this.departureDateTime = departureDateTime;
        this.endDateTime = endDateTime;
        this.planets = planets;
        this.crewMembersIds = crewMembersIds;
    }
}
