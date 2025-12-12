package uspace.application.cruise.planet.dtos;

import uspace.application.cruise.planet.excursion.dtos.ExcursionDto;

import java.util.List;

public class PlanetDto {

    public String name;
    public String arrivalDateTime;
    public String departureDateTime;
    public List<ExcursionDto> excursions;

    public PlanetDto() {
    }

    public PlanetDto(String name, String arrivalDateTime, String departureDateTime, List<ExcursionDto> excursions) {
        this.name = name;
        this.arrivalDateTime = arrivalDateTime;
        this.departureDateTime = departureDateTime;
        this.excursions = excursions;
    }
}
