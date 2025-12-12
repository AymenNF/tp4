package uspace.domain.cruise.itinerary.planet.excursion;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ExcursionName implements Serializable {

    private String name;

    public ExcursionName() {
    }

    public ExcursionName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        ExcursionName other = (ExcursionName) obj;
        return this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}