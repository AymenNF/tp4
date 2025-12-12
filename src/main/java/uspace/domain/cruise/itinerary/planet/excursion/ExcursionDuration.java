package uspace.domain.cruise.itinerary.planet.excursion;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ExcursionDuration implements Serializable {

    private int durationInHours;

    public ExcursionDuration() {
    }

    public ExcursionDuration(int durationInHours) {
        this.durationInHours = durationInHours;
    }

    public int getDurationInHours() {
        return durationInHours;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        ExcursionDuration other = (ExcursionDuration) obj;
        return this.durationInHours == other.durationInHours;
    }

    @Override
    public int hashCode() {
        return durationInHours;
    }
}
