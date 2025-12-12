package uspace.api.cruise.booking.traveler;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import uspace.api.cruise.booking.traveler.zeroGravityExperienceRequest.ZeroGravityExperienceRequestValidator;
import uspace.api.exceptions.ErrorResponse;
import uspace.application.cruise.booking.traveler.TravelerService;
import uspace.application.cruise.booking.traveler.dtos.SportBookingDto;
import uspace.domain.cruise.booking.traveler.exceptions.TravelerNotFoundException;
import uspace.domain.cruise.exceptions.CruiseNotFoundException;
import uspace.domain.cruise.sport.exceptions.SportAlreadyBookedException;
import uspace.domain.cruise.sport.exceptions.SportNotBookableException;
import uspace.domain.cruise.sport.exceptions.SportNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@Tag("component")
class TravelerResourceApiTest extends JerseyTest {
    private static final String ANY_CRUISE_ID_STR = "ANY_CRUISE_ID";
    private static final String ANY_BOOKING_ID_STR = "ANY_BOOKING_ID";
    private static final String ANY_TRAVELER_ID_STR = "ANY_TRAVELER_ID";
    private static final String ANY_SPORT = "anySport";
    private static final SportBookingDto ANY_SPORT_BOOKING_DTO = new SportBookingDto(ANY_SPORT);
    private TravelerService travelerServiceMock;

    @Override
    protected Application configure() {
        travelerServiceMock = mock(TravelerService.class);
        ZeroGravityExperienceRequestValidator zeroGravityExperienceRequestValidatorMock = mock(ZeroGravityExperienceRequestValidator.class);
        SportBookingDtoValidator sportBookingDtoValidator = new SportBookingDtoValidator();

        return new ResourceConfig()
                .packages("uspace")
                .register(JacksonFeature.withoutExceptionMappers())
                .register(new TravelerResource(travelerServiceMock, zeroGravityExperienceRequestValidatorMock, sportBookingDtoValidator));
    }

    @Test
    void givenSportDtoWithMissingParameterSport_whenBookSport_thenReturnMissingParameterError() {
        SportBookingDto sportDtoWithoutSport = new SportBookingDto(null);

        Response response = callPostBookSportApi(sportDtoWithoutSport);

        assertEquals(400, response.getStatus());
        ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);
        assertEquals("MISSING_PARAMETER", errorResponse.error);
    }

    @Test
    void givenCruiseNotFound_whenBookSport_thenReturnCruiseNotFoundError() {
        doThrow(new CruiseNotFoundException())
                .when(travelerServiceMock).bookSport(ANY_CRUISE_ID_STR, ANY_BOOKING_ID_STR, ANY_TRAVELER_ID_STR, ANY_SPORT);

        Response response = callPostBookSportApi(ANY_SPORT_BOOKING_DTO);

        assertEquals(404, response.getStatus());
        ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);
        assertEquals("CRUISE_NOT_FOUND", errorResponse.error);
    }

    @Test
    void givenTravelerNotFound_whenBookSport_thenReturnTravelerNotFoundError() {
        doThrow(new TravelerNotFoundException())
                .when(travelerServiceMock).bookSport(ANY_CRUISE_ID_STR, ANY_BOOKING_ID_STR, ANY_TRAVELER_ID_STR, ANY_SPORT);

        Response response = callPostBookSportApi(ANY_SPORT_BOOKING_DTO);

        assertEquals(404, response.getStatus());
        ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);
        assertEquals("TRAVELER_NOT_FOUND", errorResponse.error);
    }

    @Test
    void givenSportNotFound_whenBookSport_thenReturnSportNotFoundError() {
        doThrow(new SportNotFoundException())
                .when(travelerServiceMock).bookSport(ANY_CRUISE_ID_STR, ANY_BOOKING_ID_STR, ANY_TRAVELER_ID_STR, ANY_SPORT);

        Response response = callPostBookSportApi(ANY_SPORT_BOOKING_DTO);

        assertEquals(404, response.getStatus());
        ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);
        assertEquals("SPORT_NOT_FOUND", errorResponse.error);
    }

    @Test
    void givenSportNotBookable_whenBookSport_thenReturnSportNotBookableError() {
        doThrow(new SportNotBookableException())
                .when(travelerServiceMock).bookSport(ANY_CRUISE_ID_STR, ANY_BOOKING_ID_STR, ANY_TRAVELER_ID_STR, ANY_SPORT);

        Response response = callPostBookSportApi(ANY_SPORT_BOOKING_DTO);

        assertEquals(400, response.getStatus());
        ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);
        assertEquals("SPORT_NOT_BOOKABLE", errorResponse.error);
    }

    @Test
    void givenSportAlreadyBooked_whenBookSport_thenReturnSportAlreadyBookedError() {
        doThrow(new SportAlreadyBookedException())
                .when(travelerServiceMock).bookSport(ANY_CRUISE_ID_STR, ANY_BOOKING_ID_STR, ANY_TRAVELER_ID_STR, ANY_SPORT);

        Response response = callPostBookSportApi(ANY_SPORT_BOOKING_DTO);

        assertEquals(400, response.getStatus());
        ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);
        assertEquals("SPORT_ALREADY_BOOKED", errorResponse.error);
    }

    @Test
    void givenValidSportBooking_whenBookSport_thenReturnOk() {
        Response response = callPostBookSportApi(ANY_SPORT_BOOKING_DTO);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    private Response callPostBookSportApi(SportBookingDto sportBookingDto) {
        WebTarget target = target("/cruises/" + ANY_CRUISE_ID_STR + "/bookings/" + ANY_BOOKING_ID_STR + "/travelers/" + ANY_TRAVELER_ID_STR + "/sports");
        return target.request().post(Entity.json(sportBookingDto));
    }
}