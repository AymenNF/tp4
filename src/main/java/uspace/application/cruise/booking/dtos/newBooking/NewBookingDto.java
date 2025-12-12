package uspace.application.cruise.booking.dtos.newBooking;

import java.util.List;

public class NewBookingDto {
    public String cabinType;
    public List<NewBookingTravelerDto> travelers;
    public String bookingDateTime;
    public String accountUsername;

    public NewBookingDto() {
    }

    public NewBookingDto(String cabinType, List<NewBookingTravelerDto> travelers, String bookingDateTime, String accountUsername) {
        this.cabinType = cabinType;
        this.travelers = travelers;
        this.bookingDateTime = bookingDateTime;
        this.accountUsername = accountUsername;
    }
}
