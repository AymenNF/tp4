package uspace.domain.cruise.itinerary.planet.excursion;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import uspace.domain.cruise.dateTime.CruiseDateTime;

@Entity
public class Excursion {
    @Id
    private ExcursionName name;
    private ExcursionDescription description;
    private CruiseDateTime startDateTime;
    private ExcursionDuration duration;
    private ExcursionCapacity capacity;

    public Excursion() {
    }

    public Excursion(ExcursionName name, ExcursionDescription description, CruiseDateTime startDateTime, ExcursionDuration duration, ExcursionCapacity capacity) {
        this.name = name;
        this.description = description;
        this.startDateTime = startDateTime;
        this.duration = duration;
        this.capacity = capacity;
    }

    public ExcursionName getName() {
        return name;
    }

    public ExcursionDescription getDescription() {
        return description;
    }

    public CruiseDateTime getStartDateTime() {
        return startDateTime;
    }

    public int getDurationInHours() {
        return duration.getDurationInHours();
    }

    public ExcursionCapacity getCapacity() {
        return capacity;
    }

    public boolean isDuringTimeSlot(CruiseDateTime slotStartDateTime, CruiseDateTime slotEndDateTime) {
        return beginsAtOrAfterStart(slotStartDateTime) && endsAtOrBeforeEnd(slotEndDateTime);
    }

    public boolean hasSameNameThan(ExcursionName otherExcursionName) {
        return name.equals(otherExcursionName);
    }

    private boolean beginsAtOrAfterStart(CruiseDateTime dateTime) {
        return !startDateTime.isBefore(dateTime);
    }

    private boolean endsAtOrBeforeEnd(CruiseDateTime dateTime) {
        return !startDateTime.plusHours(duration.getDurationInHours()).isAfter(dateTime);
    }
}
