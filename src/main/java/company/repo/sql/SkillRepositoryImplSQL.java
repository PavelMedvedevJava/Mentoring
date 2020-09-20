package company.repo.sql;

import company.model.Skill;
import company.repo.SkillRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class SkillRepositoryImplSQL implements SkillRepository {
    private DbConnection dbConnection = DbConnection.getInstance();
    private Skill skill;
    private List<Skill> skillList;

    @Override
    public Skill create(Skill skill) {
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO skills (skill) VALUES (?)",PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, skill.getSkill());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                skill.setId(resultSet.getLong(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return skill;
    }

    @Override
    public Skill update(Skill skill) {
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE skills SET skill=? WHERE id=? ");
            statement.setString(1, skill.getSkill());
            statement.setInt(2, (int) skill.getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return skill;
    }

    @Override
    public void delete(Long aLong) {
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM  skills  WHERE id=? ");
            statement.setLong(1, aLong);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public Optional<Skill> read(Long aLong) {
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT skill ,id FROM skills WHERE id=?");
            statement.setInt(1, Math.toIntExact(aLong));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                skill = new Skill();
                skill.setId(resultSet.getInt("id"));
                skill.setSkill(resultSet.getString("skill"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.ofNullable(skill);
    }

    @Override
    public Collection<Skill> getAll() {
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT skill ,id FROM skills ");
            ResultSet resultSet = statement.executeQuery();
            skillList = new ArrayList<>();
            while (resultSet.next()) {
                skill = new Skill();
                skill.setId(resultSet.getInt("id"));
                skill.setSkill(resultSet.getString("skill"));
                skillList.add(skill);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return skillList;
    }
}
