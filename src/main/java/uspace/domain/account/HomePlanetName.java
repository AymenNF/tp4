package uspace.domain.account;

import jakarta.persistence.Embeddable;

@Embeddable
public class HomePlanetName {

    private String homePlanetName;

    public HomePlanetName(String homePlanetName) {
        this.homePlanetName = homePlanetName;
    }

    public HomePlanetName() {

    }

    @Override
    public String toString() {
        return homePlanetName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        HomePlanetName other = (HomePlanetName) obj;
        return this.homePlanetName.equals(other.homePlanetName);
    }

    @Override
    public int hashCode() {
        return homePlanetName.hashCode();
    }

    public boolean compareWithCaseInsensitive(String name) {
        return homePlanetName.equalsIgnoreCase(name);
    }
}
