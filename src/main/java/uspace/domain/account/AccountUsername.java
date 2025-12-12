package uspace.domain.account;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class AccountUsername implements Serializable {

    private String username;

    public AccountUsername(String username) {
        this.username = username;
    }

    public AccountUsername() {

    }

    @Override
    public String toString() {
        return username;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        AccountUsername other = (AccountUsername) obj;
        return this.username.equals(other.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
