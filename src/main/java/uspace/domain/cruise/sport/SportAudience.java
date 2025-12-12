package uspace.domain.cruise.sport;

import jakarta.persistence.Embeddable;
import uspace.domain.cruise.booking.traveler.TravelerCategory;

import java.io.Serializable;

@Embeddable
public class SportAudience implements Serializable {

    private String audience;

    public SportAudience() {
    }

    public SportAudience(String name) {
        this.audience = name;
    }
    public static SportAudience ALL() {
        return new SportAudience("ALL");
    }

    public boolean isEqualToTravelerCategory(TravelerCategory category) {
        return audience.equals(category.name());
    }

    @Override
    public String toString() {
        return audience;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        SportAudience other = (SportAudience) obj;
        return this.audience.equals(other.audience);
    }

    @Override
    public int hashCode() {
        return audience.hashCode();
    }
}
