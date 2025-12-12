package uspace.api.exceptions.mappers.uspace;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.itinerary.planet.excursion.exceptions.InvalidExcursionException;

@Provider
public class InvalidExcursionExceptionMapper implements ExceptionMapper<InvalidExcursionException> {

    @Override
    public Response toResponse(InvalidExcursionException exception) {
        ErrorResponse error = new ErrorResponse("INVALID_EXCURSION", exception.getMessage());
        return Response.status(400).entity(error).build();
    }
}
