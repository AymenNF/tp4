package uspace.api.exceptions.mappers.uspace;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.sport.exceptions.SportAlreadyBookedException;
import uspace.domain.cruise.sport.exceptions.SportNotBookableException;

@Provider
public class SportAlreadyBookedExceptionMapper implements ExceptionMapper<SportAlreadyBookedException> {

    @Override
    public Response toResponse(SportAlreadyBookedException exception) {
        ErrorResponse error = new ErrorResponse("SPORT_ALREADY_BOOKED", exception.getMessage());
        return Response.status(400).entity(error).build();
    }
}
