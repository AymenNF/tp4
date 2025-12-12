package uspace.domain.cruise.itinerary.exceptions;

public class InvalidPlanetDateException extends RuntimeException {

    public InvalidPlanetDateException() {
        super("Arrival date or departure date is invalid");
    }
}
