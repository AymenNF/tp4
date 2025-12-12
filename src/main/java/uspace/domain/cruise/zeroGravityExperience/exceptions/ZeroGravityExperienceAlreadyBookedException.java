package uspace.domain.cruise.zeroGravityExperience.exceptions;

public class ZeroGravityExperienceAlreadyBookedException extends RuntimeException {

    public ZeroGravityExperienceAlreadyBookedException() {
        super("Zero gravity experience already booked by traveler");
    }
}
