package uspace.application.account;

import jakarta.inject.Inject;
import uspace.application.account.dtos.AccountDto;
import uspace.domain.account.Account;
import uspace.domain.account.AccountRepository;
import uspace.domain.account.AccountUsername;
import uspace.domain.account.AccountDeletionValidator;
import uspace.domain.account.exceptions.UsernameAlreadyExistsException;
import uspace.domain.account.exceptions.AccountNotFoundException;

public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountAssembler accountAssembler;
    private final AccountDeletionValidator accountDeletionValidator;

    @Inject
    public AccountService(AccountRepository accountRepository, AccountAssembler accountAssembler, AccountDeletionValidator accountDeletionValidator) {
        this.accountRepository = accountRepository;
        this.accountAssembler = accountAssembler;
        this.accountDeletionValidator = accountDeletionValidator;
    }

    public void createAccount(AccountDto accountDto) {
        Account account = accountRepository.findByUsername(new AccountUsername(accountDto.username));
        if (account != null) {
            throw new UsernameAlreadyExistsException();
        }

        Account newAccount = accountAssembler.toAccount(accountDto);
        accountRepository.save(newAccount);
    }

    public AccountDto findAccount(String username) {
        Account account = accountRepository.findByUsername(new AccountUsername(username));
        if (account == null) {
            throw new AccountNotFoundException();
        }

        return accountAssembler.toDto(account);
    }

    public void deleteAccount(String username) {
        Account account = accountRepository.findByUsername(new AccountUsername(username));
        if (account == null) {
            throw new AccountNotFoundException();
        }

        accountDeletionValidator.validateDeletion(account);
        accountRepository.delete(account);
    }
}
