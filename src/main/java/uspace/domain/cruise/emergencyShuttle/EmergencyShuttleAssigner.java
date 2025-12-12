package uspace.domain.cruise.emergencyShuttle;

import uspace.domain.cruise.Cruise;
import uspace.domain.cruise.CruiseId;
import uspace.domain.cruise.booking.Booking;
import uspace.domain.cruise.booking.traveler.Traveler;
import uspace.domain.cruise.crew.crewMember.CrewMember;

import java.util.ArrayList;
import java.util.List;

public class EmergencyShuttleAssigner {
    
    public EmergencyShuttleManifest assignShuttles(Cruise cruise) {
        CruiseId cruiseId = cruise.getId();
        int rescueShipCount = EmergencyShuttleConfiguration.getRescueShipCountForCruise(cruiseId);
        int rescueShipCapacity = EmergencyShuttleConfiguration.getRescueShipCapacity();
        int standardShuttleCapacity = EmergencyShuttleConfiguration.getStandardShuttleCapacity();
        
        List<EmergencyShuttle> shuttles = new ArrayList<>();
        
        // Collect all travelers and crew members
        List<Traveler> allTravelers = collectAllTravelers(cruise);
        List<CrewMember> allCrewMembers = cruise.getCrewMembers();
        
        // Create assignment state to track what's been assigned
        AssignmentState state = new AssignmentState();
        
        // First, assign travelers to RESCUE_SHIP (priority)
        // Use RESCUE_SHIPs in priority, filling each completely before moving to next
        assignTravelersToShuttleType(
                allTravelers,
                EmergencyShuttleType.RESCUE_SHIP,
                rescueShipCapacity,
                rescueShipCount,
                shuttles,
                state
        );
        
        // Then, assign crew members to RESCUE_SHIP (continue filling the last RESCUE_SHIP)
        assignCrewMembersToShuttleType(
                allCrewMembers,
                EmergencyShuttleType.RESCUE_SHIP,
                rescueShipCapacity,
                rescueShipCount,
                shuttles,
                state
        );
        
        // Assign remaining travelers to STANDARD_SHUTTLE
        assignTravelersToShuttleType(
                allTravelers,
                EmergencyShuttleType.STANDARD_SHUTTLE,
                standardShuttleCapacity,
                Integer.MAX_VALUE, // Unlimited
                shuttles,
                state
        );
        
        // And remaining crew members to STANDARD_SHUTTLE
        assignCrewMembersToShuttleType(
                allCrewMembers,
                EmergencyShuttleType.STANDARD_SHUTTLE,
                standardShuttleCapacity,
                Integer.MAX_VALUE, // Unlimited
                shuttles,
                state
        );
        
        return new EmergencyShuttleManifest(shuttles);
    }
    
    private List<Traveler> collectAllTravelers(Cruise cruise) {
        List<Traveler> allTravelers = new ArrayList<>();
        for (Booking booking : cruise.getCruiseBookings().getAllBookings().values()) {
            allTravelers.addAll(booking.getTravelers());
        }
        return allTravelers;
    }
    
    private void assignTravelersToShuttleType(
            List<Traveler> allTravelers,
            EmergencyShuttleType type,
            int capacity,
            int maxShuttles,
            List<EmergencyShuttle> shuttles,
            AssignmentState state) {
        
        // Assign travelers (in booking order)
        while (state.travelerIndex < allTravelers.size()) {
            Traveler traveler = allTravelers.get(state.travelerIndex);
            
            // For RESCUE_SHIP: only create a new one if we can fill it completely
            // Check if we have enough remaining travelers to fill a new shuttle
            if (type == EmergencyShuttleType.RESCUE_SHIP) {
                int remainingTravelers = allTravelers.size() - state.travelerIndex;
                int existingRescueShipCount = (int) shuttles.stream()
                        .filter(s -> s.getType() == EmergencyShuttleType.RESCUE_SHIP)
                        .count();
                
                // If we have existing RESCUE_SHIPs, check if the last one has space
                EmergencyShuttle lastRescueShip = null;
                for (int i = shuttles.size() - 1; i >= 0; i--) {
                    EmergencyShuttle shuttle = shuttles.get(i);
                    if (shuttle.getType() == EmergencyShuttleType.RESCUE_SHIP) {
                        lastRescueShip = shuttle;
                        break;
                    }
                }
                
                // If last RESCUE_SHIP has space, use it
                if (lastRescueShip != null && lastRescueShip.hasSpace(capacity)) {
                    // Continue with existing shuttle
                } else if (existingRescueShipCount >= maxShuttles) {
                    // No more RESCUE_SHIPs available
                    break;
                } else if (remainingTravelers < capacity) {
                    // Not enough travelers to fill a new RESCUE_SHIP completely
                    // Pass to STANDARD_SHUTTLE instead
                    break;
                }
            }
            
            // Try to get or create a shuttle for this traveler
            EmergencyShuttle currentShuttle = getOrCreateShuttleForType(shuttles, type, capacity, maxShuttles);
            if (currentShuttle == null) {
                // No more shuttles of this type available, stop trying
                break;
            }
            
            EmergencyShuttleTraveler shuttleTraveler = EmergencyShuttleTraveler.fromTraveler(traveler);
            boolean added = currentShuttle.addTraveler(shuttleTraveler, capacity);
            
            if (added) {
                state.travelerIndex++;
            } else {
                // Shuttle is full, but we can't add this traveler
                // This shouldn't happen if hasSpace() was checked correctly
                // Break to avoid infinite loop
                break;
            }
        }
    }
    
