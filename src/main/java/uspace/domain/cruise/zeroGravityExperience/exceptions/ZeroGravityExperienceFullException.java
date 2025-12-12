package uspace.domain.cruise.zeroGravityExperience.exceptions;

public class ZeroGravityExperienceFullException extends RuntimeException {

    public ZeroGravityExperienceFullException() {
        super("Zero gravity experience is full");
    }
}
