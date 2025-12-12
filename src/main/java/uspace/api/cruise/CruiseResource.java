package uspace.api.cruise;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import uspace.application.cruise.CruiseService;
import uspace.application.cruise.dtos.CruiseDto;

@Path("/cruises")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CruiseResource {

    private final CruiseService cruiseService;

    @Inject
    public CruiseResource(CruiseService cruiseService) {
        this.cruiseService = cruiseService;
    }

    @GET
    @Path("{cruiseId}")
    public Response getCruise(@PathParam("cruiseId") String cruiseId) {
        CruiseDto cruiseDto = cruiseService.findCruise(cruiseId);
        return Response.ok(cruiseDto).build();
    }
}
