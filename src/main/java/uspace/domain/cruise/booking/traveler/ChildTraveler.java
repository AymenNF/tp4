package uspace.domain.cruise.booking.traveler;

import jakarta.persistence.Entity;
import uspace.domain.cruise.booking.traveler.badge.Badge;
import uspace.domain.cruise.booking.traveler.exceptions.ZeroGravityExperienceChildCriteriaException;
import uspace.domain.cruise.sport.Sport;
import uspace.domain.cruise.zeroGravityExperience.ZeroGravityExperience;

import java.util.List;


@Entity
public class ChildTraveler extends Traveler {

    public ChildTraveler() {
    }

    public ChildTraveler(TravelerId id, TravelerName name, List<Badge> badges, StandardCostMultiplier standardCostMultiplier, List<Sport> sports) {
        super(id, name, TravelerCategory.CHILD, badges, standardCostMultiplier, sports);
    }

    @Override
    public void bookZeroGravityExperience(ZeroGravityExperience zeroGravityExperience, List<Traveler> travelersInBooking) {
        for (Traveler traveler : travelersInBooking) {
            if (traveler.canAccompagnyChildForZeroGravityExperience(zeroGravityExperience)) {
                zeroGravityExperience.book(id);
                earnOneTimeBadge(Badge.MINI_ZERO_G);
                return;
            }
        }

        throw new ZeroGravityExperienceChildCriteriaException();
    }

    @Override
    public boolean canAccompagnyChildForZeroGravityExperience(ZeroGravityExperience zeroGravityExperience) {
        return false;
    }

    @Override
    protected void earnSportBadge() {
        earnOneTimeBadge(Badge.MINI_SPORTY);
    }
}
