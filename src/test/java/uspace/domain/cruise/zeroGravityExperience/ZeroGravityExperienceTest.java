package uspace.domain.cruise.zeroGravityExperience;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import uspace.domain.cruise.booking.traveler.TravelerId;
import uspace.domain.cruise.zeroGravityExperience.exceptions.ZeroGravityExperienceAlreadyBookedException;
import uspace.domain.cruise.zeroGravityExperience.exceptions.ZeroGravityExperienceFullException;

import static org.junit.jupiter.api.Assertions.*;

class ZeroGravityExperienceTest {

    private static final ZeroGravityExperienceId ANY_ZERO_GRAVITY_EXPERIENCE_ID = new ZeroGravityExperienceId();
    private static final int ANY_CAPACITY = 3;
    private static final String ANY_TRAVELER_ID_STR = "ANY_TRAVELER_ID";
    private static final TravelerId ANY_TRAVELER_ID = new TravelerId(ANY_TRAVELER_ID_STR);
    private static final TravelerId ANY_SAME_TRAVELER_ID = new TravelerId(ANY_TRAVELER_ID_STR);
    private ZeroGravityExperience zeroGravityExperience;

    @BeforeEach
    void createZeroGravityExperience() {
        zeroGravityExperience = new ZeroGravityExperience(ANY_ZERO_GRAVITY_EXPERIENCE_ID, ANY_CAPACITY);
    }

    @Test
    void givenSameTravelerIdAlreadyBooked_whenBook_thenThrowZeroGravityExperienceAlreadyBookedException() {
        zeroGravityExperience.book(ANY_TRAVELER_ID);

        Executable bookZeroGravityExperienceAgain = () -> zeroGravityExperience.book(ANY_SAME_TRAVELER_ID);

        assertThrows(ZeroGravityExperienceAlreadyBookedException.class, bookZeroGravityExperienceAgain);
    }

    @Test
    void givenZeroGravityExperienceFullAndTravelerNotAlreadyBooked_whenBook_thenThrowZeroGravityExperienceFullException() {
        bookAsMuchTravelersAsCapacity();

        Executable bookZeroGravityExperienceAgain = () -> zeroGravityExperience.book(ANY_TRAVELER_ID);

        assertThrows(ZeroGravityExperienceFullException.class, bookZeroGravityExperienceAgain);
    }

    @Test
    void givenZeroGravityExperienceNotFullAndTravelerNotAlreadyBooked_whenBook_thenTravelerHasBooked() {
        zeroGravityExperience.book(ANY_TRAVELER_ID);

        assertTrue(zeroGravityExperience.hasTravelerBooked(ANY_TRAVELER_ID));
    }

    private void bookAsMuchTravelersAsCapacity() {
        zeroGravityExperience.book(new TravelerId("1"));
        zeroGravityExperience.book(new TravelerId("2"));
        zeroGravityExperience.book(new TravelerId("3"));
    }
}