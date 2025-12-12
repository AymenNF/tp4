package uspace.domain.cruise.itinerary;

import jakarta.persistence.*;
import uspace.domain.cruise.itinerary.exceptions.ExcursionAlreadyExistsException;
import uspace.domain.cruise.itinerary.exceptions.InvalidPlanetDateException;
import uspace.domain.cruise.itinerary.exceptions.PlanetAlreadyExistsException;
import uspace.domain.cruise.itinerary.planet.exceptions.PlanetNotFoundException;
import uspace.domain.cruise.itinerary.planet.Planet;
import uspace.domain.cruise.itinerary.planet.PlanetName;
import uspace.domain.cruise.itinerary.planet.excursion.Excursion;
import uspace.domain.cruise.itinerary.planet.excursion.ExcursionName;

import java.util.List;

@Entity
public class Itinerary {
    @Id
    private ItineraryId id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Planet> planets;
    private int minimalDaysForPlanetExcursion;

    public Itinerary() {
    }

    public Itinerary(ItineraryId id, List<Planet> planets, int minimalDaysForPlanetExcursion) {
        this.id = id;
        this.planets = planets;
        this.minimalDaysForPlanetExcursion = minimalDaysForPlanetExcursion;
    }

    public List<Planet> getPlanets() {
        return planets;
    }

    public void addPlanet(Planet newPlanet) {
        if (containsPlanetName(newPlanet)) {
            throw new PlanetAlreadyExistsException();
        }

        if (isPlanetExcursionLessThanMinimalDays(newPlanet) || containsPlanetWithSameTimeSlot(newPlanet)) {
            throw new InvalidPlanetDateException();
        }

        planets.add(newPlanet);
    }

    public void addExcursion(PlanetName planetName, Excursion excursion) {
        if (excursionNameAlreadyExists(excursion.getName())) {
            throw new ExcursionAlreadyExistsException();
        }

        Planet planet = findPlanet(planetName);
        planet.addExcursion(excursion);
    }

    private boolean containsPlanetName(Planet newPlanet) {
        return planets.stream().anyMatch(newPlanet::hasSameNameThan);
    }

    private boolean isPlanetExcursionLessThanMinimalDays(Planet newPlanet) {
        return newPlanet.computeExcursionDurationInDays() < minimalDaysForPlanetExcursion;
    }

    private boolean containsPlanetWithSameTimeSlot(Planet newPlanet) {
        return planets.stream().anyMatch(newPlanet::hasSameTimeSlotThan);
    }

    private Planet findPlanet(PlanetName planetName) {
        return planets.stream().filter(planet -> planet.hasSameNameThan(planetName))
                      .findFirst()
                      .orElseThrow(PlanetNotFoundException::new);
    }

    private boolean excursionNameAlreadyExists(ExcursionName name) {
        return planets.stream().anyMatch(planet -> planet.containsExcursion(name));
    }
}
