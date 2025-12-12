package uspace.api.cruise.emergencyShuttle;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import uspace.application.cruise.emergencyShuttle.EmergencyShuttleService;
import uspace.application.cruise.emergencyShuttle.dtos.EmergencyShuttleManifestDto;

@Path("/cruises/{cruiseId}/emergencyShuttles")
@Produces(MediaType.APPLICATION_JSON)
public class EmergencyShuttleResource {
    
    private final EmergencyShuttleService emergencyShuttleService;
    
    @Inject
    public EmergencyShuttleResource(EmergencyShuttleService emergencyShuttleService) {
        this.emergencyShuttleService = emergencyShuttleService;
    }
    
    @GET
    public Response getEmergencyShuttleManifest(@PathParam("cruiseId") String cruiseId) {
        EmergencyShuttleManifestDto manifest = emergencyShuttleService.getEmergencyShuttleManifest(cruiseId);
        return Response.ok(manifest).build();
    }
}

