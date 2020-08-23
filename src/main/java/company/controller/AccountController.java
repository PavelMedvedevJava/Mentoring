package company.controller;

import company.model.Account;
import company.model.AccountStatus;
import company.repo.AccountRepository;
import company.repo.io.AccountRepositoryImpl;
import company.repo.io.CSV.AccountRepositoryImplCSV;

import java.util.Optional;

public class AccountController {

    private AccountRepository accountRepository =new AccountRepositoryImplCSV() ;


    public Account addAccount(Account account) {

        return accountRepository.create(account);
    }

    public void deleteAccount(long id) {
        accountRepository.delete(id);
    }

    public Account updateAccount(AccountStatus accountStatus  , long id) {
        return  accountRepository.update(new Account(accountStatus,id));
    }

    public Optional<Account> getAccount(long id ) {
        return accountRepository.read(id);
    }
}
