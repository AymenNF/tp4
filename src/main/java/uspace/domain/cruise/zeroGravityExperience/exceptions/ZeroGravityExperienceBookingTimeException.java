package uspace.domain.cruise.zeroGravityExperience.exceptions;

public class ZeroGravityExperienceBookingTimeException extends RuntimeException {

    public ZeroGravityExperienceBookingTimeException() {
        super("Zero gravity experience booking time must be before the cruise departure time.");
    }
}
