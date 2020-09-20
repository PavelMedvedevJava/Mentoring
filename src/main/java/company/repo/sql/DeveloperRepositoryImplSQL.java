package company.repo.sql;


import company.model.Account;
import company.model.AccountStatus;
import company.model.Developer;
import company.model.Skill;
import company.repo.DeveloperRepository;

import java.sql.*;
import java.util.*;

public class DeveloperRepositoryImplSQL implements DeveloperRepository {
    private DbConnection dbConnection = DbConnection.getInstance();
    private Developer developer;
    private List<Developer> developerList=new ArrayList<>();
    private Set<Developer> developerSet = new HashSet<>();

    Account account;

    @Override
    public Developer create(Developer developer) {
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement=
                    connection.prepareStatement("INSERT INTO  developers (name, lastName) VALUES (?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,developer.getName());
            preparedStatement.setString(2, developer.getLastName());
            preparedStatement.executeUpdate();
            ResultSet resultSet=preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                developer.setId(resultSet.getInt(1));
            }


            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            String sqlQueryAccount = ("UPDATE accounts SET id_for_developer=" + developer.getId() + " WHERE id=" + developer.getAccount().getId());
            statement.addBatch(sqlQueryAccount);

            developer.getSkills().forEach(x-> {
                try {
                    statement.
                            addBatch("UPDATE skills SET id_for_developer=" + developer.getId() + " WHERE id=" + x.getId());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            statement.executeBatch();
            connection.commit();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return developer;
    }

    @Override
    public Developer update(Developer developer) {
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE developers SET name=?,lastName=? WHERE id=? ");
            statement.setString(1,developer.getName());
            statement.setString(2,developer.getLastName());
            statement.setInt(3, (int) developer.getId());
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return developer;
    }

    @Override
    public void delete(Long aLong) {
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM  developers  WHERE id=? ");
            statement.setInt(1, Math.toIntExact(aLong));
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public Optional<Developer> read(Long aLong) {
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM developers LEFT JOIN accounts a on developers.id = a.id_for_developer" +
                            " LEFT JOIN skills s on developers.id = s.id_for_developer WHERE developers.id=? ");
            statement.setInt(1, Math.toIntExact(aLong));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                developer = new Developer();
                developer.setName(resultSet.getString("name"));
                developer.setLastName(resultSet.getString("lastName"));
                developer.setId(resultSet.getLong("id"));
                developer.setAccount(new Account(AccountStatus.valueOf(resultSet.getString("Account")),resultSet.getLong("a.id")));

                developer.setSkill(new Skill(resultSet.getString("skill"),resultSet.getLong("s.id")));

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.ofNullable(developer);
    }

    @Override
    public Collection<Developer> getAll() {
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM developers  JOIN accounts a on developers.id = a.id_for_developer" +
                            " LEFT JOIN skills s on developers.id = s.id_for_developer");

            ResultSet resultSet = statement.executeQuery();
            List<Developer> devList = new ArrayList<>();

            while (resultSet.next()) {
                developer = new Developer();
                developer.setName(resultSet.getString("name"));
                developer.setLastName(resultSet.getString("lastName"));
                developer.setId(resultSet.getLong("id"));
                developer.setAccount(new Account(AccountStatus.valueOf(resultSet.getString("Account")), resultSet.getLong("a.id")));
                developer.setSkill(new Skill(resultSet.getString("skill"), resultSet.getLong("s.id")));
                devList.add(developer);
            }

            for (Developer d:devList) {
                developer = new Developer();
                for (Developer d2:devList) {
                    if (d.getId()== d2.getId()) {
                        d.setSkill( d2.getSkills().stream().findFirst().get());
                    }


                }
                developerSet.add(d);

            }



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        developerList.addAll(developerSet);
        return developerList;
    }
}
