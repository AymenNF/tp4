package uspace.application.cruise.cabin;

import jakarta.inject.Inject;
import uspace.application.cruise.cabin.dtos.CabinAttributionDto;
import uspace.application.cruise.cabin.dtos.CabinDto;
import uspace.domain.cruise.Cruise;
import uspace.domain.cruise.CruiseId;
import uspace.domain.cruise.CruiseRepository;
import uspace.domain.cruise.cabin.CabinType;
import uspace.domain.cruise.cabin.attribution.CabinAttribution;
import uspace.domain.cruise.cabin.attribution.CabinAttributionCriteria;
import uspace.domain.cruise.exceptions.CruiseNotFoundException;
import uspace.infra.cabin.CabinJsonReader;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CabinAttributionService {
    private final CruiseRepository cruiseRepository;
    private final CabinJsonReader cabinJsonReader;

    @Inject
    public CabinAttributionService(CruiseRepository cruiseRepository, CabinJsonReader cabinJsonReader) {
        this.cruiseRepository = cruiseRepository;
        this.cabinJsonReader = cabinJsonReader;
    }

    public CabinAttributionDto getCabinAttributions(String cruiseId, String criteria) {
        Cruise cruise = cruiseRepository.findById(new CruiseId(cruiseId));
        if (cruise == null) {
            throw new CruiseNotFoundException();
        }

        CabinAttributionCriteria attributionCriteria = CabinAttributionCriteria.fromString(criteria);
        
        // Lire le fichier JSON à chaque requête
        Map<CabinType, List<String>> cabinsByType = cabinJsonReader.readCabins();
        
        // Obtenir les bookings
        Map<uspace.domain.cruise.booking.BookingId, uspace.domain.cruise.booking.Booking> bookings = 
            cruise.getCruiseBookings().getAllBookings();
        
        // Attribuer les cabines
        List<CabinAttribution> attributions = CabinAttribution.assignCabins(
            cabinsByType,
            bookings,
            attributionCriteria
        );
        
        // Convertir en DTOs
        List<CabinDto> cabinDtos = attributions.stream()
            .map(attribution -> new CabinDto(
                attribution.getCabinId(),
                attribution.getBookingId().toString(),
                attribution.getCategory().name()
            ))
            .collect(Collectors.toList());
        
        return new CabinAttributionDto(cabinDtos);
    }
}

