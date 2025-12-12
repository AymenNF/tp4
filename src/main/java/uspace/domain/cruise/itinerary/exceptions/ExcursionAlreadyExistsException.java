package uspace.domain.cruise.itinerary.exceptions;

public class ExcursionAlreadyExistsException extends RuntimeException {

    public ExcursionAlreadyExistsException() {
        super("Excursion already exists in the cruise itinerary.");
    }
}
