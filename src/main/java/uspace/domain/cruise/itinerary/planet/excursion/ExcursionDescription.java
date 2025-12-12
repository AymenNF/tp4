package uspace.domain.cruise.itinerary.planet.excursion;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ExcursionDescription implements Serializable {
    private String description;

    public ExcursionDescription() {
    }

    public ExcursionDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        ExcursionDescription other = (ExcursionDescription) obj;
        return this.description.equals(other.description);
    }
}
