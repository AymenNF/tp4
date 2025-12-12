package uspace.domain.cruise.itinerary.planet.excursion;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ExcursionCapacity implements Serializable {

    private int capacity;

    public ExcursionCapacity() {
    }

    public ExcursionCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int toInt() {
        return capacity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        ExcursionCapacity other = (ExcursionCapacity) obj;
        return this.capacity == other.capacity;
    }
}
