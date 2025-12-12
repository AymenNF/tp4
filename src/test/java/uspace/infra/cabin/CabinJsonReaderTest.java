package uspace.infra.cabin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uspace.domain.cruise.cabin.CabinType;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CabinJsonReaderTest {
    private static final String CABINS_JSON = "src/test/resources/uspace/infra/cabin/cabins-test.json";
    private CabinJsonReader cabinJsonReader;

    @BeforeEach
    void createCabinJsonReader() {
        cabinJsonReader = new CabinJsonReader(CABINS_JSON);
    }

    @Test
    void whenReadCabins_thenReturnCabinsGroupedByType() {
        Map<CabinType, List<String>> cabinsByType = cabinJsonReader.readCabins();
        
        assertNotNull(cabinsByType);
        assertTrue(cabinsByType.containsKey(CabinType.STANDARD));
        assertTrue(cabinsByType.containsKey(CabinType.DELUXE));
        assertTrue(cabinsByType.containsKey(CabinType.SUITE));
        
        assertEquals(2, cabinsByType.get(CabinType.STANDARD).size());
        assertEquals(2, cabinsByType.get(CabinType.DELUXE).size());
        assertEquals(1, cabinsByType.get(CabinType.SUITE).size());
    }

    @Test
    void whenReadCabins_thenReturnCabinsInCorrectOrder() {
        Map<CabinType, List<String>> cabinsByType = cabinJsonReader.readCabins();
        
        List<String> standardCabins = cabinsByType.get(CabinType.STANDARD);
        assertEquals("A1", standardCabins.get(0));
        assertEquals("A2", standardCabins.get(1));
    }
}

