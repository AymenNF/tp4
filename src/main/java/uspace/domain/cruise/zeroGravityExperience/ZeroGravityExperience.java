package uspace.domain.cruise.zeroGravityExperience;

import jakarta.persistence.*;
import uspace.domain.cruise.booking.traveler.TravelerId;
import uspace.domain.cruise.zeroGravityExperience.exceptions.ZeroGravityExperienceAlreadyBookedException;
import uspace.domain.cruise.zeroGravityExperience.exceptions.ZeroGravityExperienceFullException;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ZeroGravityExperience {
    @Id
    private ZeroGravityExperienceId id;

    private int capacity;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<TravelerId> travelersBooked;

    public ZeroGravityExperience() {

    }

    public ZeroGravityExperience(ZeroGravityExperienceId id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        this.travelersBooked = new ArrayList<>();
    }

    public void book(TravelerId travelerId) {
        if (hasTravelerBooked(travelerId)) {
            throw new ZeroGravityExperienceAlreadyBookedException();
        }

        if (isFull()) {
            throw new ZeroGravityExperienceFullException();
        }

        travelersBooked.add(travelerId);
    }

    public boolean hasTravelerBooked(TravelerId travelerId) {
        return travelersBooked.contains(travelerId);
    }

    private boolean isFull() {
        return travelersBooked.size() >= capacity;
    }
}
