package uspace.domain.cruise.booking.traveler.exceptions;

import uspace.domain.exceptions.InvalidParameterException;

public class InvalidTravelerCategoryException extends InvalidParameterException {

    public InvalidTravelerCategoryException() {
        super("Invalid traveler category.");
    }
}
