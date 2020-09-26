package company.view;

import company.controller.AccountController;
import company.model.Account;
import company.model.AccountStatus;
import company.repo.hibernate.AccountRepositoryImplHibernate;
import company.repo.io.AccountRepositoryImpl;
import company.repo.sql.AccountRepositoryImplSQL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class AccountView {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private AccountController accountController = new AccountController(new AccountRepositoryImplHibernate());

    private Account acc;

    public Account addAccount() {
        String read = "";
        while (true) {
            System.out.println("Write account status 1:Active ; 2:Banned ; 3:Delete");
            try {
                read = reader.readLine();
                if (read.equalsIgnoreCase("1")||read.equalsIgnoreCase("2")||read.equalsIgnoreCase("3")) {
                    switch (read) {
                        case "1":
                            return  accountController.addAccount(new Account(AccountStatus.ACTIVE));
                        case "2":
                            return accountController.addAccount(new Account(AccountStatus.BANNED));
                        case "3":
                            return   accountController.addAccount(new Account(AccountStatus.DELETED));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public Account createAccount(Account account){
        while (true) {
            System.out.println("Write account status 1:Active ; 2:Banned ; 3:Delete");
            String read = null;
            try {
                read = reader.readLine();
            } catch (IOException e) {
                System.out.println("Tray again ");
            }

            switch (read) {

                case "1":

                   acc= accountController.updateAccount(AccountStatus.ACTIVE,account.getId());

                    break;
                case "2":

                   acc= accountController.updateAccount(AccountStatus.BANNED,account.getId());
                    break;
                case "3":

                   acc= accountController.updateAccount(AccountStatus.DELETED,account.getId());
                    break;
            }
            break;
        }

        return acc;
    }

    public void deleteAccount(Account account) {
        accountController.deleteAccount(account.getId());
    }
}
