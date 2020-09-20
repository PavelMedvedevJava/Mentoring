package company.controller;

import company.model.Account;
import company.model.AccountStatus;

import company.repo.sql.AccountRepositoryImplSQL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {


@Mock
    AccountRepositoryImplSQL mockAccountRepositoryImplSQL;
@InjectMocks
    AccountController accountController = new AccountController(mockAccountRepositoryImplSQL);

    Account account ;


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addAccount() {
        account = new Account(AccountStatus.ACTIVE, 2);
        when(mockAccountRepositoryImplSQL.create(account)).thenReturn(account);
        Account account1 = accountController.addAccount(account);
       assertEquals (account,account1);
    }

    @Test
    public void deleteAccount() {
        accountController.deleteAccount(2);
        verify(mockAccountRepositoryImplSQL,atLeastOnce()).delete((long) 2);
    }

    @Test
    public void updateAccount() {
        account = new Account(AccountStatus.ACTIVE, 2);
        account.setAccountStatus(AccountStatus.BANNED);
        when(mockAccountRepositoryImplSQL.update(anyObject())).thenReturn(account);
        Account account1 = accountController.updateAccount(AccountStatus.BANNED,2);
        assertEquals (account,account1);

    }

    @Test
    public void getAccount() {
        account = new Account(AccountStatus.ACTIVE, 2);
        when(mockAccountRepositoryImplSQL.read(anyLong())).thenReturn(Optional.ofNullable(account));
        Account account1 = accountController.getAccount(2).get();
        assertEquals (account,account1);
    }
}