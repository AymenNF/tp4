package uspace.api.cruise.booking.traveler;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import uspace.api.cruise.booking.traveler.zeroGravityExperienceRequest.ZeroGravityExperienceRequest;
import uspace.api.cruise.booking.traveler.zeroGravityExperienceRequest.ZeroGravityExperienceRequestValidator;
import uspace.application.cruise.booking.traveler.TravelerService;
import uspace.application.cruise.booking.traveler.dtos.SportBookingDto;

@Path("/cruises/{cruiseId}/bookings/{bookingId}/travelers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TravelerResource {

    private final TravelerService travelerService;
    private final ZeroGravityExperienceRequestValidator zeroGravityExperienceRequestValidator;
    private final SportBookingDtoValidator sportBookingDtoValidator;

    @Inject
    public TravelerResource(TravelerService travelerService, ZeroGravityExperienceRequestValidator zeroGravityExperienceRequestValidator,
                            SportBookingDtoValidator sportBookingDtoValidator) {
        this.travelerService = travelerService;
        this.zeroGravityExperienceRequestValidator = zeroGravityExperienceRequestValidator;
        this.sportBookingDtoValidator = sportBookingDtoValidator;
    }

    @POST
    @Path("/{travelerId}/zeroGravityExperiences")
    public Response bookZeroGravityExperience(@PathParam("cruiseId") String cruiseId,
                                              @PathParam("bookingId") String bookingId,
                                              @PathParam("travelerId") String travelerId,
                                              ZeroGravityExperienceRequest zeroGravityExperienceRequest) {
        zeroGravityExperienceRequestValidator.validate(zeroGravityExperienceRequest);

        travelerService.bookZeroGravityExperience(cruiseId, bookingId, travelerId, zeroGravityExperienceRequest.experienceBookingDateTime);

        return Response.ok().build();
    }

    @POST
    @Path("/{travelerId}/sports")
    public Response bookSport(@PathParam("cruiseId") String cruiseId,
                              @PathParam("bookingId") String bookingId,
                              @PathParam("travelerId") String travelerId,
                              SportBookingDto sportBookingDto) {
        sportBookingDtoValidator.validate(sportBookingDto);

        travelerService.bookSport(cruiseId, bookingId, travelerId, sportBookingDto.sport);

        return Response.ok().build();
    }
}
