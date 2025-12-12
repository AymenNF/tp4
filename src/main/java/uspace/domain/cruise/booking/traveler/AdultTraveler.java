package uspace.domain.cruise.booking.traveler;

import jakarta.persistence.Entity;
import uspace.domain.cruise.booking.traveler.badge.Badge;
import uspace.domain.cruise.sport.Sport;
import uspace.domain.cruise.zeroGravityExperience.ZeroGravityExperience;

import java.util.List;


@Entity
public class AdultTraveler extends Traveler{

    public AdultTraveler() {
    }

    public AdultTraveler(TravelerId id, TravelerName name, List<Badge> badges, StandardCostMultiplier standardCostMultiplier, List<Sport> sports) {
        super(id, name, TravelerCategory.ADULT, badges, standardCostMultiplier, sports);
    }

    @Override
    public void bookZeroGravityExperience(ZeroGravityExperience zeroGravityExperience, List<Traveler> travelersInBooking) {
        zeroGravityExperience.book(id);
        earnOneTimeBadge(Badge.ZERO_G);
    }

    @Override
    public boolean canAccompagnyChildForZeroGravityExperience(ZeroGravityExperience zeroGravityExperience) {
        return zeroGravityExperience.hasTravelerBooked(id);
    }

    @Override
    protected void earnSportBadge() {
        earnOneTimeBadge(Badge.SPORTY);
    }
}
