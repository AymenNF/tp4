package uspace.api.exceptions.mappers.uspace;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.itinerary.exceptions.InvalidPlanetDateException;

@Provider
public class InvalidPlanetDateExceptionMapper implements ExceptionMapper<InvalidPlanetDateException> {

    @Override
    public Response toResponse(InvalidPlanetDateException exception) {
        ErrorResponse error = new ErrorResponse("INVALID_PLANET_DATE", exception.getMessage());
        return Response.status(400).entity(error).build();
    }
}
