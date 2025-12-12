package uspace.domain.cruise.dateTime;

import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Embeddable
public class CruiseDateTime {
    private LocalDateTime localDateTime;

    public CruiseDateTime() {

    }

    public CruiseDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public boolean isBefore(CruiseDateTime otherCruiseDateTime) {
        return localDateTime.isBefore(otherCruiseDateTime.localDateTime);
    }

    public boolean isAfter(CruiseDateTime otherCruiseDateTime) {
        return localDateTime.isAfter(otherCruiseDateTime.localDateTime);
    }

    public int calculateDaysBefore(CruiseDateTime laterCruiseDateTime) {
        return (int) localDateTime.until(laterCruiseDateTime.localDateTime, ChronoUnit.DAYS);
    }

    public CruiseDateTime plusHours(int durationInHours) {
        return new CruiseDateTime(localDateTime.plusHours(durationInHours));
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    @Override
    public String toString() {
        return localDateTime.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        CruiseDateTime that = (CruiseDateTime) obj;
        return localDateTime.equals(that.localDateTime);
    }
}
