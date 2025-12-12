package uspace.api.exceptions.mappers.uspace;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.cabin.exceptions.CabinTypeNotAvailableException;

@Provider
public class CabinTypeNotAvailableExceptionMapper implements ExceptionMapper<CabinTypeNotAvailableException> {

    @Override
    public Response toResponse(CabinTypeNotAvailableException exception) {
        ErrorResponse error = new ErrorResponse("CABIN_TYPE_NOT_AVAILABLE", exception.getMessage());
        return Response.status(400).entity(error).build();
    }
}
