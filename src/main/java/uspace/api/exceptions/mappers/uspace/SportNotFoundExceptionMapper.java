package uspace.api.exceptions.mappers.uspace;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.booking.exceptions.BookingNotFoundException;
import uspace.domain.cruise.sport.exceptions.SportNotFoundException;

@Provider
public class SportNotFoundExceptionMapper implements ExceptionMapper<SportNotFoundException> {

    @Override
    public Response toResponse(SportNotFoundException exception) {
        ErrorResponse error = new ErrorResponse("SPORT_NOT_FOUND", exception.getMessage());
        return Response.status(404).entity(error).build();
    }
}
