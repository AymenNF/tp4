package uspace.domain.cruise.sport;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import uspace.domain.cruise.booking.traveler.TravelerCategory;

@Entity
public class Sport {

    @Id
    private SportName name;
    private SportAudience audience;

    public Sport() {

    }

    public Sport(SportName name, SportAudience audience) {
        this.name = name;
        this.audience = audience;
    }

    public SportName getName() {
        return name;
    }

    public SportAudience getAudience() {
        return audience;
    }

    public boolean canBeBookedBy(TravelerCategory category) {
        return audience.equals(SportAudience.ALL()) || audience.isEqualToTravelerCategory(category);
    }
}
