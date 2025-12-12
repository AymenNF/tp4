package uspace.domain.account;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import uspace.domain.cruise.booking.BookingId;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {

    @Id
    private AccountUsername username;
    private HomePlanetName homePlanetName;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<BookingId> bookingIds;

    public Account() {
    }

    public Account(AccountUsername username, HomePlanetName homePlanetName) {
        this.username = username;
        this.homePlanetName = homePlanetName;
        this.bookingIds = new ArrayList<>();
    }

    public AccountUsername getUsername() {
        return username;
    }

    public HomePlanetName getHomePlanetName() {
        return homePlanetName;
    }

    public List<BookingId> getBookingIds() {
        return bookingIds;
    }

    public void addBookingId(BookingId id) {
        bookingIds.add(id);
    }

    public boolean hasBookings() {
        return !bookingIds.isEmpty();
    }

    public boolean isFromEarth() {
        return homePlanetName.compareWithCaseInsensitive("Earth");
    }
}
