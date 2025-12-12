package uspace.api.account;

import uspace.application.account.dtos.AccountDto;
import uspace.domain.exceptions.MissingParameterException;

public class AccountDtoValidator {

    public void validate(AccountDto accountDto) {
        if (accountDto.username == null || accountDto.username.isEmpty()) {
            throw new MissingParameterException("username");
        }
        if (accountDto.homePlanetName == null || accountDto.homePlanetName.isEmpty()) {
            throw new MissingParameterException("homePlanetName");
        }
    }
}
