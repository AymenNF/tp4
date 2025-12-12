package uspace.infra.persistence.hibernate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import uspace.domain.cruise.Cruise;
import uspace.domain.cruise.CruiseId;
import uspace.domain.cruise.dateTime.CruiseDateTime;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Tag("component")
class HibernateCruiseRepositoryIntegrationTest {

    private static final CruiseId ANY_CRUISE_ID = new CruiseId("a_cruise_id");
    private static final CruiseId ANY_SAME_CRUISE_ID = new CruiseId("a_cruise_id");
    private static final CruiseId ANY_DIFFERENT_CRUISE_ID = new CruiseId("another_cruise_id");
    private static final CruiseDateTime ANY_DATE_TIME = new CruiseDateTime(LocalDateTime.of(2084, 4, 8, 12, 30));
    private static final Cruise ANY_CRUISE = new Cruise(ANY_CRUISE_ID, ANY_DATE_TIME, ANY_DATE_TIME, null, null, null, null, null, null);
    private HibernateCruiseRepository hibernateCruiseRepository;

    @BeforeEach
    void createHibernateCruiseRepository() {
        hibernateCruiseRepository = new HibernateCruiseRepository();
    }

    @AfterEach
    void tearDownSession() {
        HibernateUtil.shutdown();
    }

    @Test
    void givenASavedCruise_whenFindById_thenReturnsTheCruise() {
        hibernateCruiseRepository.save(ANY_CRUISE);

        Cruise foundCruise = hibernateCruiseRepository.findById(ANY_SAME_CRUISE_ID);

        assertEquals(ANY_CRUISE_ID, foundCruise.getId());
        assertEquals(ANY_DATE_TIME, foundCruise.getDepartureDateTime());
        assertEquals(ANY_DATE_TIME, foundCruise.getEndDateTime());
    }

    @Test
    void givenANotSavedCruise_whenFindById_thenReturnsNull() {
        hibernateCruiseRepository.save(ANY_CRUISE);

        Cruise foundCruise = hibernateCruiseRepository.findById(ANY_DIFFERENT_CRUISE_ID);

        assertNull(foundCruise);
    }

    @Test
    void givenAnAlreadySavedCruise_whenSave_thenUpdatesTheCruise() {
        hibernateCruiseRepository.save(ANY_CRUISE);
        CruiseDateTime anyDifferentDateTime = new CruiseDateTime(LocalDateTime.of(1999, 1, 1, 0, 0, 0));
        Cruise updatedCruise = new Cruise(ANY_CRUISE_ID, anyDifferentDateTime, ANY_DATE_TIME, null, null, null, null, null, null);

        hibernateCruiseRepository.save(updatedCruise);

        Cruise foundCruise = hibernateCruiseRepository.findById(ANY_CRUISE_ID);
        assertEquals(ANY_CRUISE_ID, foundCruise.getId());
        assertEquals(anyDifferentDateTime, foundCruise.getDepartureDateTime());
        assertEquals(ANY_DATE_TIME, foundCruise.getEndDateTime());
    }
}