package uspace.api.cruise.booking.traveler.zeroGravityExperienceRequest;

import uspace.domain.exceptions.MissingParameterException;

public class ZeroGravityExperienceRequestValidator {
    public void validate(ZeroGravityExperienceRequest zeroGravityExperienceRequest) {
        if (zeroGravityExperienceRequest.experienceBookingDateTime == null) {
            throw new MissingParameterException("experienceBookingDateTime");
        }
    }
}
