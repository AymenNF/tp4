package uspace.api.exceptions.mappers.uspace;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.exceptions.CruiseNotFoundException;

@Provider
public class CruiseNotFoundExceptionMapper implements ExceptionMapper<CruiseNotFoundException> {

    @Override
    public Response toResponse(CruiseNotFoundException exception) {
        ErrorResponse error = new ErrorResponse("CRUISE_NOT_FOUND", exception.getMessage());
        return Response.status(404).entity(error).build();
    }
}
