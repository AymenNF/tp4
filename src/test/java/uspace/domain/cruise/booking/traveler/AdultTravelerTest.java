package uspace.domain.cruise.booking.traveler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uspace.domain.cruise.booking.traveler.badge.Badge;
import uspace.domain.cruise.money.Money;
import uspace.domain.cruise.sport.Sport;
import uspace.domain.cruise.sport.SportAudience;
import uspace.domain.cruise.sport.SportName;
import uspace.domain.cruise.sport.exceptions.SportAlreadyBookedException;
import uspace.domain.cruise.sport.exceptions.SportNotBookableException;
import uspace.domain.cruise.zeroGravityExperience.ZeroGravityExperience;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdultTravelerTest {
    private static final TravelerId ANY_ID = new TravelerId();
    private static final TravelerName ANY_NAME = new TravelerName("Bob");
    private static final StandardCostMultiplier ANY_STANDARD_COST_MULTIPLIER = new StandardCostMultiplier(2);
    private static final List<Traveler> ANY_TRAVELERS_IN_BOOKING = List.of();
    private static final SportName ANY_SPORT_NAME = new SportName("anysport");
    private static final SportAudience ANY_SPORT_AUDIENCE = SportAudience.ALL();
    private static final Sport ANY_SPORT = new Sport(ANY_SPORT_NAME, ANY_SPORT_AUDIENCE);
    private List<Sport> anySports;
    private AdultTraveler adultTraveler;
    @Mock
    private ZeroGravityExperience zeroGravityExperience;
    @Mock
    private Sport sportMock;

    @BeforeEach
    void createAdultTraveler() {
        List<Badge> anyBadges = new ArrayList<>();
        anySports = new ArrayList<>();
        adultTraveler = new AdultTraveler(ANY_ID, ANY_NAME, anyBadges, ANY_STANDARD_COST_MULTIPLIER, anySports);
    }

    @Test
    void whenCreate_thenHasTravelerCategoryAdult() {
        TravelerCategory expectedCategory = TravelerCategory.ADULT;

        TravelerCategory actualCategory = adultTraveler.getCategory();

        assertEquals(expectedCategory, actualCategory);
    }

    @Test
    void whenBookZeroGravityExperience_thenZeroGravityExperienceIsBooked() {
        adultTraveler.bookZeroGravityExperience(zeroGravityExperience, ANY_TRAVELERS_IN_BOOKING);

        verify(zeroGravityExperience).book(ANY_ID);
    }

    @Test
    void givenNoZeroGBadge_whenBookZeroGravityExperience_thenEarnZeroGBadge() {
        List<Badge> noBadge = new ArrayList<>();
        AdultTraveler adultTravelerWithoutBadge = new AdultTraveler(ANY_ID, ANY_NAME, noBadge, ANY_STANDARD_COST_MULTIPLIER,
                                                                    anySports);

        adultTravelerWithoutBadge.bookZeroGravityExperience(zeroGravityExperience, ANY_TRAVELERS_IN_BOOKING);

        assertTrue(adultTravelerWithoutBadge.getBadges().contains(Badge.ZERO_G));
    }

    @Test
    void givenZeroGBadge_whenBookZeroGravityExperience_thenDoNotEarnSecondZeroGBadge() {
        List<Badge> badgesWithZeroG = List.of(Badge.ZERO_G);
        AdultTraveler adultTravelerWithZeroGBadge = new AdultTraveler(ANY_ID, ANY_NAME, badgesWithZeroG, ANY_STANDARD_COST_MULTIPLIER,
                                                                      anySports);
        int expectedNumberOfZeroGBadges = 1;

        adultTravelerWithZeroGBadge.bookZeroGravityExperience(zeroGravityExperience, ANY_TRAVELERS_IN_BOOKING);

        int numberOfZeroGBadges = (int) adultTravelerWithZeroGBadge.getBadges().stream().filter(Badge.ZERO_G::equals).count();
        assertEquals(expectedNumberOfZeroGBadges, numberOfZeroGBadges);
    }

    @Test
    void whenCalculateStandardBookingCost_thenReturnCabinBaseCostPerPersonMultiplyByStandardCostMultiplier() {
        Money cabinBaseCostPerPerson = new Money(100);
        Money bookingCostExpected = new Money(200);

        Money bookingCost = adultTraveler.calculateStandardBookingCost(cabinBaseCostPerPerson);

        assertEquals(bookingCostExpected, bookingCost);
    }

    @Test
    void givenSportAlreadyBooked_whenBookSport_thenThrowSportAlreadyBookedException() {
        adultTraveler.bookSport(ANY_SPORT);

        Executable bookSport = () -> adultTraveler.bookSport(ANY_SPORT);

        assertThrows(SportAlreadyBookedException.class, bookSport);
    }

    @Test
    void givenSportNotBookableForAdult_whenBookSport_thenThrowSportNotBookableException() {
        when(sportMock.canBeBookedBy(TravelerCategory.ADULT)).thenReturn(false);

        Executable bookSport = () -> adultTraveler.bookSport(sportMock);

        assertThrows(SportNotBookableException.class, bookSport);
    }

    @Test
    void givenSportBookableForAdult_whenBookSport_thenEarnSportyBadge() {
        when(sportMock.canBeBookedBy(TravelerCategory.ADULT)).thenReturn(true);

        adultTraveler.bookSport(sportMock);

        assertTrue(adultTraveler.getBadges().contains(Badge.SPORTY));
    }

    @Test
    void givenSportBookableForAdult_whenBookAnotherSport_thenDoNotEarnSecondSportyBadge() {
        when(sportMock.canBeBookedBy(TravelerCategory.ADULT)).thenReturn(true);
        List<Badge> badgesWithSporty = List.of(Badge.SPORTY);
        AdultTraveler adultTravelerWithSportyBadge = new AdultTraveler(ANY_ID, ANY_NAME, badgesWithSporty, ANY_STANDARD_COST_MULTIPLIER,
                                                                       anySports);
        int expectedNumberOfZSportyBadges = 1;

        adultTravelerWithSportyBadge.bookSport(sportMock);

        int numberOfZeroGBadges = (int) adultTravelerWithSportyBadge.getBadges().stream().filter(Badge.SPORTY::equals).count();
        assertEquals(expectedNumberOfZSportyBadges, numberOfZeroGBadges);
    }
}