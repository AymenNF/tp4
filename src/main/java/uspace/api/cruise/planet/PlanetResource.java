package uspace.api.cruise.planet;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import uspace.application.cruise.planet.PlanetService;
import uspace.application.cruise.planet.dtos.NewPlanetDto;
import uspace.application.cruise.planet.excursion.dtos.ExcursionDto;

@Path("/cruises/{cruiseId}/planets")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PlanetResource {

    private final PlanetService planetService;
    private final NewPlanetDtoValidator planetDtoValidator;
    private final ExcursionDtoValidator excursionDtoValidator;

    @Inject
    public PlanetResource(PlanetService planetService, NewPlanetDtoValidator planetDtoValidator, ExcursionDtoValidator excursionDtoValidator) {
        this.planetService = planetService;
        this.planetDtoValidator = planetDtoValidator;
        this.excursionDtoValidator = excursionDtoValidator;
    }

    @POST
    public Response addPlanet(@PathParam("cruiseId") String cruiseId, NewPlanetDto newPlanetDto) {
        planetDtoValidator.validate(newPlanetDto);
        planetService.addPlanetToItinerary(cruiseId, newPlanetDto);

        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/{planetName}/excursions")
    public Response addExcursion(@PathParam("cruiseId") String cruiseId, @PathParam("planetName") String planetName, ExcursionDto excursionDto) {
        excursionDtoValidator.validate(excursionDto);

        planetService.addExcursionToPlanet(cruiseId, planetName, excursionDto);

        return Response.status(Response.Status.CREATED).build();
    }
}
