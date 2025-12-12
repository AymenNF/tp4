package uspace.domain.cruise.sport;

import uspace.domain.cruise.CruiseId;

public interface SportRegistry {
    Sport findSport(String sportName, CruiseId cruiseId);
}
