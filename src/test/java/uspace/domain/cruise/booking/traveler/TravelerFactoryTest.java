package uspace.domain.cruise.booking.traveler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TravelerFactoryTest {

    private static final String ANY_NAME = "Bob";
    private TravelerFactory travelerFactory;

    @BeforeEach
    void createTravelerFactory() {
        travelerFactory = new TravelerFactory();
    }

    @Test
    void givenAdultCategory_whenCreate_thenCreateAdultTraveler() {
        TravelerCategory adultCategory = TravelerCategory.ADULT;

        Traveler traveler = travelerFactory.create(ANY_NAME, adultCategory);

        assertTrue(traveler instanceof AdultTraveler);
    }

    @Test
    void givenChildCategory_whenCreate_thenCreateChildTraveler() {
        TravelerCategory childCategory = TravelerCategory.CHILD;

        Traveler traveler = travelerFactory.create(ANY_NAME, childCategory);

        assertTrue(traveler instanceof ChildTraveler);
    }

    @Test
    void givenSeniorCategory_whenCreate_thenCreateSeniorTraveler() {
        TravelerCategory seniorCategory = TravelerCategory.SENIOR;

        Traveler traveler = travelerFactory.create(ANY_NAME, seniorCategory);

        assertTrue(traveler instanceof SeniorTraveler);
    }
}