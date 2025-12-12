package uspace.domain.cruise.booking.traveler;

import uspace.Configuration;
import uspace.domain.cruise.booking.traveler.badge.Badge;
import uspace.domain.cruise.booking.traveler.exceptions.InvalidTravelerCategoryException;
import uspace.domain.cruise.sport.Sport;

import java.util.ArrayList;
import java.util.List;

public class TravelerFactory {

    private final StandardCostMultiplier adultStandardCostMultiplier;
    private final StandardCostMultiplier childStandardCostMultiplier;
    private final StandardCostMultiplier seniorStandardCostMultiplier;

    public TravelerFactory() {
        this.adultStandardCostMultiplier = new StandardCostMultiplier(Configuration.ADULT_STANDARD_COST_MULTIPLIER);
        this.childStandardCostMultiplier = new StandardCostMultiplier(Configuration.CHILD_STANDARD_COST_MULTIPLIER);
        this.seniorStandardCostMultiplier = new StandardCostMultiplier(Configuration.SENIOR_STANDARD_COST_MULTIPLIER);
    }

    public Traveler create(String travelerNameStr, TravelerCategory travelerCategory) {
        TravelerId travelerId = new TravelerId();
        TravelerName travelerName = new TravelerName(travelerNameStr);
        List<Badge> noBadgesInitial = new ArrayList<>();
        List<Sport> noSportInitial = new ArrayList<>();

        return switch (travelerCategory) {
            case ADULT -> new AdultTraveler(travelerId, travelerName, noBadgesInitial, adultStandardCostMultiplier, noSportInitial);
            case CHILD -> new ChildTraveler(travelerId, travelerName, noBadgesInitial, childStandardCostMultiplier, noSportInitial);
            case SENIOR -> new SeniorTraveler(travelerId, travelerName, noBadgesInitial, seniorStandardCostMultiplier, noSportInitial);
            default -> throw new InvalidTravelerCategoryException();
        };
    }
}
