package uspace.domain.cruise.crew.crewMember.employeeId;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "employee_id_validator_type", discriminatorType = DiscriminatorType.STRING)
public abstract class EmployeeIdValidator {

    @Id
    protected EmployeeIdValidatorId id;

    protected EmployeeIdValidator() {
        id = new EmployeeIdValidatorId();
    }

    public abstract boolean isValid(EmployeeId employeeId);
}
