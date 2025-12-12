package uspace.api.exceptions.mappers.uspace;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.booking.traveler.exceptions.ZeroGravityExperienceChildCriteriaException;

@Provider
public class ZeroGravityExperienceChildCriteriaExceptionMapper implements ExceptionMapper<ZeroGravityExperienceChildCriteriaException> {

    @Override
    public Response toResponse(ZeroGravityExperienceChildCriteriaException exception) {
        ErrorResponse error = new ErrorResponse("ZERO_GRAVITY_EXPERIENCE_CHILD_CRITERIA", exception.getMessage());
        return Response.status(400).entity(error).build();
    }
}
