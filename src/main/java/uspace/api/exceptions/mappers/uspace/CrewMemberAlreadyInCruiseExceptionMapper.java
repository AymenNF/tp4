package uspace.api.exceptions.mappers.uspace;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import uspace.api.exceptions.ErrorResponse;
import uspace.domain.cruise.crew.exceptions.CrewMemberAlreadyInCruiseException;

@Provider
public class CrewMemberAlreadyInCruiseExceptionMapper implements ExceptionMapper<CrewMemberAlreadyInCruiseException> {

    @Override
    public Response toResponse(CrewMemberAlreadyInCruiseException exception) {
        ErrorResponse error = new ErrorResponse("CREW_MEMBER_ALREADY_EXISTS", exception.getMessage());
        return Response.status(400).entity(error).build();
    }
}
