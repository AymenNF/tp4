package uspace.api.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import uspace.application.account.dtos.AccountDto;
import uspace.domain.exceptions.MissingParameterException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountDtoValidatorTest {
    private static final String ANY_USERNAME_PRESENT = "validUsername";
    private static final String ANY_HOME_PLANET_NAME_PRESENT = "validHomePlanetName";
    private static final List<String> ANY_BOOKING_IDS = List.of();
    private AccountDtoValidator accountDtoValidator;

    @BeforeEach
    void createAccountDtoValidator() {
        accountDtoValidator = new AccountDtoValidator();
    }

    @Test
    void givenAnAccountDtoWithNullUsername_whenValidating_thenThrowsMissingParameterException() {
        AccountDto accountDto = new AccountDto(null, ANY_HOME_PLANET_NAME_PRESENT, ANY_BOOKING_IDS);

        Executable validate = () -> accountDtoValidator.validate(accountDto);

        assertThrows(MissingParameterException.class, validate);
    }

    @Test
    void givenAnAccountDtoWithEmptyUsername_whenValidating_thenThrowsMissingParameterException() {
        AccountDto accountDto = new AccountDto("", ANY_HOME_PLANET_NAME_PRESENT, ANY_BOOKING_IDS);

        Executable validate = () -> accountDtoValidator.validate(accountDto);

        assertThrows(MissingParameterException.class, validate);
    }

    @Test
    void givenAnAccountDtoWithNullHomePlanetName_whenValidating_thenThrowsMissingParameterException() {
        AccountDto accountDto = new AccountDto(ANY_USERNAME_PRESENT, null, ANY_BOOKING_IDS);

        Executable validate = () -> accountDtoValidator.validate(accountDto);

        assertThrows(MissingParameterException.class, validate);
    }

    @Test
    void givenAnAccountDtoWithEmptyHomePlanetName_whenValidating_thenThrowsMissingParameterException() {
        AccountDto accountDto = new AccountDto(ANY_USERNAME_PRESENT, "", ANY_BOOKING_IDS);

        Executable validate = () -> accountDtoValidator.validate(accountDto);

        assertThrows(MissingParameterException.class, validate);
    }

    @Test
    void givenAnAccountDtoWithNullBookingIds_whenValidating_thenDoesNotThrow() {
        AccountDto accountDto = new AccountDto(ANY_USERNAME_PRESENT, ANY_HOME_PLANET_NAME_PRESENT, ANY_BOOKING_IDS);

        Executable validate = () -> accountDtoValidator.validate(accountDto);

        assertDoesNotThrow(validate);
    }
}