package uspace.domain.cruise.crew.exceptions;

public class InvalidEmployeeIdFormatException extends RuntimeException {

    public InvalidEmployeeIdFormatException() {
        super("Invalid employee ID format.");
    }
}
