package uspace.domain.cruise.sport.exceptions;

public class SportAlreadyBookedException extends RuntimeException {

    public SportAlreadyBookedException() {
        super("Sport already booked by traveler");
    }
}
