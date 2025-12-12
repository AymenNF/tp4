package uspace.api.cruise.crew;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import uspace.application.cruise.crew.CrewMemberService;
import uspace.application.cruise.crew.dtos.CrewMemberDto;

@Path("/cruises/{cruiseId}/crewMembers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CrewMemberResource {

    private final CrewMemberService crewMemberService;
    private final CrewMemberDtoValidator crewMemberDtoValidator;

    @Inject
    public CrewMemberResource(CrewMemberService crewMemberService, CrewMemberDtoValidator crewMemberDtoValidator) {
        this.crewMemberService = crewMemberService;
        this.crewMemberDtoValidator = crewMemberDtoValidator;
    }

    @POST
    public Response addCrewMember(@PathParam("cruiseId") String cruiseId, CrewMemberDto crewMemberDto) {
        crewMemberDtoValidator.validate(crewMemberDto);

        crewMemberService.addCrewMember(cruiseId, crewMemberDto);

        return Response.status(Response.Status.CREATED).build();
    }
}
