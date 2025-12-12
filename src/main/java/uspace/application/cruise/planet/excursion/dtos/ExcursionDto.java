package uspace.application.cruise.planet.excursion.dtos;

public class ExcursionDto {

    public String name;
    public String description;
    public String startDateTime;
    public int duration;
    public int capacity;

    public ExcursionDto() {
    }

    public ExcursionDto(String name, String description, String startDateTime, int duration, int capacity) {
        this.name = name;
        this.description = description;
        this.startDateTime = startDateTime;
        this.duration = duration;
        this.capacity = capacity;
    }
}
