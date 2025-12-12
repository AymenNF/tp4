package uspace.api.cruise.emergencyShuttle;

import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import uspace.api.exceptions.ErrorResponse;
import uspace.application.cruise.emergencyShuttle.EmergencyShuttleService;
import uspace.application.cruise.emergencyShuttle.dtos.EmergencyShuttleCrewMemberDto;
import uspace.application.cruise.emergencyShuttle.dtos.EmergencyShuttleDto;
import uspace.application.cruise.emergencyShuttle.dtos.EmergencyShuttleManifestDto;
import uspace.application.cruise.emergencyShuttle.dtos.EmergencyShuttleTravelerDto;
import uspace.domain.cruise.exceptions.CruiseNotFoundException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("component")
class EmergencyShuttleResourceApiTest extends JerseyTest {
    private static final String ANY_CRUISE_ID = "JUPITER_MOONS_EXPLORATION_2085";
    private EmergencyShuttleService emergencyShuttleServiceMock;

    @Override
    protected Application configure() {
        emergencyShuttleServiceMock = mock(EmergencyShuttleService.class);
        return new ResourceConfig()
                .packages("uspace")
                .register(JacksonFeature.withoutExceptionMappers())
                .register(new EmergencyShuttleResource(emergencyShuttleServiceMock));
    }

    @Test
    void givenValidCruise_whenGetEmergencyShuttleManifest_thenReturnManifest() {
        EmergencyShuttleManifestDto expectedDto = createEmergencyShuttleManifestDto();
        when(emergencyShuttleServiceMock.getEmergencyShuttleManifest(eq(ANY_CRUISE_ID)))
                .thenReturn(expectedDto);

        Response response = callGetEmergencyShuttleManifestApi(ANY_CRUISE_ID);

        assertEquals(200, response.getStatus());
        EmergencyShuttleManifestDto actualDto = response.readEntity(EmergencyShuttleManifestDto.class);
        assertEquals(200000, actualDto.cost);
        assertEquals(2, actualDto.emergencyShuttles.size());
        assertEquals("RESCUE_SHIP", actualDto.emergencyShuttles.get(0).type);
        assertEquals("STANDARD_SHUTTLE", actualDto.emergencyShuttles.get(1).type);
    }

    @Test
    void givenCruiseNotFound_whenGetEmergencyShuttleManifest_thenReturnCruiseNotFoundError() {
        doThrow(new CruiseNotFoundException())
                .when(emergencyShuttleServiceMock).getEmergencyShuttleManifest(eq(ANY_CRUISE_ID));

        Response response = callGetEmergencyShuttleManifestApi(ANY_CRUISE_ID);

        assertEquals(404, response.getStatus());
        ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);
        assertEquals("CRUISE_NOT_FOUND", errorResponse.error);
    }

    @Test
    void givenEmptyManifest_whenGetEmergencyShuttleManifest_thenReturnEmptyList() {
        EmergencyShuttleManifestDto emptyDto = new EmergencyShuttleManifestDto(0, Arrays.asList());
        when(emergencyShuttleServiceMock.getEmergencyShuttleManifest(eq(ANY_CRUISE_ID)))
                .thenReturn(emptyDto);

        Response response = callGetEmergencyShuttleManifestApi(ANY_CRUISE_ID);

        assertEquals(200, response.getStatus());
        EmergencyShuttleManifestDto actualDto = response.readEntity(EmergencyShuttleManifestDto.class);
        assertEquals(0, actualDto.cost);
        assertTrue(actualDto.emergencyShuttles.isEmpty());
    }

    private Response callGetEmergencyShuttleManifestApi(String cruiseId) {
        WebTarget target = target("/cruises/" + cruiseId + "/emergencyShuttles");
        return target.request().get();
    }

    private EmergencyShuttleManifestDto createEmergencyShuttleManifestDto() {
        EmergencyShuttleTravelerDto traveler1 = new EmergencyShuttleTravelerDto("traveler-1", "John Doe");
        EmergencyShuttleTravelerDto traveler2 = new EmergencyShuttleTravelerDto("traveler-2", "Jane Doe");
        
        EmergencyShuttleCrewMemberDto crew1 = new EmergencyShuttleCrewMemberDto("ABC123", "Crew Member 1");
        
        EmergencyShuttleDto shuttle1 = new EmergencyShuttleDto(
                "RESCUE_SHIP",
                Arrays.asList(traveler1, traveler2),
                Arrays.asList(crew1)
        );
        
        EmergencyShuttleDto shuttle2 = new EmergencyShuttleDto(
                "STANDARD_SHUTTLE",
                Arrays.asList(),
                Arrays.asList()
        );
        
        return new EmergencyShuttleManifestDto(200000, Arrays.asList(shuttle1, shuttle2));
    }
}

