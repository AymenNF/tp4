package uspace.domain.cruise.booking.traveler;

import uspace.domain.cruise.booking.traveler.exceptions.InvalidTravelerCategoryException;

public enum TravelerCategory {
    ADULT("adult"),
    CHILD("child"),
    SENIOR("senior");

    private final String type;

    TravelerCategory(String type) {
        this.type = type;
    }

    public static TravelerCategory fromString(String type) {
        for (TravelerCategory travelerCategory : TravelerCategory.values()) {
            if (travelerCategory.type.equalsIgnoreCase(type)) {
                return travelerCategory;
            }
        }

        throw new InvalidTravelerCategoryException();
    }
}
