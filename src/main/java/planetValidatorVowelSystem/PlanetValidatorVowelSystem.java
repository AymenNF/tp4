package planetValidatorVowelSystem;

import uspace.domain.cruise.itinerary.planet.PlanetValidator;

public class PlanetValidatorVowelSystem implements PlanetValidator {

    private static final String VOWELS = "AEIOUYaeiouy";
    public boolean isPlanetValid(String planetName) {
        if (planetName == null) {
            return false;
        }

        return !planetName.isEmpty()
               && containsOnlyLetters(planetName)
               && containsAtLeastOneVowel(planetName)
               && containsLessThanSixVowels(planetName)
               && !containsMoreThanTwoConsecutiveVowels(planetName);
    }

    private boolean containsOnlyLetters(String planetName) {
        return planetName.matches("[a-zA-Z]+");
    }

    private boolean containsAtLeastOneVowel(String planetName) {
        return planetName.matches(".*[" + VOWELS + "].*");
    }

    private boolean containsLessThanSixVowels(String planetName) {
        return planetName.replaceAll("[^" + VOWELS + "]", "").length() < 6;
    }

    private boolean containsMoreThanTwoConsecutiveVowels(String planetName) {
        return planetName.matches(".*[" + VOWELS + "]{3}.*");
    }
}
