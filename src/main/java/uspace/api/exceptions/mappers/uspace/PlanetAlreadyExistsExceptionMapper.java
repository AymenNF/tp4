package uspace.api.exceptions.mappers.uspace;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.itinerary.exceptions.PlanetAlreadyExistsException;

@Provider
public class PlanetAlreadyExistsExceptionMapper implements ExceptionMapper<PlanetAlreadyExistsException> {

    @Override
    public Response toResponse(PlanetAlreadyExistsException exception) {
        ErrorResponse error = new ErrorResponse("PLANET_ALREADY_EXISTS", exception.getMessage());
        return Response.status(400).entity(error).build();
    }
}
