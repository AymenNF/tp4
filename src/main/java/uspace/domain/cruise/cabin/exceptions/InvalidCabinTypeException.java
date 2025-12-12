package uspace.domain.cruise.cabin.exceptions;

import uspace.domain.exceptions.InvalidParameterException;

public class InvalidCabinTypeException extends InvalidParameterException {

    public InvalidCabinTypeException() {
        super("Invalid cabin type.");
    }
}
