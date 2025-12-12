package uspace.application.cruise.emergencyShuttle;

import uspace.application.cruise.emergencyShuttle.dtos.EmergencyShuttleManifestDto;
import uspace.domain.cruise.emergencyShuttle.EmergencyShuttle;
import uspace.domain.cruise.emergencyShuttle.EmergencyShuttleManifest;
import uspace.domain.cruise.emergencyShuttle.EmergencyShuttleTraveler;
import uspace.domain.cruise.emergencyShuttle.EmergencyShuttleType;
import uspace.domain.cruise.booking.traveler.TravelerId;
import uspace.domain.cruise.booking.traveler.TravelerName;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmergencyShuttleAssemblerTest {

    @Test
    public void testToDto() {
        EmergencyShuttleAssembler assembler = new EmergencyShuttleAssembler();
        EmergencyShuttle shuttle = new EmergencyShuttle(EmergencyShuttleType.RESCUE_SHIP);

        TravelerId travelerId = new TravelerId("t1");
        TravelerName travelerName = new TravelerName("Alice");
        EmergencyShuttleTraveler traveler = new EmergencyShuttleTraveler(travelerId, travelerName);

        shuttle.addTraveler(traveler, 100);

        EmergencyShuttleManifest manifest = new EmergencyShuttleManifest(List.of(shuttle));

        EmergencyShuttleManifestDto dto = assembler.toDto(manifest);

        assertEquals(1, dto.getEmergencyShuttles().size());
        assertEquals("RESCUE_SHIP", dto.getEmergencyShuttles().get(0).getType());

        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        try {
            String json = mapper.writeValueAsString(dto);
            System.out.println("JSON: " + json);
            if (!json.contains("\"type\":\"RESCUE_SHIP\"")) {
                throw new RuntimeException("JSON type mismatch: " + json);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
