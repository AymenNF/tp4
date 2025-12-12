package uspace.api.exceptions.mappers.account;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.account.exceptions.AccountNotFoundException;

@Provider
public class AccountNotFoundExceptionMapper implements ExceptionMapper<AccountNotFoundException> {

    @Override
    public Response toResponse(AccountNotFoundException exception) {
        ErrorResponse error = new ErrorResponse("ACCOUNT_NOT_FOUND", exception.getMessage());
        return Response.status(404).entity(error).build();
    }
}
