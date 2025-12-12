package uspace.domain.cruise.crew.crewMember.employeeId;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class EmployeeIdValidatorId implements Serializable {
    private String id;

    public EmployeeIdValidatorId() {
        id = UUID.randomUUID().toString();
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
        EmployeeIdValidatorId other = (EmployeeIdValidatorId) obj;
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
