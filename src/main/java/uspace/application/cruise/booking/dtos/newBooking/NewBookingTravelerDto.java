package uspace.application.cruise.booking.dtos.newBooking;

public class NewBookingTravelerDto {
    public String name;
    public String category;

    public NewBookingTravelerDto() {
    }

    public NewBookingTravelerDto(String name, String category) {
        this.name = name;
        this.category = category;
    }
}
