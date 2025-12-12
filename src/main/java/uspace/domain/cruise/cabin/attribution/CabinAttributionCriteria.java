package uspace.domain.cruise.cabin.attribution;

import uspace.domain.cruise.cabin.exceptions.InvalidCabinAttributionCriteriaException;

public enum CabinAttributionCriteria {
    BOOKING_DATE_TIME("bookingDateTime"),
    TRAVELERS("travelers");

    private final String value;

    CabinAttributionCriteria(String value) {
        this.value = value;
    }

    public static CabinAttributionCriteria fromString(String criteria) {
        for (CabinAttributionCriteria criterion : CabinAttributionCriteria.values()) {
            if (criterion.value.equalsIgnoreCase(criteria)) {
                return criterion;
            }
        }
        throw new InvalidCabinAttributionCriteriaException(
            "Invalid cabin attribution criteria. It must be one of: bookingDateTime, travelers."
        );
    }
}

