package uspace.domain.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import uspace.domain.account.exceptions.AccountCannotBeDeletedException;
import uspace.domain.cruise.booking.BookingId;

import static org.junit.jupiter.api.Assertions.*;

class AccountDeletionValidatorTest {
    private static final AccountUsername ANY_USERNAME = new AccountUsername("a_username");
    private static final HomePlanetName ANY_HOME_PLANET_NOT_EARTH = new HomePlanetName("a_home_planet");
    private static final HomePlanetName ANY_HOME_PLANET_EARTH = new HomePlanetName("EARTH");
    private static final BookingId ANY_BOOKING_ID = new BookingId("a_booking_id");
    private AccountDeletionValidator accountDeletionValidator;

    @BeforeEach
    void createAccountDeletionValidator() {
        accountDeletionValidator = new AccountDeletionValidator();
    }

    @Test
    void givenAEarthAccount_whenValidatingDeletion_thenThrowsAccountCannotBeDeletedException() {
        Account account = new Account(ANY_USERNAME, ANY_HOME_PLANET_EARTH);

        Executable validateDeletion = () -> accountDeletionValidator.validateDeletion(account);

        assertThrows(AccountCannotBeDeletedException.class, validateDeletion);
    }

    @Test
    void givenANonEarthAccountWithBookings_whenValidatingDeletion_thenThrowsAccountCannotBeDeletedException() {
        Account account = new Account(ANY_USERNAME, ANY_HOME_PLANET_NOT_EARTH);
        account.addBookingId(ANY_BOOKING_ID);

        Executable validateDeletion = () -> accountDeletionValidator.validateDeletion(account);

        assertThrows(AccountCannotBeDeletedException.class, validateDeletion);
    }

    @Test
    void givenANonEarthAccountWithoutBookings_whenValidatingDeletion_thenDoesNotThrowAnyException() {
        Account account = new Account(ANY_USERNAME, ANY_HOME_PLANET_NOT_EARTH);

        Executable validateDeletion = () -> accountDeletionValidator.validateDeletion(account);

        assertDoesNotThrow(validateDeletion);
    }
}