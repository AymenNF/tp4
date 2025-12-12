package uspace.api.exceptions.mappers.account;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.account.exceptions.AccountCannotBeDeletedException;

@Provider
public class AccountCannotBeDeletedExceptionMapper implements ExceptionMapper<AccountCannotBeDeletedException> {

    @Override
    public Response toResponse(AccountCannotBeDeletedException exception) {
        ErrorResponse error = new ErrorResponse("ACCOUNT_CANNOT_BE_DELETED", exception.getMessage());
        return Response.status(400).entity(error).build();
    }
}
