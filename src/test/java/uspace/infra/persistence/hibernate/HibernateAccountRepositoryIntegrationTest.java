package uspace.infra.persistence.hibernate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import uspace.domain.account.Account;
import uspace.domain.account.AccountUsername;
import uspace.domain.account.HomePlanetName;

import static org.junit.jupiter.api.Assertions.*;

@Tag("component")
class HibernateAccountRepositoryIntegrationTest {
    private static final AccountUsername ANY_USERNAME = new AccountUsername("a_username");
    private static final AccountUsername ANY_SAME_USERNAME = new AccountUsername("a_username");
    private static final AccountUsername ANY_DIFFERENT_USERNAME = new AccountUsername("another_username");
    private static final HomePlanetName ANY_HOME_PLANET = new HomePlanetName("a_home_planet");
    private static final Account ANY_ACCOUNT = new Account(ANY_USERNAME, ANY_HOME_PLANET);
    private HibernateAccountRepository hibernateAccountRepository;

    @BeforeEach
    void createHibernateAccountRepository() {
        hibernateAccountRepository = new HibernateAccountRepository();
    }

    @AfterEach
    void tearDownSession() {
        HibernateUtil.shutdown();
    }

    @Test
    void givenASavedAccount_whenFindingByUsername_thenReturnsTheAccount() {
        hibernateAccountRepository.save(ANY_ACCOUNT);

        Account foundAccount = hibernateAccountRepository.findByUsername(ANY_SAME_USERNAME);

        assertEquals(ANY_USERNAME, foundAccount.getUsername());
        assertEquals(ANY_HOME_PLANET, foundAccount.getHomePlanetName());
    }

    @Test
    void givenANotSavedAccount_whenFindingById_thenReturnsNull() {
        hibernateAccountRepository.save(ANY_ACCOUNT);

        Account foundAccount = hibernateAccountRepository.findByUsername(ANY_DIFFERENT_USERNAME);

        assertNull(foundAccount);
    }

    @Test
    void givenAnAlreadySavedAccount_whenSaving_thenUpdatesTheAccount() {
        hibernateAccountRepository.save(ANY_ACCOUNT);
        HomePlanetName anyDifferentHomePlanet = new HomePlanetName("another_home_planet");
        Account updatedAccount = new Account(ANY_USERNAME, anyDifferentHomePlanet);

        hibernateAccountRepository.save(updatedAccount);

        Account foundAccount = hibernateAccountRepository.findByUsername(ANY_USERNAME);
        assertEquals(ANY_USERNAME, foundAccount.getUsername());
        assertEquals(anyDifferentHomePlanet, foundAccount.getHomePlanetName());
    }

    @Test
    void givenAnAlreadySavedAccount_whenDeleting_thenTheAccountCannotBeFoundAgain() {
        hibernateAccountRepository.save(ANY_ACCOUNT);

        hibernateAccountRepository.delete(ANY_ACCOUNT);

        Account foundAccount = hibernateAccountRepository.findByUsername(ANY_USERNAME);
        assertNull(foundAccount);
    }

    @Test
    void givenANotSavedAccount_whenDeleting_thenDoesNothing() {
        hibernateAccountRepository.delete(ANY_ACCOUNT);

        Account foundAccount = hibernateAccountRepository.findByUsername(ANY_USERNAME);
        assertNull(foundAccount);
    }
}