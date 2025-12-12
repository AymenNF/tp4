package uspace.api.cruise.booking.traveler.zeroGravityExperienceRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import uspace.domain.exceptions.MissingParameterException;

import static org.junit.jupiter.api.Assertions.*;

class ZeroGravityExperienceRequestValidatorTest {
    private static final String ANY_EXPERIENCE_BOOKING_DATE_TIME = "2084-04-08T12:30";
    private ZeroGravityExperienceRequest zeroGravityExperienceRequest;
    private ZeroGravityExperienceRequestValidator zeroGravityExperienceRequestValidator;

    @BeforeEach
    void createZeroGravityExperienceRequestValidatorAndRequest() {
        zeroGravityExperienceRequest = new ZeroGravityExperienceRequest();
        zeroGravityExperienceRequestValidator = new ZeroGravityExperienceRequestValidator();
    }

    @Test
    void givenNoExperienceBookingDateTime_whenValidate_thenThrowMissingParameterException() {
        zeroGravityExperienceRequest.experienceBookingDateTime = null;

        Executable validate = () -> zeroGravityExperienceRequestValidator.validate(zeroGravityExperienceRequest);

        assertThrows(MissingParameterException.class, validate);
    }

    @Test
    void givenExperienceBookingDateTime_whenValidate_thenDoNothing() {
        zeroGravityExperienceRequest.experienceBookingDateTime = ANY_EXPERIENCE_BOOKING_DATE_TIME;

        Executable validate = () -> zeroGravityExperienceRequestValidator.validate(zeroGravityExperienceRequest);

        assertDoesNotThrow(validate);
    }
}