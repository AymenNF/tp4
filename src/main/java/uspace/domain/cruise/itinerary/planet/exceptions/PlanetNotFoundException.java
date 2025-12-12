package uspace.domain.cruise.itinerary.planet.exceptions;

public class PlanetNotFoundException extends RuntimeException {

    public PlanetNotFoundException() {
        super("Planet not found.");
    }
}
