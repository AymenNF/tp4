package uspace.domain.cruise.crew.crewMember.employeeId;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("regex")
public class EmployeeIdValidatorRegex extends EmployeeIdValidator{

    private static final String REGEX_FOR_3_LETTERS_3_DIGITS = "^[A-Z]{3}[0-9]{3}$";
    private String regex;

    public EmployeeIdValidatorRegex() {
    }

    public EmployeeIdValidatorRegex(String regex) {
        this.regex = regex;
    }

    public static EmployeeIdValidatorRegex for3UppercaseLettersFollowedBy3Digits() {
        return new EmployeeIdValidatorRegex(REGEX_FOR_3_LETTERS_3_DIGITS);
    }

    @Override
    public boolean isValid(EmployeeId employeeId) {
        return employeeId != null && employeeId.toString().matches(regex);
    }
}
