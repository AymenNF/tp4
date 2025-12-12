package uspace.domain.cruise.sport;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class SportName implements Serializable {

    private String name;

    public SportName() {
    }

    public SportName(String name) {
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
        SportName other = (SportName) obj;
        return this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
