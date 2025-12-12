package uspace.domain.account;

import uspace.domain.account.exceptions.AccountCannotBeDeletedException;

public class AccountDeletionValidator {
    public void validateDeletion(Account account) {
        if (account.hasBookings() || account.isFromEarth()) {
            throw new AccountCannotBeDeletedException();
        }
    }
}
