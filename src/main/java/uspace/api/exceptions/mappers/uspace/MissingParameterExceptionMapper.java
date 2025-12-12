package uspace.api.exceptions.mappers.uspace;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.exceptions.MissingParameterException;

@Provider
public class MissingParameterExceptionMapper implements ExceptionMapper<MissingParameterException> {

    @Override
    public Response toResponse(MissingParameterException exception) {
        ErrorResponse error = new ErrorResponse("MISSING_PARAMETER", exception.getMessage());
        return Response.status(400).entity(error).build();
    }
}
