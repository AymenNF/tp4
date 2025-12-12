package uspace.infra.cabin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uspace.domain.cruise.cabin.CabinType;
import uspace.infra.cabin.dtos.CabinCategoryDto;
import uspace.infra.cabin.dtos.CabinFileDto;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CabinJsonReader {
    private final Logger logger = LoggerFactory.getLogger(CabinJsonReader.class);
    private final String cabinJsonPath;

    public CabinJsonReader(String cabinJsonPath) {
        this.cabinJsonPath = cabinJsonPath;
    }

    public Map<CabinType, List<String>> readCabins() {
        String cabinJsonStr = null;
        try (FileInputStream stream = new FileInputStream(cabinJsonPath)) {
            cabinJsonStr = new String(stream.readAllBytes());
        } catch (IOException e) {
            logger.error("Error while trying to read cabin json file " + cabinJsonPath, e);
            throw new RuntimeException(e);
        }

        try {
            CabinFileDto cabinFileDto = new ObjectMapper().readValue(cabinJsonStr, CabinFileDto.class);
            Map<CabinType, List<String>> cabinsByType = new HashMap<>();
            
            for (CabinCategoryDto category : cabinFileDto.cabins) {
                CabinType cabinType = CabinType.fromString(category.category);
                cabinsByType.put(cabinType, category.ids);
            }
            
            return cabinsByType;
        } catch (JsonProcessingException e) {
            logger.error("Error while trying to parse cabin json file " + cabinJsonPath, e);
            throw new RuntimeException(e);
        }
    }
}

