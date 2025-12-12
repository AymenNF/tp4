package uspace.api.exceptions.mappers.uspace;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.zeroGravityExperience.exceptions.ZeroGravityExperienceAlreadyBookedException;

@Provider
public class ZeroGravityAlreadyBookedExceptionMapper implements ExceptionMapper<ZeroGravityExperienceAlreadyBookedException> {

    @Override
    public Response toResponse(ZeroGravityExperienceAlreadyBookedException exception) {
        ErrorResponse error = new ErrorResponse("ZERO_GRAVITY_EXPERIENCE_ALREADY_BOOKED", exception.getMessage());
        return Response.status(400).entity(error).build();
    }
}
