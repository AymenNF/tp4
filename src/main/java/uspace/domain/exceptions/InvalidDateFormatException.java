package uspace.domain.exceptions;

public class InvalidDateFormatException extends IllegalArgumentException {

    public InvalidDateFormatException() {
        super("Invalid date format.");
    }
}
