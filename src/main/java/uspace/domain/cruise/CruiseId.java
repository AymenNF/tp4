package uspace.domain.cruise;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class CruiseId implements Serializable {

    private String id;

    public CruiseId() {

    }

    public CruiseId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        CruiseId other = (CruiseId) obj;
        return id.equals(other.id);
    }
}
