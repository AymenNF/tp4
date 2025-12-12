package planetValidatorVowelSystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlanetValidatorVowelSystemTest {

    private PlanetValidatorVowelSystem planetValidatorVowelSystem;

    @BeforeEach
    public void createPlanetValidatorVowelSystem() {
        planetValidatorVowelSystem = new PlanetValidatorVowelSystem();
    }

    @Test
    public void givenPlanetNameNull_whenIsPlanetValid_thenReturnFalse() {
        String nameNull = null;

        boolean isPlanetValid = planetValidatorVowelSystem.isPlanetValid(nameNull);

        assertFalse(isPlanetValid);
    }

    @Test
    public void givenPlanetNameEmpty_whenIsPlanetValid_thenReturnFalse() {
        String nameEmpty = "";

        boolean isPlanetValid = planetValidatorVowelSystem.isPlanetValid(nameEmpty);

        assertFalse(isPlanetValid);
    }

    @Test
    public void givenPlanetNameNotOnlyWithLetters_whenIsPlanetValid_thenReturnFalse() {
        String nameNotOnlyWithLetters = "Jupiter1";

        boolean isPlanetValid = planetValidatorVowelSystem.isPlanetValid(nameNotOnlyWithLetters);

        assertFalse(isPlanetValid);
    }

    @Test
    public void givenPlanetNameWithoutVowel_whenIsPlanetValid_thenReturnFalse() {
        String nameWithoutVowel = "bcd";

        boolean isPlanetValid = planetValidatorVowelSystem.isPlanetValid(nameWithoutVowel);

        assertFalse(isPlanetValid);
    }

    @Test
    public void givenPlanetNameWithSixOrMoreVowelsNotConsecutive_whenIsPlanetValid_thenReturnFalse() {
        String nameWithSixOrMoreVowelsNotConsecutive = "abEbibObubY";

        boolean isPlanetValid = planetValidatorVowelSystem.isPlanetValid(nameWithSixOrMoreVowelsNotConsecutive);

        assertFalse(isPlanetValid);
    }

    @Test
    public void givenPlanetNameWithMoreThanTwoConsecutiveVowelsAndLessThan6Vowels_whenIsPlanetValid_thenReturnFalse() {
        String nameWithMoreThanTwoConsecutiveVowelsAndLessThan6Vowels = "EaY";

        boolean isPlanetValid = planetValidatorVowelSystem.isPlanetValid(
                nameWithMoreThanTwoConsecutiveVowelsAndLessThan6Vowels);

        assertFalse(isPlanetValid);
    }

    @Test
    public void givenPlanetNameWithLettersOnlyAndAtLeastOneVowelAndLessThanSixVowelsAndNoMoreThanTwoConsecutiveVowels_whenIsPlanetValid_thenReturnTrue() {
        String nameWithLettersOnlyAndAtLeastOneVowelAndLessThanSixVowelsAndNoMoreThanTwoConsecutiveVowels = "Jupiter";

        boolean isPlanetValid = planetValidatorVowelSystem.isPlanetValid(
                nameWithLettersOnlyAndAtLeastOneVowelAndLessThanSixVowelsAndNoMoreThanTwoConsecutiveVowels);

        assertTrue(isPlanetValid);
    }
}