package uspace.domain.cruise.emergencyShuttle;

import uspace.domain.cruise.booking.traveler.Traveler;
import uspace.domain.cruise.booking.traveler.TravelerId;
import uspace.domain.cruise.booking.traveler.TravelerName;

public class EmergencyShuttleTraveler {
    private final TravelerId travelerId;
    private final TravelerName travelerName;
    
    public EmergencyShuttleTraveler(TravelerId travelerId, TravelerName travelerName) {
        this.travelerId = travelerId;
        this.travelerName = travelerName;
    }
    
    public static EmergencyShuttleTraveler fromTraveler(Traveler traveler) {
        return new EmergencyShuttleTraveler(traveler.getId(), traveler.getName());
    }
    
    public TravelerId getTravelerId() {
        return travelerId;
    }
    
    public TravelerName getTravelerName() {
        return travelerName;
    }
}

