package uspace.application.cruise.booking.traveler;

import jakarta.inject.Inject;
import uspace.application.cruise.booking.traveler.dtos.SportBookingDto;
import uspace.application.utils.dateTimeParser.LocalDateTimeParser;
import uspace.domain.cruise.Cruise;
import uspace.domain.cruise.CruiseId;
import uspace.domain.cruise.CruiseRepository;
import uspace.domain.cruise.booking.BookingId;
import uspace.domain.cruise.booking.traveler.TravelerId;
import uspace.domain.cruise.dateTime.CruiseDateTime;
import uspace.domain.cruise.exceptions.CruiseNotFoundException;
import uspace.domain.cruise.sport.Sport;
import uspace.domain.cruise.sport.SportRegistry;
import uspace.domain.cruise.sport.exceptions.SportNotFoundException;

import java.time.LocalDateTime;

public class TravelerService {
    private final CruiseRepository cruiseRepository;
    private final LocalDateTimeParser localDateTimeParser;
    private final SportRegistry sportRegistry;

    @Inject
    public TravelerService(CruiseRepository cruiseRepository, LocalDateTimeParser localDateTimeParser,
                           SportRegistry sportRegistry) {
        this.cruiseRepository = cruiseRepository;
        this.localDateTimeParser = localDateTimeParser;
        this.sportRegistry = sportRegistry;
    }

    public void bookZeroGravityExperience(String cruiseId, String bookingIdStr, String travelerIdStr, String experienceBookingDateTime) {
        Cruise cruise = cruiseRepository.findById(new CruiseId(cruiseId));
        if (cruise == null) {
            throw new CruiseNotFoundException();
        }

        LocalDateTime experienceBookingLocalDateTime = localDateTimeParser.parse(experienceBookingDateTime);
        CruiseDateTime experienceCruiseBookingDateTime = new CruiseDateTime(experienceBookingLocalDateTime);

        BookingId bookingId = new BookingId(bookingIdStr);
        TravelerId travelerId = new TravelerId(travelerIdStr);

        cruise.bookZeroGravityExperience(bookingId, travelerId, experienceCruiseBookingDateTime);

        cruiseRepository.save(cruise);
    }

    public void bookSport(String cruiseIdStr, String bookingIdStr, String travelerIdStr, String sportName) {
        CruiseId cruiseId = new CruiseId(cruiseIdStr);
        Cruise cruise = cruiseRepository.findById(cruiseId);
        if (cruise == null) {
            throw new CruiseNotFoundException();
        }

        Sport sport = sportRegistry.findSport(sportName, cruiseId);
        if (sport == null) {
            throw new SportNotFoundException();
        }

        BookingId bookingId = new BookingId(bookingIdStr);
        TravelerId travelerId = new TravelerId(travelerIdStr);

        cruise.bookSport(bookingId, travelerId, sport);

        cruiseRepository.save(cruise);
    }
}
