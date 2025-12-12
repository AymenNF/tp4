package uspace.domain.cruise.sport.exceptions;

public class SportNotFoundException extends RuntimeException {

    public SportNotFoundException() {
        super("Sport not found");
    }
}
