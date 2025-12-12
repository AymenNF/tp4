package uspace.api.exceptions.mappers.uspace;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.cabin.exceptions.InvalidCabinAttributionCriteriaException;

@Provider
public class InvalidCabinAttributionCriteriaExceptionMapper implements ExceptionMapper<InvalidCabinAttributionCriteriaException> {

    @Override
    public Response toResponse(InvalidCabinAttributionCriteriaException exception) {
        ErrorResponse error = new ErrorResponse("INVALID_CABIN_ATTRIBUTION_CRITERIA", exception.getMessage());
        return Response.status(400).entity(error).build();
    }
}

