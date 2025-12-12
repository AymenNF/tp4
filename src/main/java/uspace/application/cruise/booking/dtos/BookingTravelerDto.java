package uspace.application.cruise.booking.dtos;

import java.util.List;

public class BookingTravelerDto {
    public String id;

    public String name;
    public String category;
    public List<String> badges;

    public BookingTravelerDto() {
    }

    public BookingTravelerDto(String id, String name, String category, List<String> badges) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.badges = badges;
    }
}
