package uspace.domain.cruise.booking.traveler;

import jakarta.persistence.Entity;
import uspace.domain.cruise.booking.traveler.badge.Badge;
import uspace.domain.cruise.sport.Sport;
import uspace.domain.cruise.zeroGravityExperience.ZeroGravityExperience;

import java.util.List;
@Entity
public class SeniorTraveler extends Traveler {

    public SeniorTraveler() {
    }

    public SeniorTraveler(TravelerId id, TravelerName name, List<Badge> badges, StandardCostMultiplier standardCostMultiplier, List<Sport> sports) {
        super(id, name, TravelerCategory.SENIOR, badges, standardCostMultiplier, sports);
    }

    @Override
    public void bookZeroGravityExperience(ZeroGravityExperience zeroGravityExperience, List<Traveler> travelersInBooking) {
        zeroGravityExperience.book(id);
        earnOneTimeBadge(Badge.ZERO_G);
        earnOneTimeBadge(Badge.STILL_GOT_IT);
    }

    @Override
    public boolean canAccompagnyChildForZeroGravityExperience(ZeroGravityExperience zeroGravityExperience) {
        return zeroGravityExperience.hasTravelerBooked(id);
    }

    @Override
    protected void earnSportBadge() {
        if (badges.contains(Badge.SPORTY)) {
            earnOneTimeBadge(Badge.SUPER_SPORTY);
        }
        earnOneTimeBadge(Badge.SPORTY);
    }
}
