package uspace.api.exceptions.mappers.uspace;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.itinerary.planet.exceptions.PlanetNotFoundException;

@Provider
public class PlanetNotFoundExceptionMapper implements ExceptionMapper<PlanetNotFoundException> {

    @Override
    public Response toResponse(PlanetNotFoundException exception) {
        ErrorResponse error = new ErrorResponse("PLANET_NOT_FOUND", exception.getMessage());
        return Response.status(404).entity(error).build();
    }
}
