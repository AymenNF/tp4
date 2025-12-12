package uspace.api.account;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import uspace.application.account.AccountService;
import uspace.application.account.dtos.AccountDto;

@Path("/accounts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

    private final AccountService accountService;
    private final AccountDtoValidator accountDtoValidator;

    @Inject
    public AccountResource(AccountService accountService, AccountDtoValidator accountDtoValidator) {
        this.accountService = accountService;
        this.accountDtoValidator = accountDtoValidator;
    }

    @POST
    public Response createAccount(AccountDto accountDto) {
        accountDtoValidator.validate(accountDto);
        accountService.createAccount(accountDto);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{username}")
    public Response getAccount(@PathParam("username") String username) {
        AccountDto accountDto = accountService.findAccount(username);
        return Response.ok(accountDto).build();
    }

    @DELETE
    @Path("/{username}")
    public Response deleteAccount(@PathParam("username") String username) {
        accountService.deleteAccount(username);
        return Response.ok().build();
    }
}
