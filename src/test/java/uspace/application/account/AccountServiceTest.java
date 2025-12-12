package uspace.application.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uspace.application.account.dtos.AccountDto;
import uspace.domain.account.*;
import uspace.domain.account.exceptions.AccountCannotBeDeletedException;
import uspace.domain.account.exceptions.AccountNotFoundException;
import uspace.domain.account.exceptions.UsernameAlreadyExistsException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    private static final String ANY_USERNAME_STR = "a_username";
    private static final AccountUsername ANY_USERNAME = new AccountUsername(ANY_USERNAME_STR);
    private static final String ANY_HOME_PLANET_STR = "a_home_planet";
    private static final HomePlanetName ANY_HOME_PLANET = new HomePlanetName(ANY_HOME_PLANET_STR);
    private static final AccountDto ANY_ACCOUNT_DTO = new AccountDto(ANY_USERNAME_STR, ANY_HOME_PLANET_STR, null);
    private static final Account ANY_ACCOUNT = new Account(ANY_USERNAME, ANY_HOME_PLANET);
    private AccountService accountService;
    @Mock
    private AccountRepository accountRepositoryMock;
    @Mock
    private AccountAssembler accountAssemblerMock;
    @Mock
    private AccountDeletionValidator accountDeletionValidatorMock;


    @BeforeEach
    void createAccountService() {
        accountService = new AccountService(accountRepositoryMock, accountAssemblerMock, accountDeletionValidatorMock);
    }

    @Test
    void givenExistingAccountWithUsername_whenCreateAccount_thenThrowsUsernameAlreadyExistsException() {
        when(accountRepositoryMock.findByUsername(ANY_USERNAME)).thenReturn(ANY_ACCOUNT);

        Executable createAccount = () -> accountService.createAccount(ANY_ACCOUNT_DTO);

        assertThrows(UsernameAlreadyExistsException.class, createAccount);
    }

    @Test
    void givenNonExistingAccount_whenCreateAccount_thenSavesAccount() {
        when(accountRepositoryMock.findByUsername(ANY_USERNAME)).thenReturn(null);
        when(accountAssemblerMock.toAccount(ANY_ACCOUNT_DTO)).thenReturn(ANY_ACCOUNT);

        accountService.createAccount(ANY_ACCOUNT_DTO);

        verify(accountRepositoryMock).save(ANY_ACCOUNT);
    }

    @Test
    void givenNonExistingAccount_whenDeleteAccount_thenThrowsAccountNotFoundException() {
        givenNonExistingAccount();

        Executable deleteAccount = () -> accountService.deleteAccount(ANY_USERNAME_STR);

        assertThrows(AccountNotFoundException.class, deleteAccount);
    }

    @Test
    void givenExistingAccount_whenDeleteAccount_thenValidatesDeletion() {
        givenExistingAccount();

        accountService.deleteAccount(ANY_USERNAME_STR);

        verify(accountDeletionValidatorMock).validateDeletion(ANY_ACCOUNT);
    }

    @Test
    void givenExistingAccount_whenDeleteAccount_thenDeletesAccount() {
        givenExistingAccount();

        accountService.deleteAccount(ANY_USERNAME_STR);

        verify(accountRepositoryMock).delete(ANY_ACCOUNT);
    }

    @Test
    void givenExistingAccountAndDeletionValidatorThrows_whenDeleteAccount_thenDoNotDeleteAccount() {
        givenExistingAccount();
        doThrow(new AccountCannotBeDeletedException()).when(accountDeletionValidatorMock).validateDeletion(ANY_ACCOUNT);

        Executable deleteAccount = () -> accountService.deleteAccount(ANY_USERNAME_STR);

        assertThrows(AccountCannotBeDeletedException.class, deleteAccount);
        verify(accountRepositoryMock, never()).delete(ANY_ACCOUNT);
    }

    private void givenNonExistingAccount() {
        when(accountRepositoryMock.findByUsername(ANY_USERNAME)).thenReturn(null);
    }

    private void givenExistingAccount() {
        when(accountRepositoryMock.findByUsername(ANY_USERNAME)).thenReturn(ANY_ACCOUNT);
    }

}