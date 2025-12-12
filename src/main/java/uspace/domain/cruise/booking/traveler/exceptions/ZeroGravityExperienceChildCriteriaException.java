package uspace.domain.cruise.booking.traveler.exceptions;

public class ZeroGravityExperienceChildCriteriaException extends RuntimeException {
    public ZeroGravityExperienceChildCriteriaException() {
        super("Child cannot book zero gravity experience without an adult or a senior of the same booking.");
    }
}
