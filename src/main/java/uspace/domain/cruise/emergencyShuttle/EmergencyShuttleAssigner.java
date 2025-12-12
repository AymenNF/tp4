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

        List<Traveler> allTravelers = collectAllTravelers(cruise);
        List<CrewMember> allCrewMembers = cruise.getCrewMembers();

        AssignmentState state = new AssignmentState();

        // 1. Assign travelers to RESCUE_SHIP
        assignTravelersToShuttleType(
                allTravelers,
                allCrewMembers.size(),
                EmergencyShuttleType.RESCUE_SHIP,
                rescueShipCapacity,
                rescueShipCount,
                shuttles,
                state);

        // 2. Assign crew members to RESCUE_SHIP
        assignCrewMembersToShuttleType(
                allCrewMembers,
                EmergencyShuttleType.RESCUE_SHIP,
                rescueShipCapacity,
                rescueShipCount,
                shuttles,
                state);

        // 3. Assign remaining travelers to STANDARD_SHUTTLE
        assignTravelersToShuttleType(
                allTravelers,
                0,
                EmergencyShuttleType.STANDARD_SHUTTLE,
                standardShuttleCapacity,
                Integer.MAX_VALUE,
                shuttles,
                state);

        // 4. Assign remaining crew members to STANDARD_SHUTTLE
        assignCrewMembersToShuttleType(
                allCrewMembers,
                EmergencyShuttleType.STANDARD_SHUTTLE,
                standardShuttleCapacity,
                Integer.MAX_VALUE,
                shuttles,
                state);

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
            int availableCrewCount,
            EmergencyShuttleType type,
            int capacity,
            int maxShuttles,
            List<EmergencyShuttle> shuttles,
            AssignmentState state) {

        while (state.travelerIndex < allTravelers.size()) {
            // For RESCUE_SHIP, check if we should stop creating new ones before
            // getting/creating
            if (type == EmergencyShuttleType.RESCUE_SHIP) {
                EmergencyShuttle lastRescue = getLastShuttleOfType(shuttles, type);
                if (lastRescue != null && !lastRescue.hasSpace(capacity)) {
                    // Last rescue ship is full. Check if we should create another
                    int remainingTravelers = allTravelers.size() - state.travelerIndex;
                    if ((remainingTravelers + availableCrewCount) < capacity) {
                        break;
                    }
                }
            }

            EmergencyShuttle currentShuttle = getOrCreateShuttleForType(shuttles, type, capacity, maxShuttles);
            if (currentShuttle == null) {
                // No more shuttles can be created of this type
                break;
            }

            Traveler traveler = allTravelers.get(state.travelerIndex);
            EmergencyShuttleTraveler shuttleTraveler = EmergencyShuttleTraveler.fromTraveler(traveler);
            boolean added = currentShuttle.addTraveler(shuttleTraveler, capacity);

            if (added) {
                state.travelerIndex++;
            } else {
                // Current shuttle is full. Check if we're at the max shuttle count
                int currentCount = (int) shuttles.stream()
                        .filter(s -> s.getType() == type)
                        .count();
                if (currentCount >= maxShuttles) {
                    // Can't create more shuttles of this type
                    break;
                }
                // Otherwise, loop will create a new shuttle on next iteration
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

        while (state.crewMemberIndex < allCrewMembers.size()) {
            // For RESCUE_SHIP, check if we should stop creating new ones before
            // getting/creating
            if (type == EmergencyShuttleType.RESCUE_SHIP) {
                EmergencyShuttle lastRescue = getLastShuttleOfType(shuttles, type);
                if (lastRescue != null && !lastRescue.hasSpace(capacity)) {
                    // Last rescue ship is full. Check if we should create another
                    int remainingCrew = allCrewMembers.size() - state.crewMemberIndex;
                    if (remainingCrew < capacity) {
                        // Not enough crew left to fill another rescue ship
                        break;
                    }
                }
            }

            EmergencyShuttle currentShuttle = getOrCreateShuttleForType(shuttles, type, capacity, maxShuttles);
            if (currentShuttle == null) {
                // No more shuttles can be created of this type
                break;
            }

            CrewMember crewMember = allCrewMembers.get(state.crewMemberIndex);
            EmergencyShuttleCrewMember shuttleCrewMember = EmergencyShuttleCrewMember.fromCrewMember(crewMember);
            boolean added = currentShuttle.addCrewMember(shuttleCrewMember, capacity);

            if (added) {
                state.crewMemberIndex++;
            } else {
                // Current shuttle is full. Check if we're at the max shuttle count
                int currentCount = (int) shuttles.stream()
                        .filter(s -> s.getType() == type)
                        .count();
                if (currentCount >= maxShuttles) {
                    // Can't create more shuttles of this type
                    break;
                }
                // Otherwise, loop will create a new shuttle on next iteration
            }
        }
    }

    private EmergencyShuttle getLastShuttleOfType(List<EmergencyShuttle> shuttles, EmergencyShuttleType type) {
        for (int i = shuttles.size() - 1; i >= 0; i--) {
            EmergencyShuttle shuttle = shuttles.get(i);
            if (shuttle.getType() == type) {
                return shuttle;
            }
        }
        return null;
    }

    private EmergencyShuttle getOrCreateShuttleForType(
            List<EmergencyShuttle> shuttles,
            EmergencyShuttleType type,
            int capacity,
            int maxShuttles) {

        EmergencyShuttle lastShuttleOfType = getLastShuttleOfType(shuttles, type);

        if (lastShuttleOfType != null && lastShuttleOfType.hasSpace(capacity)) {
            return lastShuttleOfType;
        }

        int shuttleCount = (int) shuttles.stream().filter(s -> s.getType() == type).count();
        if (shuttleCount >= maxShuttles) {
            return null;
        }

        EmergencyShuttle newShuttle = new EmergencyShuttle(type);
        shuttles.add(newShuttle);
        return newShuttle;
    }

    private static class AssignmentState {
        int travelerIndex = 0;
        int crewMemberIndex = 0;
    }
}