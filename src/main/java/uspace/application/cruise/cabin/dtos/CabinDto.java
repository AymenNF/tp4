package uspace.application.cruise.cabin.dtos;

public class CabinDto {
    public String cabinId;
    public String bookingId;
    public String category;

    public CabinDto() {
    }

    public CabinDto(String cabinId, String bookingId, String category) {
        this.cabinId = cabinId;
        this.bookingId = bookingId;
        this.category = category;
    }
}

