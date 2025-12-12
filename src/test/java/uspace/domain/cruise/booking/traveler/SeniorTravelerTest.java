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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SeniorTravelerTest {
    private static final TravelerId ANY_ID = new TravelerId();
    private static final TravelerName ANY_NAME = new TravelerName("Bob");
    private static final List<Badge> ANY_BADGES = new ArrayList<>();
    private static final List<Badge> NO_BADGE = new ArrayList<>();
    private static final List<Sport> NO_SPORT = new ArrayList<>();
    private static final StandardCostMultiplier ANY_STANDARD_COST_MULTIPLIER = new StandardCostMultiplier(2);
    private static final List<Traveler> ANY_TRAVELERS_IN_BOOKING = List.of();
    private static final SportName ANY_SPORT_NAME = new SportName("anysport");
    private static final SportAudience ANY_SPORT_AUDIENCE = SportAudience.ALL();
    private static final Sport ANY_SPORT = new Sport(ANY_SPORT_NAME, ANY_SPORT_AUDIENCE);
    private List<Sport> anySports;
    private SeniorTraveler seniorTraveler;
    @Mock
    private ZeroGravityExperience zeroGravityExperience;
    @Mock
    private Sport sportMock;

    @BeforeEach
    void createSeniorTraveler() {
        anySports = new ArrayList<>();
        seniorTraveler = new SeniorTraveler(ANY_ID, ANY_NAME, ANY_BADGES, ANY_STANDARD_COST_MULTIPLIER, anySports);
    }

    @Test
    void whenCreate_thenHasTravelerCategorySenior() {
        TravelerCategory expectedCategory = TravelerCategory.SENIOR;

        TravelerCategory actualCategory = seniorTraveler.getCategory();

        assertEquals(expectedCategory, actualCategory);
    }

    @Test
    void whenBookZeroGravityExperience_thenZeroGravityExperienceIsBooked() {
        seniorTraveler.bookZeroGravityExperience(zeroGravityExperience, ANY_TRAVELERS_IN_BOOKING);

        verify(zeroGravityExperience).book(ANY_ID);
    }

    @Test
    void givenNoZeroGBadge_whenBookZeroGravityExperience_thenEarnZeroGBadge() {
        SeniorTraveler seniorTravelerWithoutBadge = new SeniorTraveler(ANY_ID, ANY_NAME, NO_BADGE, ANY_STANDARD_COST_MULTIPLIER, NO_SPORT);

        seniorTravelerWithoutBadge.bookZeroGravityExperience(zeroGravityExperience, ANY_TRAVELERS_IN_BOOKING);

        assertTrue(seniorTravelerWithoutBadge.getBadges().contains(Badge.ZERO_G));
    }

    @Test
    void givenZeroGBadge_whenBookZeroGravityExperience_thenDoNotEarnSecondZeroGBadge() {
        List<Badge> badgesWithZeroG = new ArrayList<>(List.of(Badge.ZERO_G));
        SeniorTraveler seniorTravelerWithZeroGBadge = new SeniorTraveler(ANY_ID, ANY_NAME, badgesWithZeroG, ANY_STANDARD_COST_MULTIPLIER, NO_SPORT);
        int expectedNumberOfZeroGBadges = 1;

        seniorTravelerWithZeroGBadge.bookZeroGravityExperience(zeroGravityExperience, ANY_TRAVELERS_IN_BOOKING);

        int numberOfZeroGBadges = (int) seniorTravelerWithZeroGBadge.getBadges().stream().filter(Badge.ZERO_G::equals).count();
        assertEquals(expectedNumberOfZeroGBadges, numberOfZeroGBadges);
    }

    @Test
    void givenNoStillGotItBadge_whenBookZeroGravityExperience_thenEarnStillGotItBadge() {
        SeniorTraveler seniorTravelerWithoutBadge = new SeniorTraveler(ANY_ID, ANY_NAME, NO_BADGE, ANY_STANDARD_COST_MULTIPLIER, NO_SPORT);

        seniorTravelerWithoutBadge.bookZeroGravityExperience(zeroGravityExperience, ANY_TRAVELERS_IN_BOOKING);

        assertTrue(seniorTravelerWithoutBadge.getBadges().contains(Badge.STILL_GOT_IT));
    }

    @Test
    void givenStillGotItBadge_whenBookZeroGravityExperience_thenDoNotEarnSecondStillGotItBadge() {
        List<Badge> badgesWithStillGotIt = new ArrayList<>(List.of(Badge.STILL_GOT_IT));
        SeniorTraveler seniorTravelerWithStillGotItBadge = new SeniorTraveler(ANY_ID, ANY_NAME, badgesWithStillGotIt, ANY_STANDARD_COST_MULTIPLIER, NO_SPORT);
        int expectedNumberOfStillGotItBadges = 1;

        seniorTravelerWithStillGotItBadge.bookZeroGravityExperience(zeroGravityExperience, ANY_TRAVELERS_IN_BOOKING);

        int numberOfStillGotItBadges = (int) seniorTravelerWithStillGotItBadge.getBadges().stream().filter(Badge.STILL_GOT_IT::equals).count();
        assertEquals(expectedNumberOfStillGotItBadges, numberOfStillGotItBadges);
    }

    @Test
    void whenCalculateStandardBookingCost_thenReturnCabinBaseCostPerPersonMultiplyByStandardCostMultiplier() {
        Money cabinBaseCostPerPerson = new Money(100);
        Money bookingCostExpected = new Money(200);

        Money bookingCost = seniorTraveler.calculateStandardBookingCost(cabinBaseCostPerPerson);

        assertEquals(bookingCostExpected, bookingCost);
    }

    @Test
    void givenSportAlreadyBooked_whenBookSport_thenThrowSportAlreadyBookedException() {
        seniorTraveler.bookSport(ANY_SPORT);

        Executable bookSport = () -> seniorTraveler.bookSport(ANY_SPORT);

        assertThrows(SportAlreadyBookedException.class, bookSport);
    }

    @Test
    void givenSportNotBookableForAdult_whenBookSport_thenThrowSportNotBookableException() {
        when(sportMock.canBeBookedBy(TravelerCategory.SENIOR)).thenReturn(false);

        Executable bookSport = () -> seniorTraveler.bookSport(sportMock);

        assertThrows(SportNotBookableException.class, bookSport);
    }

    @Test
    void givenSportBookableForAdult_whenBookSport_thenEarnSportyBadge() {
        when(sportMock.canBeBookedBy(TravelerCategory.SENIOR)).thenReturn(true);

        seniorTraveler.bookSport(sportMock);

        assertTrue(seniorTraveler.getBadges().contains(Badge.SPORTY));
    }

    @Test
    void givenSportBookableForSenior_whenBookAnotherSport_thenDoNotEarnSecondSportyBadge() {
        when(sportMock.canBeBookedBy(TravelerCategory.SENIOR)).thenReturn(true);
        List<Badge> badgesWithSporty = new ArrayList<>(List.of(Badge.SPORTY));
        SeniorTraveler seniorTravelerWithSportyBadge = new SeniorTraveler(ANY_ID, ANY_NAME, badgesWithSporty, ANY_STANDARD_COST_MULTIPLIER,
                                                                       anySports);
        int expectedNumberOfZSportyBadges = 1;

        seniorTravelerWithSportyBadge.bookSport(sportMock);

        int numberOfZeroGBadges = (int) seniorTravelerWithSportyBadge.getBadges().stream().filter(Badge.SPORTY::equals).count();
        assertEquals(expectedNumberOfZSportyBadges, numberOfZeroGBadges);
    }

    @Test
    void givenSportBookableForSeniorAndSportyBadgeAlreadyEarned_whenBookAnotherSport_thenEarnSuperSportyBadge() {
        when(sportMock.canBeBookedBy(TravelerCategory.SENIOR)).thenReturn(true);
        List<Badge> badgesWithSporty = new ArrayList<>(List.of(Badge.SPORTY));
        SeniorTraveler seniorTravelerWithSportyBadge = new SeniorTraveler(ANY_ID, ANY_NAME, badgesWithSporty, ANY_STANDARD_COST_MULTIPLIER,
                                                                          anySports);

        seniorTravelerWithSportyBadge.bookSport(sportMock);

        assertTrue(seniorTravelerWithSportyBadge.getBadges().contains(Badge.SUPER_SPORTY));
    }

    @Test
    void givenSportBookableForSeniorAndSuperSportyBadgeAlreadyEarned_whenBookAnotherSport_thenDoNotEarnSecondSuperSportyBadge() {
        when(sportMock.canBeBookedBy(TravelerCategory.SENIOR)).thenReturn(true);
        List<Badge> badgesWithSportyAndSuperSporty = List.of(Badge.SPORTY, Badge.SUPER_SPORTY);
        SeniorTraveler seniorTravelerWithSuperSportyBadge = new SeniorTraveler(ANY_ID, ANY_NAME, badgesWithSportyAndSuperSporty,
                                                                               ANY_STANDARD_COST_MULTIPLIER, anySports);
        int expectedNumberOfSuperSportyBadges = 1;

        seniorTravelerWithSuperSportyBadge.bookSport(sportMock);

        int numberOfSuperSportyBadges = (int) seniorTravelerWithSuperSportyBadge.getBadges().stream().filter(Badge.SUPER_SPORTY::equals).count();
        assertEquals(expectedNumberOfSuperSportyBadges, numberOfSuperSportyBadges);
    }
}