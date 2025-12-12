package uspace.domain.cruise.itinerary.planet;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PlanetName implements Serializable {

    private String name;

    public PlanetName() {
    }

    public PlanetName(String name) {
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
        PlanetName other = (PlanetName) obj;
        return this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
