package uspace.api.exceptions.mappers.uspace;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.exceptions.InvalidDateFormatException;

@Provider
public class InvalidDateFormatExceptionMapper implements ExceptionMapper<InvalidDateFormatException> {

    @Override
    public Response toResponse(InvalidDateFormatException exception) {
        ErrorResponse error = new ErrorResponse("INVALID_DATE_FORMAT", exception.getMessage());
        return Response.status(400).entity(error).build();
    }
}
