package uspace.api.exceptions.mappers.uspace;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.crew.exceptions.InvalidEmployeeIdFormatException;

@Provider
public class InvalidEmployeeIdFormatExceptionMapper implements ExceptionMapper<InvalidEmployeeIdFormatException> {

    @Override
    public Response toResponse(InvalidEmployeeIdFormatException exception) {
        ErrorResponse error = new ErrorResponse("INVALID_EMPLOYEE_ID_FORMAT", exception.getMessage());
        return Response.status(400).entity(error).build();
    }
}
