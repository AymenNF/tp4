package uspace.application.cruise.planet.dtos;

public class NewPlanetDto {

    public String name;
    public String arrivalDateTime;
    public String departureDateTime;

    public NewPlanetDto() {
    }

    public NewPlanetDto(String name, String arrivalDateTime, String departureDateTime) {
        this.name = name;
        this.arrivalDateTime = arrivalDateTime;
        this.departureDateTime = departureDateTime;
    }
}
