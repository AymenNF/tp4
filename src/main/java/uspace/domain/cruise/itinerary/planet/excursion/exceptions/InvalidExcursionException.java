package uspace.domain.cruise.itinerary.planet.excursion.exceptions;

public class InvalidExcursionException extends RuntimeException {

    public InvalidExcursionException() {
        super("Invalid excursion.");
    }
}
