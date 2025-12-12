package uspace.application.account;

import uspace.application.account.dtos.AccountDto;
import uspace.domain.account.Account;
import uspace.domain.account.AccountUsername;
import uspace.domain.account.HomePlanetName;
import uspace.domain.cruise.booking.BookingId;

public class AccountAssembler {

    public Account toAccount(AccountDto accountDto) {
        return new Account(
                new AccountUsername(accountDto.username),
                new HomePlanetName(accountDto.homePlanetName)
        );
    }

    public AccountDto toDto(Account account) {
        return new AccountDto(
                account.getUsername().toString(),
                account.getHomePlanetName().toString(),
                account.getBookingIds().stream().map(BookingId::toString).toList()
        );
    }
}
