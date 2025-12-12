package uspace.domain.cruise.itinerary.planet.excursion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uspace.domain.cruise.dateTime.CruiseDateTime;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ExcursionTest {

    private Excursion excursion;
    private static final ExcursionName ANY_EXCURSION_NAME = new ExcursionName("ANY_EXCURSION");
    private static final ExcursionName ANY_SAME_EXCURSION_NAME = new ExcursionName("ANY_EXCURSION");
    private static final ExcursionName ANY_OTHER_EXCURSION_NAME = new ExcursionName("ANY_OTHER_EXCURSION");
    private static final ExcursionDescription ANY_EXCURSION_DESCRIPTION = new ExcursionDescription("ANY_DESCRIPTION");
    private static final LocalDateTime ANY_EXCURSION_START_LOCAL_DATE_TIME = LocalDateTime.of(2084, 4, 8, 12, 30);
    private static final CruiseDateTime ANY_EXCURSION_START_DATE_TIME = new CruiseDateTime(ANY_EXCURSION_START_LOCAL_DATE_TIME);
    private static final int ANY_EXCURSION_DURATION_IN_HOURS = 2;
    private static final ExcursionDuration ANY_EXCURSION_DURATION = new ExcursionDuration(ANY_EXCURSION_DURATION_IN_HOURS);
    private static final LocalDateTime ANY_EXCURSION_END_DATE_TIME = LocalDateTime.of(2084, 4, 8, 14, 30);
    private static final ExcursionCapacity ANY_EXCURSION_CAPACITY = new ExcursionCapacity(11);

    @BeforeEach
    void createExcursion() {
        excursion = new Excursion(ANY_EXCURSION_NAME, ANY_EXCURSION_DESCRIPTION, ANY_EXCURSION_START_DATE_TIME, ANY_EXCURSION_DURATION, ANY_EXCURSION_CAPACITY);
    }

    @Test
    void givenSameName_whenHasSameNameThan_thenReturnTrue() {
        boolean hasSameName = excursion.hasSameNameThan(ANY_SAME_EXCURSION_NAME);

        assertTrue(hasSameName);
    }

    @Test
    void givenDifferentName_whenHasSameNameThan_thenReturnFalse() {
        boolean hasSameName = excursion.hasSameNameThan(ANY_OTHER_EXCURSION_NAME);

        assertFalse(hasSameName);
    }

    @Test
    void givenExcursionDuringTimeSlot_whenIsDuringTimeSlot_thenReturnTrue() {
        CruiseDateTime slotStartDateTimeBeforeExcursionStartDateTime = new CruiseDateTime(ANY_EXCURSION_START_LOCAL_DATE_TIME.minusDays(1));
        CruiseDateTime slotEndDateTimeAfterEndOfExcursion = new CruiseDateTime(ANY_EXCURSION_END_DATE_TIME.plusDays(2));

        boolean isDuringTimeSlot = excursion.isDuringTimeSlot(slotStartDateTimeBeforeExcursionStartDateTime, slotEndDateTimeAfterEndOfExcursion);

        assertTrue(isDuringTimeSlot);
    }

    @Test
    void givenExcursionStartsBeforeTimeSlot_whenIsDuringTimeSlot_thenReturnFalse() {
        CruiseDateTime slotStartDateTimeAfterExcursionStartDateTime = new CruiseDateTime(ANY_EXCURSION_START_LOCAL_DATE_TIME.plusDays(1));
        CruiseDateTime slotEndDateTimeAfterEndOfExcursion = new CruiseDateTime(ANY_EXCURSION_END_DATE_TIME.plusDays(2));

        boolean isDuringTimeSlot = excursion.isDuringTimeSlot(slotStartDateTimeAfterExcursionStartDateTime, slotEndDateTimeAfterEndOfExcursion);

        assertFalse(isDuringTimeSlot);
    }

    @Test
    void givenExcursionEndsAfterTimeSlot_whenIsDuringTimeSlot_thenReturnFalse() {
        CruiseDateTime slotStartDateTimeBeforeExcursionStartDateTime = new CruiseDateTime(ANY_EXCURSION_START_LOCAL_DATE_TIME.minusDays(1));
        CruiseDateTime slotEndDateTimeBeforeEndOfExcursion = new CruiseDateTime(ANY_EXCURSION_END_DATE_TIME.minusHours(1));

        boolean isDuringTimeSlot = excursion.isDuringTimeSlot(slotStartDateTimeBeforeExcursionStartDateTime, slotEndDateTimeBeforeEndOfExcursion);

        assertFalse(isDuringTimeSlot);
    }

    @Test
    void givenExcursionDurationIsExactlyTimeSlot_whenIsDuringTimeSlot_thenReturnTrue() {
        CruiseDateTime slotStartDateTimeAtExcursionStartDateTime = new CruiseDateTime(ANY_EXCURSION_START_LOCAL_DATE_TIME);
        CruiseDateTime slotEndDateTimeAtEndOfExcursion = new CruiseDateTime(ANY_EXCURSION_END_DATE_TIME);

        boolean isDuringTimeSlot = excursion.isDuringTimeSlot(slotStartDateTimeAtExcursionStartDateTime, slotEndDateTimeAtEndOfExcursion);

        assertTrue(isDuringTimeSlot);
    }
}