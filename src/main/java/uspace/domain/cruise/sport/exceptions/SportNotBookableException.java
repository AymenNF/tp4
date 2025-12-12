package uspace.domain.cruise.sport.exceptions;

public class SportNotBookableException extends RuntimeException {

    public SportNotBookableException() {
        super("Sport not bookable for traveler");
    }
}
