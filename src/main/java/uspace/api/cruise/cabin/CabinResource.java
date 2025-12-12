package uspace.api.cruise.cabin;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import uspace.application.cruise.cabin.CabinAttributionService;
import uspace.application.cruise.cabin.dtos.CabinAttributionDto;

@Path("/cruises/{cruiseId}/cabins")
@Produces(MediaType.APPLICATION_JSON)
public class CabinResource {

    private final CabinAttributionService cabinAttributionService;

    @Inject
    public CabinResource(CabinAttributionService cabinAttributionService) {
        this.cabinAttributionService = cabinAttributionService;
    }

    @GET
    public Response getCabinAttributions(
            @PathParam("cruiseId") String cruiseId,
            @QueryParam("criteria") String criteria) {
        
        if (criteria == null || criteria.isEmpty()) {
            throw new uspace.domain.cruise.cabin.exceptions.InvalidCabinAttributionCriteriaException(
                "Invalid cabin attribution criteria. It must be one of: bookingDateTime, travelers."
            );
        }
        
        CabinAttributionDto cabinAttributionDto = cabinAttributionService.getCabinAttributions(cruiseId, criteria);
        return Response.ok(cabinAttributionDto).build();
    }
}

