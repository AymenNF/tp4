package uspace.domain.account;

public interface AccountRepository {

    Account findByUsername(AccountUsername username);
    void save(Account account);
    void delete(Account account);
}
