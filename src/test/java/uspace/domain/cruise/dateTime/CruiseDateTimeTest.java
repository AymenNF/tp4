package uspace.domain.cruise.dateTime;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CruiseDateTimeTest {

    private static final LocalDateTime ANY_LOCAL_DATE_TIME = LocalDateTime.now();
    private static final CruiseDateTime ANY_CRUISE_DATE_TIME = new CruiseDateTime(ANY_LOCAL_DATE_TIME);
    private static final int ANY_NUMBER_OF_DAYS = 2;

    @Test
    void givenLaterCruiseDateTime_whenCalculateDaysBefore_thenReturnDaysBeforeGivenDateTime() {
        CruiseDateTime laterCruiseDateTime = new CruiseDateTime(ANY_LOCAL_DATE_TIME.plusDays(ANY_NUMBER_OF_DAYS));

        int daysBeforeDateTime = ANY_CRUISE_DATE_TIME.calculateDaysBefore(laterCruiseDateTime);

        assertEquals(ANY_NUMBER_OF_DAYS, daysBeforeDateTime);
    }

    @Test
    void givenSameCruiseDateTime_whenCalculateDaysBefore_thenReturnZero() {
        int daysBeforeDateTime = ANY_CRUISE_DATE_TIME.calculateDaysBefore(ANY_CRUISE_DATE_TIME);

        assertEquals(0, daysBeforeDateTime);
    }

    @Test
    void givenEarlierCruiseDateTime_whenCalculateDaysBefore_thenReturnNegativeDaysBeforeGivenDateTime() {
        CruiseDateTime earlierCruiseDateTime = new CruiseDateTime(ANY_LOCAL_DATE_TIME.minusDays(ANY_NUMBER_OF_DAYS));

        int daysBeforeDateTime = ANY_CRUISE_DATE_TIME.calculateDaysBefore(earlierCruiseDateTime);

        assertEquals(-ANY_NUMBER_OF_DAYS, daysBeforeDateTime);
    }
}