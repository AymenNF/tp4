package uspace.infra.sport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uspace.UspaceMain;
import uspace.domain.cruise.CruiseId;
import uspace.domain.cruise.sport.Sport;
import uspace.domain.cruise.sport.SportAudience;
import uspace.domain.cruise.sport.SportName;
import uspace.domain.cruise.sport.SportRegistry;
import uspace.infra.sport.dtos.SportDto;
import uspace.infra.sport.dtos.SportFileDto;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class SportJsonReader implements SportRegistry {
    private final Logger logger = LoggerFactory.getLogger(SportJsonReader.class);
    private final String sportJsonPath;

    public SportJsonReader(String sportJsonPath) {
        this.sportJsonPath = sportJsonPath;
    }

    public Sport findSport(String sportName, CruiseId cruiseId) {
        List<SportDto> sportJson = readSportJson();
        SportDto sportDto = sportJson.stream()
                .filter(sport -> sport.name.equals(sportName))
                .filter(sport -> sport.cruises.contains(cruiseId.toString()))
                .findFirst()
                .orElse(null);

        if (sportDto == null) {
            return null;
        }

        return new Sport(new SportName(sportDto.name), new SportAudience(sportDto.audience));
    }

    private List<SportDto> readSportJson() {
        String sportJsonStr = null;
        try {
            FileInputStream stream = new FileInputStream(sportJsonPath);
            sportJsonStr = new String(stream.readAllBytes());
        } catch (IOException e) {
            logger.error("Error while trying to read sport json file " + sportJsonPath, e);
            throw new RuntimeException(e);
        }

        try {
            SportFileDto sportFileDto = new ObjectMapper().readValue(sportJsonStr, SportFileDto.class);
            return sportFileDto.sports;
        } catch (JsonProcessingException e) {
            logger.error("Error while trying to parse sport json file " + sportJsonPath, e);
            throw new RuntimeException(e);
        }
    }
}
