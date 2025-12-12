package uspace.domain.cruise.crew.exceptions;

public class CrewMemberAlreadyInCruiseException extends RuntimeException {

    public CrewMemberAlreadyInCruiseException() {
        super("Crew member already in the cruise.");
    }
}
