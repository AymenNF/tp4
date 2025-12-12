package uspace.domain.cruise.exceptions;

public class NoTravelerToBookException extends IllegalArgumentException {

    public NoTravelerToBookException() {
        super("No traveler to book.");
    }
}
