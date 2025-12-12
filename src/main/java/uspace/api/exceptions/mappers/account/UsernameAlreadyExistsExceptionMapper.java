package uspace.api.exceptions.mappers.account;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.account.exceptions.UsernameAlreadyExistsException;

@Provider
public class UsernameAlreadyExistsExceptionMapper implements ExceptionMapper<UsernameAlreadyExistsException> {

    @Override
    public Response toResponse(UsernameAlreadyExistsException exception) {
        ErrorResponse error = new ErrorResponse("USERNAME_ALREADY_EXISTS", exception.getMessage());
        return Response.status(400).entity(error).build();
    }
}
