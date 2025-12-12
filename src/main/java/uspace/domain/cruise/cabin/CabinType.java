package uspace.domain.cruise.cabin;

import uspace.domain.cruise.cabin.exceptions.InvalidCabinTypeException;

public enum CabinType {
    STANDARD("standard"),
    DELUXE("deluxe"),
    SUITE("suite");

    private final String type;

    CabinType(String type) {
        this.type = type;
    }

    public static CabinType fromString(String type) {
        for (CabinType cabinType : CabinType.values()) {
            if (cabinType.type.equalsIgnoreCase(type)) {
                return cabinType;
            }
        }

        throw new InvalidCabinTypeException();
    }
}
