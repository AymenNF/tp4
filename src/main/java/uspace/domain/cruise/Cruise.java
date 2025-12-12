package uspace.domain.cruise;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import uspace.domain.cruise.booking.*;
import uspace.domain.cruise.booking.BookingCreator;
import uspace.domain.cruise.booking.newBooking.NewBooking;
import uspace.domain.cruise.booking.traveler.TravelerId;
import uspace.domain.cruise.booking.traveler.exceptions.TravelerNotFoundException;
import uspace.domain.cruise.cabin.CabinAvailabilities;
import uspace.domain.cruise.cabin.CabinPriceRegistry;
import uspace.domain.cruise.crew.Crew;
import uspace.domain.cruise.crew.crewMember.CrewMember;
import uspace.domain.cruise.dateTime.CruiseDateTime;
import uspace.domain.cruise.itinerary.Itinerary;
import uspace.domain.cruise.itinerary.exceptions.InvalidPlanetDateException;
import uspace.domain.cruise.itinerary.planet.Planet;
import uspace.domain.cruise.itinerary.planet.PlanetName;
import uspace.domain.cruise.itinerary.planet.excursion.Excursion;
import uspace.domain.cruise.money.Money;
import uspace.domain.cruise.sport.Sport;
import uspace.domain.cruise.zeroGravityExperience.ZeroGravityExperience;
import uspace.domain.cruise.zeroGravityExperience.exceptions.ZeroGravityExperienceBookingTimeException;

import java.util.List;

@Entity
@Table(name = "Cruise")
public class Cruise {
    @Id
    private CruiseId id;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(
                name = "localDateTime",
                column = @Column(name = "departureDateTime")
        ),
    })
    private CruiseDateTime departureDateTime;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "localDateTime",
                    column = @Column(name = "endDateTime")
            ),
    })
    private CruiseDateTime endDateTime;

    @Fetch(FetchMode.SELECT)
    private CabinPriceRegistry cabinPriceRegistry;

    @OneToOne(cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private CabinAvailabilities cabinAvailabilities;

    @OneToOne(cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private CruiseBookings cruiseBookings;

    @OneToOne(cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private ZeroGravityExperience zeroGravityExperience;

    @OneToOne(cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private Itinerary itinerary;

    @OneToOne(cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private Crew crew;

    public Cruise() {

    }

    public Cruise(CruiseId id, CruiseDateTime departureDateTime, CruiseDateTime endDateTime, CabinPriceRegistry cabinPriceRegistry,
                  CabinAvailabilities cabinAvailabilities, CruiseBookings  cruiseBookings, ZeroGravityExperience zeroGravityExperience, Itinerary itinerary, Crew crew) {
        this.id = id;
        this.departureDateTime = departureDateTime;
        this.endDateTime = endDateTime;
        this.cabinPriceRegistry = cabinPriceRegistry;
        this.cabinAvailabilities = cabinAvailabilities;
        this.cruiseBookings = cruiseBookings;
        this.zeroGravityExperience = zeroGravityExperience;
        this.itinerary = itinerary;
        this.crew = crew;
    }

    public CruiseId getId() {
        return id;
    }

    public CruiseDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public CruiseDateTime getEndDateTime() {
        return endDateTime;
    }

    public List<Planet> getPlanetsInItinerary() {
        return itinerary.getPlanets();
    }

    public BookingId processBooking(NewBooking newBooking, BookingCreator bookingCreator) {
        Money cabinBaseCostPerPerson = cabinPriceRegistry.getCabinPrice(newBooking.getCabinType());
        Booking booking = bookingCreator.createBooking(newBooking, departureDateTime, cabinBaseCostPerPerson);

        cabinAvailabilities.bookCabin(booking.getCabinType());
        cruiseBookings.add(booking);

        return booking.getId();
    }

    public Booking findBookingById(BookingId bookingId) {
        return cruiseBookings.findById(bookingId);
    }

    public void bookZeroGravityExperience(BookingId bookingId, TravelerId travelerId, CruiseDateTime experienceBookingDateTime) {
        if (experienceBookingDateTime.isAfter(departureDateTime)) {
            throw new ZeroGravityExperienceBookingTimeException();
        }

        Booking booking = cruiseBookings.findById(bookingId);
        if (booking == null) {
            throw new TravelerNotFoundException();
        }

        booking.bookZeroGravityExperience(travelerId, zeroGravityExperience);
    }

    public void addPlanetToItinerary(Planet planet) {
        if (isPlanetExcursionOutsideOfCruiseTime(planet)) {
            throw new InvalidPlanetDateException();
        }

        itinerary.addPlanet(planet);
    }

    public void addCrewMember(CrewMember newCrewMember) {
        crew.addCrewMember(newCrewMember, cabinAvailabilities);
    }

    public List<CrewMember> getCrewMembers() {
        return crew.getAllCrewMembers();
    }

    public CruiseBookings getCruiseBookings() {
        return cruiseBookings;
    }

    public void addExcursionToPlanet(PlanetName excursionPlanetName, Excursion excursion) {
        itinerary.addExcursion(excursionPlanetName, excursion);
    }

    public void bookSport(BookingId bookingId, TravelerId travelerId, Sport sport) {
        Booking booking = cruiseBookings.findById(bookingId);
        if (booking == null) {
            throw new TravelerNotFoundException();
        }

        booking.bookSport(travelerId, sport);
    }

    private boolean isPlanetExcursionOutsideOfCruiseTime(Planet planet) {
        return planet.arriveBefore(departureDateTime) || planet.departAfter(endDateTime);
    }
}
