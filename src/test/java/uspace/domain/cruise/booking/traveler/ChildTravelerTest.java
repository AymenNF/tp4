package uspace.domain.cruise.booking.traveler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uspace.domain.cruise.booking.traveler.badge.Badge;
import uspace.domain.cruise.booking.traveler.exceptions.ZeroGravityExperienceChildCriteriaException;
import uspace.domain.cruise.money.Money;
import uspace.domain.cruise.sport.Sport;
import uspace.domain.cruise.sport.SportAudience;
import uspace.domain.cruise.sport.SportName;
import uspace.domain.cruise.sport.exceptions.SportNotBookableException;
import uspace.domain.cruise.zeroGravityExperience.ZeroGravityExperience;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChildTravelerTest {
    private static final TravelerId ANY_ID = new TravelerId();
    private static final TravelerId ANY_CHILD_ID = new TravelerId();
    private static final TravelerId ANY_SENIOR_ID = new TravelerId();
    private static final TravelerName ANY_NAME = new TravelerName("Bob");
    private static final StandardCostMultiplier ANY_STANDARD_COST_MULTIPLIER = new StandardCostMultiplier(2);
    private static final List<Badge> ANY_BADGES = new ArrayList<>();
    private static final List<Badge> NO_BADGE = new ArrayList<>();
    private static final List<Sport> NO_SPORT = new ArrayList<>();
    private static final List<Badge> BADGES_WITH_MINI_ZERO_G = List.of(Badge.MINI_ZERO_G);
    private static final AdultTraveler ANY_CHILD_TRAVELER = new AdultTraveler(ANY_CHILD_ID, ANY_NAME, ANY_BADGES, ANY_STANDARD_COST_MULTIPLIER, NO_SPORT);
    private static final SeniorTraveler ANY_SENIOR_TRAVELER = new SeniorTraveler(ANY_SENIOR_ID, ANY_NAME, ANY_BADGES, ANY_STANDARD_COST_MULTIPLIER, NO_SPORT);
    private static final List<Traveler> ANY_CHILDS_AND_SENIORS_IN_BOOKING = List.of(ANY_CHILD_TRAVELER, ANY_SENIOR_TRAVELER);
    private static final SportName ANY_SPORT_NAME = new SportName("anysport");
    private static final SportAudience ANY_SPORT_AUDIENCE = SportAudience.ALL();
    private static final Sport ANY_SPORT = new Sport(ANY_SPORT_NAME, ANY_SPORT_AUDIENCE);
    private List<Sport> anySports = new ArrayList<>();
    private ChildTraveler childTraveler;
    @Mock
    private ZeroGravityExperience zeroGravityExperience;
    @Mock
    private Sport sportMock;

    @BeforeEach
    void createChildTraveler() {
        anySports = new ArrayList<>();
        childTraveler = new ChildTraveler(ANY_ID, ANY_NAME, ANY_BADGES, ANY_STANDARD_COST_MULTIPLIER, anySports);
    }

    @Test
    void whenCreate_thenHasTravelerCategoryChild() {
        TravelerCategory expectedCategory = TravelerCategory.CHILD;

        TravelerCategory actualCategory = childTraveler.getCategory();

        assertEquals(expectedCategory, actualCategory);
    }

    @Test
    void givenNoAdultAndNoSeniorInBooking_whenBookZeroGravityExperience_thenThrowZeroGravityExperienceChildCriteriaException() {
        List<Traveler> noAdultAndNoSenior = List.of();

        Executable bookZeroGravityExperience = () -> childTraveler.bookZeroGravityExperience(zeroGravityExperience, noAdultAndNoSenior);

        assertThrows(ZeroGravityExperienceChildCriteriaException.class, bookZeroGravityExperience);
    }

    @Test
    void givenNoAdultsAndSeniorsInBookingWhoBookedZeroGravityExperience_whenBookZeroGravityExperience_thenThrowZeroGravityExperienceChildCriteriaException() {
        givenTravelerWhoDidNotBookZeroGravityExperience(ANY_CHILD_ID);
        givenTravelerWhoDidNotBookZeroGravityExperience(ANY_SENIOR_ID);

        Executable bookZeroGravityExperience = () -> childTraveler.bookZeroGravityExperience(zeroGravityExperience, ANY_CHILDS_AND_SENIORS_IN_BOOKING);

        assertThrows(ZeroGravityExperienceChildCriteriaException.class, bookZeroGravityExperience);
    }

    @Test
    void givenAdultInBookingWhoBookedZeroGravityExperience_whenBookZeroGravityExperience_thenZeroGravityExperienceIsBooked() {
        givenAdultWhoBookedZeroGravityExperience();

        childTraveler.bookZeroGravityExperience(zeroGravityExperience, ANY_CHILDS_AND_SENIORS_IN_BOOKING);

        verify(zeroGravityExperience).book(ANY_ID);
    }

    @Test
    void givenAdultInBookingWhoBookedZeroGravityExperienceAndNoMiniZeroGBadge_whenBookZeroGravityExperience_thenEarnMiniZeroGBadge() {
        givenAdultWhoBookedZeroGravityExperience();
        ChildTraveler childTravelerWithoutBadge = new ChildTraveler(ANY_ID, ANY_NAME, NO_BADGE, ANY_STANDARD_COST_MULTIPLIER, NO_SPORT);

        childTravelerWithoutBadge.bookZeroGravityExperience(zeroGravityExperience, ANY_CHILDS_AND_SENIORS_IN_BOOKING);

        assertTrue(childTravelerWithoutBadge.getBadges().contains(Badge.MINI_ZERO_G));
    }

    @Test
    void givenAdultInBookingWhoBookedZeroGravityExperienceAndMiniZeroGBadge_whenBookZeroGravityExperience_thenDoNotEarnSecondMiniZeroGBadge() {
        givenAdultWhoBookedZeroGravityExperience();
        ChildTraveler childTravelerWithZeroGBadge = new ChildTraveler(ANY_ID, ANY_NAME, BADGES_WITH_MINI_ZERO_G, ANY_STANDARD_COST_MULTIPLIER, NO_SPORT);
        int expectedNumberOfMiniZeroGBadges = 1;

        childTravelerWithZeroGBadge.bookZeroGravityExperience(zeroGravityExperience, ANY_CHILDS_AND_SENIORS_IN_BOOKING);

        int numberOfMiniZeroGBadges = (int) childTravelerWithZeroGBadge.getBadges().stream().filter(Badge.MINI_ZERO_G::equals).count();
        assertEquals(expectedNumberOfMiniZeroGBadges, numberOfMiniZeroGBadges);
    }

    @Test
    void givenSeniorInBookingWhoBookedZeroGravityExperience_whenBookZeroGravityExperience_thenZeroGravityExperienceIsBooked() {
        givenSeniorWhoBookedZeroGravityExperience();

        childTraveler.bookZeroGravityExperience(zeroGravityExperience, ANY_CHILDS_AND_SENIORS_IN_BOOKING);

        verify(zeroGravityExperience).book(ANY_ID);
    }

    @Test
    void givenSeniorInBookingWhoBookedZeroGravityExperienceAndNoMiniZeroGBadge_whenBookZeroGravityExperience_thenEarnMiniZeroGBadge() {
        givenSeniorWhoBookedZeroGravityExperience();
        ChildTraveler childTravelerWithoutBadge = new ChildTraveler(ANY_ID, ANY_NAME, NO_BADGE, ANY_STANDARD_COST_MULTIPLIER, NO_SPORT);

        childTravelerWithoutBadge.bookZeroGravityExperience(zeroGravityExperience, ANY_CHILDS_AND_SENIORS_IN_BOOKING);

        assertTrue(childTravelerWithoutBadge.getBadges().contains(Badge.MINI_ZERO_G));
    }

    @Test
    void givenSeniorInBookingWhoBookedZeroGravityExperienceAndMiniZeroGBadge_whenBookZeroGravityExperience_thenDoNotEarnSecondMiniZeroGBadge() {
        givenSeniorWhoBookedZeroGravityExperience();
        ChildTraveler childTravelerWithZeroGBadge = new ChildTraveler(ANY_ID, ANY_NAME, BADGES_WITH_MINI_ZERO_G, ANY_STANDARD_COST_MULTIPLIER, NO_SPORT);
        int expectedNumberOfMiniZeroGBadges = 1;

        childTravelerWithZeroGBadge.bookZeroGravityExperience(zeroGravityExperience, ANY_CHILDS_AND_SENIORS_IN_BOOKING);

        int numberOfMiniZeroGBadges = (int) childTravelerWithZeroGBadge.getBadges().stream().filter(Badge.MINI_ZERO_G::equals).count();
        assertEquals(expectedNumberOfMiniZeroGBadges, numberOfMiniZeroGBadges);
    }

    @Test
    void whenCalculateStandardBookingCost_thenReturnCabinBaseCostPerPersonMultiplyByStandardCostMultiplier() {
        Money cabinBaseCostPerPerson = new Money(100);
        Money bookingCostExpected = new Money(200);

        Money bookingCost = childTraveler.calculateStandardBookingCost(cabinBaseCostPerPerson);

        assertEquals(bookingCostExpected, bookingCost);
    }

    @Test
    void givenSportNotBookableForAdult_whenBookSport_thenThrowSportNotBookableException() {
        when(sportMock.canBeBookedBy(TravelerCategory.CHILD)).thenReturn(false);

        Executable bookSport = () -> childTraveler.bookSport(sportMock);

        assertThrows(SportNotBookableException.class, bookSport);
    }

    @Test
    void givenSportBookableForAdult_whenBookSport_thenEarnMiniSportyBadge() {
        when(sportMock.canBeBookedBy(TravelerCategory.CHILD)).thenReturn(true);

        childTraveler.bookSport(sportMock);

        assertTrue(childTraveler.getBadges().contains(Badge.MINI_SPORTY));
    }

    @Test
    void givenSportBookableForChild_whenBookAnotherSport_thenDoNotEarnSecondMiniSportyBadge() {
        when(sportMock.canBeBookedBy(TravelerCategory.CHILD)).thenReturn(true);
        List<Badge> badgesWithMiniSporty = List.of(Badge.MINI_SPORTY);
        ChildTraveler childTravelerWithMiniSportyBadge = new ChildTraveler(ANY_ID, ANY_NAME, badgesWithMiniSporty, ANY_STANDARD_COST_MULTIPLIER,
                                                                       anySports);
        int expectedNumberOfZSportyBadges = 1;

        childTravelerWithMiniSportyBadge.bookSport(sportMock);

        int numberOfZeroGBadges = (int) childTravelerWithMiniSportyBadge.getBadges().stream().filter(Badge.MINI_SPORTY::equals).count();
        assertEquals(expectedNumberOfZSportyBadges, numberOfZeroGBadges);
    }

    private void givenTravelerWhoDidNotBookZeroGravityExperience(TravelerId travelerId) {
        when(zeroGravityExperience.hasTravelerBooked(travelerId)).thenReturn(false);
    }

    private void givenAdultWhoBookedZeroGravityExperience() {
        when(zeroGravityExperience.hasTravelerBooked(ANY_CHILD_ID)).thenReturn(true);
    }

    private void givenSeniorWhoBookedZeroGravityExperience() {
        when(zeroGravityExperience.hasTravelerBooked(ANY_SENIOR_ID)).thenReturn(true);
        when(zeroGravityExperience.hasTravelerBooked(ANY_CHILD_ID)).thenReturn(false);
    }
}