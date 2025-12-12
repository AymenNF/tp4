package uspace.domain.cruise.cabin.attribution;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import uspace.domain.cruise.cabin.exceptions.InvalidCabinAttributionCriteriaException;

import static org.junit.jupiter.api.Assertions.*;

class CabinAttributionCriteriaTest {

    @Test
    void givenValidCriteriaBookingDateTime_whenFromString_thenReturnBookingDateTime() {
        CabinAttributionCriteria criteria = CabinAttributionCriteria.fromString("bookingDateTime");
        
        assertEquals(CabinAttributionCriteria.BOOKING_DATE_TIME, criteria);
    }

    @Test
    void givenValidCriteriaTravelers_whenFromString_thenReturnTravelers() {
        CabinAttributionCriteria criteria = CabinAttributionCriteria.fromString("travelers");
        
        assertEquals(CabinAttributionCriteria.TRAVELERS, criteria);
    }

    @Test
    void givenValidCriteriaCaseInsensitive_whenFromString_thenReturnCriteria() {
        CabinAttributionCriteria criteria = CabinAttributionCriteria.fromString("BOOKINGDATETIME");
        
        assertEquals(CabinAttributionCriteria.BOOKING_DATE_TIME, criteria);
    }

    @Test
    void givenInvalidCriteria_whenFromString_thenThrowInvalidCabinAttributionCriteriaException() {
        Executable action = () -> CabinAttributionCriteria.fromString("invalid");
        
        assertThrows(InvalidCabinAttributionCriteriaException.class, action);
    }
}

