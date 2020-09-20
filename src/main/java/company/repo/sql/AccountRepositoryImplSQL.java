package company.repo.sql;

import company.model.Account;
import company.model.AccountStatus;
import company.repo.AccountRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class AccountRepositoryImplSQL implements AccountRepository {

    private DbConnection dbConnection =DbConnection.getInstance();
    private Account account;
    private List<Account> accountList;

    @Override
    public Account create(Account account) {
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO accounts (Account) VALUES (?)",PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, account.getAccountStatus().toString());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                account.setId(resultSet.getLong(1));
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return account;
    }

    @Override
    public Account update(Account account) {
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE accounts SET Account=? WHERE id=? ");
            statement.setString(1, account.getAccountStatus().toString());
            statement.setInt(2, (int) account.getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return account;
    }

    @Override
    public void delete(Long aLong) {
        try (Connection connection = DbConnection.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM  accounts  WHERE id=? ");
            statement.setInt(1, Math.toIntExact(aLong));
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Optional<Account> read(Long aLong) {
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT Account ,id FROM accounts WHERE id=?");
            statement.setInt(1, Math.toIntExact(aLong));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                account = new Account();
                account.setId(resultSet.getInt("id"));
                account.setAccountStatus(AccountStatus.valueOf(resultSet.getString("Account")));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.ofNullable(account);
    }

    @Override
    public Collection<Account> getAll() {
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT Account ,id FROM accounts ");
            ResultSet resultSet = statement.executeQuery();
            accountList = new ArrayList<>();
            while (resultSet.next()) {
                account = new Account();
                account.setId(resultSet.getInt("id"));
                account.setAccountStatus(AccountStatus.valueOf(resultSet.getString("Account")));
                accountList.add(account);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return accountList;
    }
}
