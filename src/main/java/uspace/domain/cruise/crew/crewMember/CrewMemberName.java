package uspace.domain.cruise.crew.crewMember;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class CrewMemberName implements Serializable {
    private String name;

    public CrewMemberName() {

    }

    public CrewMemberName(String name) {
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
        CrewMemberName other = (CrewMemberName) obj;
        return this.name.equals(other.name);
    }
}
