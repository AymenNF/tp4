package uspace.api.exceptions.mappers.uspace;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.zeroGravityExperience.exceptions.ZeroGravityExperienceBookingTimeException;

@Provider
public class ZeroGravityAlreadyBookingTimeExceptionMapper implements ExceptionMapper<ZeroGravityExperienceBookingTimeException> {

    @Override
    public Response toResponse(ZeroGravityExperienceBookingTimeException exception) {
        ErrorResponse error = new ErrorResponse("ZERO_GRAVITY_EXPERIENCE_BOOKING_TIME", exception.getMessage());
        return Response.status(400).entity(error).build();
    }
}
