package uspace.infra.sport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uspace.domain.cruise.CruiseId;
import uspace.domain.cruise.sport.Sport;
import uspace.domain.cruise.sport.SportAudience;
import uspace.domain.cruise.sport.SportName;

import static org.junit.jupiter.api.Assertions.*;

class SportJsonReaderTest {
    private static final String SPORTS_JSON = "src/test/java/uspace/infra/sport/dataTest/sports.json";
    private static final String ANY_SPORT_NAME_EXISTING_WITH_CRUISE = "Zero-Gravity Basketball";
    private static final String ANY_SPORT_NAME_IN_JSON_AND_NOT_WITH_CRUISE = "Mars Rover Challenge";
    private static final String ANY_SPORT_NAME_NOT_IN_JSON = "NotInJson";
    private static final CruiseId ANY_CRUISE_ID = new CruiseId("JUPITER_MOONS_EXPLORATION_2085");
    private SportJsonReader sportJsonReader;

    @BeforeEach
    void createSportJsonReader() {
        sportJsonReader = new SportJsonReader(SPORTS_JSON);
    }

    @Test
    void givenSportNameExistingWithCruise_whenFindSport_thenReturnSport() {
        Sport expectedSport = new Sport(new SportName(ANY_SPORT_NAME_EXISTING_WITH_CRUISE), new SportAudience("ALL"));

        Sport sport = sportJsonReader.findSport(ANY_SPORT_NAME_EXISTING_WITH_CRUISE, ANY_CRUISE_ID);

        assertEquals(expectedSport.getName(), sport.getName());
        assertEquals(expectedSport.getAudience(), sport.getAudience());
    }

    @Test
    void givenSportNameInJsonAndNotWithCruise_whenFindSport_thenReturnNull() {
        Sport sport = sportJsonReader.findSport(ANY_SPORT_NAME_IN_JSON_AND_NOT_WITH_CRUISE, ANY_CRUISE_ID);

        assertNull(sport);
    }

    @Test
    void givenSportNameNotInJson_whenFindSport_thenReturnNull() {
        Sport sport = sportJsonReader.findSport(ANY_SPORT_NAME_NOT_IN_JSON, ANY_CRUISE_ID);

        assertNull(sport);
    }
}