package uspace.domain.exceptions;

public class InvalidParameterException extends IllegalArgumentException {

    public InvalidParameterException(String message) {
        super(message);
    }
}
