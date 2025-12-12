package uspace.api.exceptions.mappers.uspace;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.zeroGravityExperience.exceptions.ZeroGravityExperienceFullException;

@Provider
public class ZeroGravityExperienceFullExceptionMapper implements ExceptionMapper<ZeroGravityExperienceFullException> {

    @Override
    public Response toResponse(ZeroGravityExperienceFullException exception) {
        ErrorResponse error = new ErrorResponse("ZERO_GRAVITY_EXPERIENCE_FULL", exception.getMessage());
        return Response.status(400).entity(error).build();
    }
}
