package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    public UserDaoJDBCImpl() {
    }
    private Connection connection = getConnection();

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS User (id BIGINT(255) PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(255) NOT NULL," +
                "lastName VARCHAR(255) NOT NULL," +
                "age TINYINT(255))";
//         String sql = "CREATE TABLE IF NOT EXISTS User \n" +
//                "(\n" +
//                "    id LONGBLOB PRIMARY KEY AUTO_INCREMENT,\n" +
//                "    name VARCHAR(255) NOT NULL,\n" +
//                "    lastName VARCHAR(255) NOT NULL,\n" +
//                "    age TINYINT \n" +
//                ");";
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(sql);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS User;";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        String sql = "INSERT INTO User (name, lastName, age) VALUES (?,?,?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getLastName());
            statement.setByte(3, user.getAge());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM User WHERE id=?;";
        User user = new User();
        try (Statement statement = connection.createStatement()) {
             statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "Select id, name, lastName, age;";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setName(resultSet.getString("name"));
                user.setAge(resultSet.getByte("age"));

                list.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM User;";
        User user = new User();
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
