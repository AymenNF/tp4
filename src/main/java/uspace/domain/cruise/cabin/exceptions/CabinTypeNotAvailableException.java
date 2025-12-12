package uspace.domain.cruise.cabin.exceptions;

public class CabinTypeNotAvailableException extends RuntimeException {

    public CabinTypeNotAvailableException() {
        super("Cabin type not available");
    }
}
