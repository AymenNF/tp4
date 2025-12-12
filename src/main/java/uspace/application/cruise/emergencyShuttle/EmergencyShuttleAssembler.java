package uspace.application.cruise.emergencyShuttle;

import uspace.application.cruise.emergencyShuttle.dtos.EmergencyShuttleCrewMemberDto;
import uspace.application.cruise.emergencyShuttle.dtos.EmergencyShuttleDto;
import uspace.application.cruise.emergencyShuttle.dtos.EmergencyShuttleManifestDto;
import uspace.application.cruise.emergencyShuttle.dtos.EmergencyShuttleTravelerDto;
import uspace.domain.cruise.emergencyShuttle.EmergencyShuttle;
import uspace.domain.cruise.emergencyShuttle.EmergencyShuttleCrewMember;
import uspace.domain.cruise.emergencyShuttle.EmergencyShuttleManifest;
import uspace.domain.cruise.emergencyShuttle.EmergencyShuttleTraveler;

import java.util.List;
import java.util.stream.Collectors;

public class EmergencyShuttleAssembler {
    
    public EmergencyShuttleManifestDto toDto(EmergencyShuttleManifest manifest) {
        List<EmergencyShuttleDto> shuttleDtos = manifest.getShuttles().stream()
                .map(this::toShuttleDto)
                .collect(Collectors.toList());
        
        return new EmergencyShuttleManifestDto(
                (int) manifest.getTotalCost().toFloat(),
                shuttleDtos
        );
    }
    
    private EmergencyShuttleDto toShuttleDto(EmergencyShuttle shuttle) {
        List<EmergencyShuttleTravelerDto> travelerDtos = shuttle.getTravelers().stream()
                .map(this::toTravelerDto)
                .collect(Collectors.toList());
        
        List<EmergencyShuttleCrewMemberDto> crewMemberDtos = shuttle.getCrewMembers().stream()
                .map(this::toCrewMemberDto)
                .collect(Collectors.toList());
        
        return new EmergencyShuttleDto(
                shuttle.getType().name(),
                travelerDtos,
                crewMemberDtos
        );
    }
    
    private EmergencyShuttleTravelerDto toTravelerDto(EmergencyShuttleTraveler traveler) {
        return new EmergencyShuttleTravelerDto(
                traveler.getTravelerId().toString(),
                traveler.getTravelerName().toString()
        );
    }
    
    private EmergencyShuttleCrewMemberDto toCrewMemberDto(EmergencyShuttleCrewMember crewMember) {
        return new EmergencyShuttleCrewMemberDto(
                crewMember.getEmployeeId().toString(),
                crewMember.getCrewMemberName().toString()
        );
    }
}

