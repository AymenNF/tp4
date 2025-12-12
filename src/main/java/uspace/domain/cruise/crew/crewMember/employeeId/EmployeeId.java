package uspace.domain.cruise.crew.crewMember.employeeId;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class EmployeeId implements Serializable {
    private String id;

    public EmployeeId() {
    }

    public EmployeeId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        EmployeeId other = (EmployeeId) obj;
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
