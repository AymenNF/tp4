package uspace.api.exceptions.mappers.uspace;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.booking.traveler.exceptions.TravelerNotFoundException;

@Provider
public class TravelerNotFoundExceptionMapper implements ExceptionMapper<TravelerNotFoundException> {

    @Override
    public Response toResponse(TravelerNotFoundException exception) {
        ErrorResponse error = new ErrorResponse("TRAVELER_NOT_FOUND", exception.getMessage());
        return Response.status(404).entity(error).build();
    }
}
