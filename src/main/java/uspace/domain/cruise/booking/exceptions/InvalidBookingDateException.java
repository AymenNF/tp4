package uspace.domain.cruise.booking.exceptions;

import uspace.domain.exceptions.InvalidParameterException;

public class InvalidBookingDateException extends InvalidParameterException {

    public InvalidBookingDateException() {
        super("Invalid booking date.");
    }
}
