package uspace.api.cruise.booking;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import uspace.application.cruise.booking.dtos.BookingDto;
import uspace.application.cruise.booking.dtos.BookingIdDto;
import uspace.application.cruise.booking.BookingService;
import uspace.application.cruise.booking.dtos.newBooking.NewBookingDto;

import java.net.URI;

@Path("/cruises/{cruiseId}/bookings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookingResource {

    private final BookingService bookingService;
    private final NewBookingDtoValidator newBookingDtoValidator;

    @Inject
    public BookingResource(BookingService bookingService, NewBookingDtoValidator newBookingDtoValidator) {
        this.bookingService = bookingService;
        this.newBookingDtoValidator = newBookingDtoValidator;
    }

    @POST
    public Response createBooking(
            @PathParam("cruiseId") String cruiseId,
            NewBookingDto newBookingDto,
            @Context UriInfo uriInfo) {
        newBookingDtoValidator.validate(newBookingDto);

        BookingIdDto bookingIdDto = bookingService.createBooking(cruiseId, newBookingDto);

        URI location = uriInfo.getAbsolutePathBuilder().path(bookingIdDto.id).build();
        return Response.created(location).build();
    }

    @GET
    @Path("/{bookingId}")
    public Response getBooking(@PathParam("cruiseId") String cruiseId, @PathParam("bookingId") String bookingId) {
        BookingDto bookingDto = bookingService.findBooking(cruiseId, bookingId);
        return Response.ok(bookingDto).build();
    }
}
