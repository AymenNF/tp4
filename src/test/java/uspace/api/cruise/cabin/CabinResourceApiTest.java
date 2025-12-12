package uspace.api.cruise.cabin;

import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import uspace.api.exceptions.ErrorResponse;
import uspace.application.cruise.cabin.CabinAttributionService;
import uspace.application.cruise.cabin.dtos.CabinAttributionDto;
import uspace.application.cruise.cabin.dtos.CabinDto;
import uspace.domain.cruise.cabin.exceptions.InvalidCabinAttributionCriteriaException;
import uspace.domain.cruise.exceptions.CruiseNotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("component")
class CabinResourceApiTest extends JerseyTest {
    private static final String ANY_CRUISE_ID = "JUPITER_MOONS_EXPLORATION_2085";
    private CabinAttributionService cabinAttributionServiceMock;

    @Override
    protected Application configure() {
        cabinAttributionServiceMock = mock(CabinAttributionService.class);
        return new ResourceConfig()
                .packages("uspace")
                .register(JacksonFeature.withoutExceptionMappers())
                .register(new CabinResource(cabinAttributionServiceMock));
    }

    @Test
    void givenValidCruiseAndCriteriaBookingDateTime_whenGetCabinAttributions_thenReturnCabins() {
        CabinAttributionDto expectedDto = createCabinAttributionDto();
        when(cabinAttributionServiceMock.getCabinAttributions(eq(ANY_CRUISE_ID), eq("bookingDateTime")))
                .thenReturn(expectedDto);

        Response response = callGetCabinAttributionsApi(ANY_CRUISE_ID, "bookingDateTime");

        assertEquals(200, response.getStatus());
        CabinAttributionDto actualDto = response.readEntity(CabinAttributionDto.class);
        assertEquals(2, actualDto.cabins.size());
        assertEquals("A1", actualDto.cabins.get(0).cabinId);
    }

    @Test
    void givenValidCruiseAndCriteriaTravelers_whenGetCabinAttributions_thenReturnCabins() {
        CabinAttributionDto expectedDto = createCabinAttributionDto();
        when(cabinAttributionServiceMock.getCabinAttributions(eq(ANY_CRUISE_ID), eq("travelers")))
                .thenReturn(expectedDto);

        Response response = callGetCabinAttributionsApi(ANY_CRUISE_ID, "travelers");

        assertEquals(200, response.getStatus());
        CabinAttributionDto actualDto = response.readEntity(CabinAttributionDto.class);
        assertEquals(2, actualDto.cabins.size());
    }

    @Test
    void givenCruiseNotFound_whenGetCabinAttributions_thenReturnCruiseNotFoundError() {
        doThrow(new CruiseNotFoundException()).when(cabinAttributionServiceMock)
                .getCabinAttributions(eq(ANY_CRUISE_ID), any());

        Response response = callGetCabinAttributionsApi(ANY_CRUISE_ID, "bookingDateTime");

        assertEquals(404, response.getStatus());
        ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);
        assertEquals("CRUISE_NOT_FOUND", errorResponse.error);
    }

    @Test
    void givenInvalidCriteria_whenGetCabinAttributions_thenReturnInvalidCriteriaError() {
        doThrow(new InvalidCabinAttributionCriteriaException("Invalid cabin attribution criteria. It must be one of: bookingDateTime, travelers."))
                .when(cabinAttributionServiceMock).getCabinAttributions(eq(ANY_CRUISE_ID), eq("invalid"));

        Response response = callGetCabinAttributionsApi(ANY_CRUISE_ID, "invalid");

        assertEquals(400, response.getStatus());
        ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);
        assertEquals("INVALID_CABIN_ATTRIBUTION_CRITERIA", errorResponse.error);
    }

    @Test
    void givenMissingCriteria_whenGetCabinAttributions_thenReturnInvalidCriteriaError() {
        WebTarget target = target("/cruises/" + ANY_CRUISE_ID + "/cabins");
        Response response = target.request().get();

        assertEquals(400, response.getStatus());
        ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);
        assertEquals("INVALID_CABIN_ATTRIBUTION_CRITERIA", errorResponse.error);
    }

    private Response callGetCabinAttributionsApi(String cruiseId, String criteria) {
        WebTarget target = target("/cruises/" + cruiseId + "/cabins")
                .queryParam("criteria", criteria);
        return target.request().get();
    }

    private CabinAttributionDto createCabinAttributionDto() {
        List<CabinDto> cabins = Arrays.asList(
                new CabinDto("A1", "booking1", "STANDARD"),
                new CabinDto("A2", "booking2", "STANDARD")
        );
        return new CabinAttributionDto(cabins);
    }
}

