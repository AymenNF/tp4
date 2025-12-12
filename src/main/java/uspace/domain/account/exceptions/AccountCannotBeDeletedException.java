package uspace.domain.account.exceptions;

public class AccountCannotBeDeletedException extends RuntimeException {

    public AccountCannotBeDeletedException() {
        super("Account cannot be deleted.");
    }
}
