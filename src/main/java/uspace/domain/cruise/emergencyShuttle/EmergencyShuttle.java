package uspace.domain.cruise.emergencyShuttle;

import java.util.ArrayList;
import java.util.List;

public class EmergencyShuttle {
    private final EmergencyShuttleType type;
    private final List<EmergencyShuttleTraveler> travelers;
    private final List<EmergencyShuttleCrewMember> crewMembers;
    
    public EmergencyShuttle(EmergencyShuttleType type) {
        this.type = type;
        this.travelers = new ArrayList<>();
        this.crewMembers = new ArrayList<>();
    }
    
    public EmergencyShuttleType getType() {
        return type;
    }
    
    public List<EmergencyShuttleTraveler> getTravelers() {
        return List.copyOf(travelers);
    }
    
    public List<EmergencyShuttleCrewMember> getCrewMembers() {
        return List.copyOf(crewMembers);
    }
    
    public int getCurrentOccupancy() {
        return travelers.size() + crewMembers.size();
    }
    
    public boolean hasSpace(int capacity) {
        return getCurrentOccupancy() < capacity;
    }
    
    public boolean addTraveler(EmergencyShuttleTraveler traveler, int capacity) {
        if (!hasSpace(capacity)) {
            return false;
        }
        travelers.add(traveler);
        return true;
    }
    
    public boolean addCrewMember(EmergencyShuttleCrewMember crewMember, int capacity) {
        if (!hasSpace(capacity)) {
            return false;
        }
        crewMembers.add(crewMember);
        return true;
    }
    
    public boolean isEmpty() {
        return travelers.isEmpty() && crewMembers.isEmpty();
    }
}

