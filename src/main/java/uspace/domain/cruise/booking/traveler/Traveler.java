package uspace.domain.cruise.booking.traveler;

import jakarta.persistence.*;
import uspace.domain.cruise.booking.traveler.badge.Badge;
import uspace.domain.cruise.money.Money;
import uspace.domain.cruise.sport.Sport;
import uspace.domain.cruise.sport.exceptions.SportAlreadyBookedException;
import uspace.domain.cruise.sport.exceptions.SportNotBookableException;
import uspace.domain.cruise.zeroGravityExperience.ZeroGravityExperience;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Traveler {

    @Id
    protected TravelerId id;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    protected List<Badge> badges;

    private TravelerName name;

    @Enumerated(EnumType.STRING)
    private TravelerCategory category;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Sport> sports;

    private StandardCostMultiplier standardCostMultiplier;

    protected Traveler() {

    }

    protected Traveler(TravelerId id, TravelerName name, TravelerCategory category, List<Badge> badges, StandardCostMultiplier standardCostMultiplier, List<Sport> sports)
    {
        this.id = id;
        this.name = name;
        this.category = category;
        this.badges = badges;
        this.standardCostMultiplier = standardCostMultiplier;
        this.sports = sports;
    }

    public TravelerId getId() {
        return id;
    }

    public TravelerName getName() {
        return name;
    }

    public TravelerCategory getCategory() {
        return category;
    }

    public List<Badge> getBadges() {
        return badges;
    }

    public abstract void bookZeroGravityExperience(ZeroGravityExperience zeroGravityExperience,
                                                   List<Traveler> travelersInBooking);

    public abstract boolean canAccompagnyChildForZeroGravityExperience(ZeroGravityExperience zeroGravityExperience);

    public Money calculateStandardBookingCost(Money cabinBaseCostPerPerson) {
        return standardCostMultiplier.applyToMoney(cabinBaseCostPerPerson);
    }

    public void bookSport(Sport sport) {
        if (isSportBooked(sport))
        {
            throw new SportAlreadyBookedException();
        }

        if (!sport.canBeBookedBy(category))
        {
            throw new SportNotBookableException();
        }

        sports.add(sport);
        earnSportBadge();
    }

    protected void earnOneTimeBadge(Badge badge) {
        if (!badges.contains(badge))
        {
            badges.add(badge);
        }
    }

    protected abstract void earnSportBadge();

    private boolean isSportBooked(Sport sport) {
        return sports.stream().anyMatch(s -> s.getName().equals(sport.getName()));
    }
}
