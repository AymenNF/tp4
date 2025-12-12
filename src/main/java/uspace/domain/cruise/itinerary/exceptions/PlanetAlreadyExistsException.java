package uspace.domain.cruise.itinerary.exceptions;

public class PlanetAlreadyExistsException extends RuntimeException {

    public PlanetAlreadyExistsException() {
        super("Planet already exists in the itinerary");
    }
}
