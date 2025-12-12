package uspace.api.exceptions.mappers.uspace;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.sport.exceptions.SportNotBookableException;

@Provider
public class SportNotBookableExceptionMapper implements ExceptionMapper<SportNotBookableException> {

    @Override
    public Response toResponse(SportNotBookableException exception) {
        ErrorResponse error = new ErrorResponse("SPORT_NOT_BOOKABLE", exception.getMessage());
        return Response.status(400).entity(error).build();
    }
}
