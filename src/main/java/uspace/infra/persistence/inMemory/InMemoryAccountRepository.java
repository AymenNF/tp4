package uspace.infra.persistence.inMemory;

import uspace.domain.account.Account;
import uspace.domain.account.AccountRepository;
import uspace.domain.account.AccountUsername;

import java.util.HashMap;
import java.util.Map;

public class InMemoryAccountRepository implements AccountRepository {

    private static final Map<AccountUsername, Account> accounts = new HashMap<>();

    public InMemoryAccountRepository() {
    }

    @Override
    public Account findByUsername(AccountUsername username) {
        return accounts.get(username);
    }

    @Override
    public void save(Account account) {
        accounts.put(account.getUsername(), account);
    }

    @Override
    public void delete(Account account) {
        accounts.remove(account.getUsername());
    }
}
