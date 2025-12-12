package uspace.domain.cruise.itinerary.planet.exceptions;

public class InvalidPlanetException extends RuntimeException {

        public InvalidPlanetException() {
            super("Invalid planet");
        }
}
