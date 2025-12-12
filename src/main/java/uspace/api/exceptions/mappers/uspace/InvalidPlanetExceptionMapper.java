package uspace.api.exceptions.mappers.uspace;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.itinerary.planet.exceptions.InvalidPlanetException;

@Provider
public class InvalidPlanetExceptionMapper implements ExceptionMapper<InvalidPlanetException> {

    @Override
    public Response toResponse(InvalidPlanetException exception) {
        ErrorResponse error = new ErrorResponse("INVALID_PLANET", exception.getMessage());
        return Response.status(400).entity(error).build();
    }
}
