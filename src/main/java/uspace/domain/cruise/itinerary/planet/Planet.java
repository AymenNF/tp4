package uspace.domain.cruise.itinerary.planet;

import jakarta.persistence.*;
import uspace.domain.cruise.dateTime.CruiseDateTime;
import uspace.domain.cruise.itinerary.planet.excursion.Excursion;
import uspace.domain.cruise.itinerary.planet.excursion.ExcursionName;
import uspace.domain.cruise.itinerary.planet.excursion.exceptions.InvalidExcursionException;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Planet {
    @Id
    private PlanetName name;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "localDateTime",
                    column = @Column(name = "arrivalDateTime")
            ),
    })
    private CruiseDateTime arrivalDateTime;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "localDateTime",
                    column = @Column(name = "departureDateTime")
            ),
    })
    private CruiseDateTime departureDateTime;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Excursion> excursions;

    public Planet() {
    }

    public Planet(PlanetName name, CruiseDateTime arrivalDateTime, CruiseDateTime departureDateTime) {
        this.name = name;
        this.arrivalDateTime = arrivalDateTime;
        this.departureDateTime = departureDateTime;
        this.excursions = new ArrayList<>();
    }

    public PlanetName getName() {
        return name;
    }

    public CruiseDateTime getArrivalDate() {
        return arrivalDateTime;
    }

    public CruiseDateTime getDepartureDate() {
        return departureDateTime;
    }

    public List<Excursion> getExcursions() {return excursions;}

    public boolean hasSameNameThan(Planet otherPlanet) {
        return name.equals(otherPlanet.name);
    }

    public boolean hasSameNameThan(PlanetName otherPlanetName) {
        return name.equals(otherPlanetName);
    }

    public boolean arriveBefore(CruiseDateTime dateTime) {
        return arrivalDateTime.isBefore(dateTime);
    }

    public boolean departAfter(CruiseDateTime dateTime) {
        return departureDateTime.isAfter(dateTime);
    }

    public boolean hasSameTimeSlotThan(Planet otherPlanet) {
        return arriveInTimeSlot(otherPlanet) || departInTimeSlot(otherPlanet);
    }

    public int computeExcursionDurationInDays() {
        return arrivalDateTime.calculateDaysBefore(departureDateTime);
    }

    public boolean containsExcursion(ExcursionName name) {
        return excursions.stream().anyMatch(excursion -> excursion.hasSameNameThan(name));
    }

    public void addExcursion(Excursion excursion) {
        if (!excursion.isDuringTimeSlot(arrivalDateTime, departureDateTime)) {
            throw new InvalidExcursionException();
        }

        excursions.add(excursion);
    }

    private boolean arriveInTimeSlot(Planet otherPlanet) {
        return !(otherPlanet.arrivalDateTime.isBefore(arrivalDateTime)
                 || otherPlanet.arrivalDateTime.isAfter(departureDateTime));
    }

    private boolean departInTimeSlot(Planet otherPlanet) {
        return !(otherPlanet.departureDateTime.isBefore(arrivalDateTime)
                 || otherPlanet.departureDateTime.isAfter(departureDateTime));
    }
}
