package uspace.api.exceptions.mappers.uspace;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.exceptions.NoTravelerToBookException;

@Provider
public class NoTravelerToBookExceptionMapper implements ExceptionMapper<NoTravelerToBookException> {

    @Override
    public Response toResponse(NoTravelerToBookException exception) {
        ErrorResponse error = new ErrorResponse("NO_TRAVELER", exception.getMessage());
        return Response.status(400).entity(error).build();
    }
}