    private void assignCrewMembersToShuttleType(
            List<CrewMember> allCrewMembers,
            EmergencyShuttleType type,
            int capacity,
            int maxShuttles,
            List<EmergencyShuttle> shuttles,
            AssignmentState state) {
        
        // Assign crew members (continue filling the last shuttle)
        while (state.crewMemberIndex < allCrewMembers.size()) {
            CrewMember crewMember = allCrewMembers.get(state.crewMemberIndex);
            
            // For RESCUE_SHIP: crew members continue filling the last RESCUE_SHIP
            // They don't create new RESCUE_SHIPs unless the last one is full
            // So we just check if we can use the existing one or if we've reached the limit
            if (type == EmergencyShuttleType.RESCUE_SHIP) {
                int existingRescueShipCount = (int) shuttles.stream()
                        .filter(s -> s.getType() == EmergencyShuttleType.RESCUE_SHIP)
                        .count();
                
                // Check if the last RESCUE_SHIP has space
                EmergencyShuttle lastRescueShip = null;
                for (int i = shuttles.size() - 1; i >= 0; i--) {
                    EmergencyShuttle shuttle = shuttles.get(i);
                    if (shuttle.getType() == EmergencyShuttleType.RESCUE_SHIP) {
                        lastRescueShip = shuttle;
                        break;
                    }
                }
                
                // If last RESCUE_SHIP is full and we've reached the limit, stop
                if (lastRescueShip != null && !lastRescueShip.hasSpace(capacity) 
                    && existingRescueShipCount >= maxShuttles) {
                    break;
                }
            }
            
            // Try to get or create a shuttle for this crew member
            EmergencyShuttle currentShuttle = getOrCreateShuttleForType(shuttles, type, capacity, maxShuttles);
            if (currentShuttle == null) {
                // No more shuttles of this type available, stop trying
                break;
            }
            
            EmergencyShuttleCrewMember shuttleCrewMember = EmergencyShuttleCrewMember.fromCrewMember(crewMember);
            boolean added = currentShuttle.addCrewMember(shuttleCrewMember, capacity);
            
            if (added) {
                state.crewMemberIndex++;
            } else {
                // Shuttle is full, but we can't add this crew member
                // This shouldn't happen if hasSpace() was checked correctly
                // Break to avoid infinite loop
                break;
            }
        }
    }
    
    private EmergencyShuttle getOrCreateShuttleForType(
            List<EmergencyShuttle> shuttles,
            EmergencyShuttleType type,
            int capacity,
            int maxShuttles) {
        
        // Find the last shuttle of this type
        EmergencyShuttle lastShuttleOfType = null;
        for (int i = shuttles.size() - 1; i >= 0; i--) {
            EmergencyShuttle shuttle = shuttles.get(i);
            if (shuttle.getType() == type) {
                lastShuttleOfType = shuttle;
                break; // Found the last shuttle of this type
            }
        }
        
        // If we found a last shuttle of this type and it has space, use it
        // This ensures we fill the last shuttle before opening a new one
        if (lastShuttleOfType != null && lastShuttleOfType.hasSpace(capacity)) {
            return lastShuttleOfType;
        }
        
        // No existing shuttle with space, check if we can create a new one
        int shuttleCount = (int) shuttles.stream().filter(s -> s.getType() == type).count();
        if (shuttleCount >= maxShuttles) {
            return null; // No more shuttles of this type available
        }
        
        // For RESCUE_SHIP: only create a new one if we can fill it completely
        // This means we need at least 'capacity' remaining people to assign
        // But we don't know how many people are left to assign here, so we'll create it
        // and let the assignment logic handle it
        
        // Create a new shuttle
        EmergencyShuttle newShuttle = new EmergencyShuttle(type);
        shuttles.add(newShuttle);
        return newShuttle;
    }
    
    private static class AssignmentState {
        int travelerIndex = 0;
        int crewMemberIndex = 0;
    }
}