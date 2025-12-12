package uspace.domain.cruise.crew.crewMember.employeeId;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeIdValidatorRegexTest {
    private EmployeeIdValidatorRegex employeeIdValidatorRegex;
    private static final String ANY_REGEX = "A*";

    @Test
    void givenEmployeeIdMatchingRegex_whenValidate_thenReturnTrue() {
        EmployeeId anyEmployeeIdMatchingRegex = new EmployeeId("AAA");
        employeeIdValidatorRegex = new EmployeeIdValidatorRegex(ANY_REGEX);

        boolean isValid = employeeIdValidatorRegex.isValid(anyEmployeeIdMatchingRegex);

        assertTrue(isValid);
    }

    @Test
    void givenEmployeeIdNotMatchingRegex_whenValidate_thenReturnFalse() {
        EmployeeId anyEmployeeIdNotMatchingRegex = new EmployeeId("BBB");
        employeeIdValidatorRegex = new EmployeeIdValidatorRegex(ANY_REGEX);

        boolean isValid = employeeIdValidatorRegex.isValid(anyEmployeeIdNotMatchingRegex);

        assertFalse(isValid);
    }

    @Test
    void givenValidatorFor3UppercaseLettersFollowedBy3Digits_whenValidateEmployeeIdWith3UppercaseLettersFollowedBy3Digits_thenReturnTrue() {
        EmployeeId anyEmployeeIdWith3UppercaseLetters3Digits = new EmployeeId("AAA111");
        employeeIdValidatorRegex = EmployeeIdValidatorRegex.for3UppercaseLettersFollowedBy3Digits();

        boolean isValid = employeeIdValidatorRegex.isValid(anyEmployeeIdWith3UppercaseLetters3Digits);

        assertTrue(isValid);
    }

    @Test
    void givenValidatorFor3UppercaseLettersFollowedBy3Digits_whenValidateEmployeeIdWith3UppercaseLettersNotFollowedBy3Digits_thenReturnFalse() {
        EmployeeId anyEmployeeIdWith3UppercaseLettersNot3Digits = new EmployeeId("AAA-11");
        employeeIdValidatorRegex = EmployeeIdValidatorRegex.for3UppercaseLettersFollowedBy3Digits();

        boolean isValid = employeeIdValidatorRegex.isValid(anyEmployeeIdWith3UppercaseLettersNot3Digits);

        assertFalse(isValid);
    }
}