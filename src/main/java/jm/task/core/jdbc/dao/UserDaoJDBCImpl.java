package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.connection;

public class UserDaoJDBCImpl implements UserDao {
    public static final String CREATE_TABLE = """
                CREATE TABLE IF NOT EXISTS users (
                id INT NOT NULL AUTO_INCREMENT,
                name VARCHAR(63),
                lastName VARCHAR(63),
                age TINYINT UNSIGNED,
                PRIMARY KEY (id));
            """;
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS users;";
    public static final String SAVE_USER = "INSERT INTO users(name, lastName, age) VALUES (?, ?, ?);";
    public static final String REMOVE_USER = "DELETE FROM users WHERE id = ?;";
    public static final String GET_ALL = "SELECT * FROM users;";
    public static final String CLEAN_TABLE = "DELETE FROM users WHERE id > 0;";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(CREATE_TABLE);
        } catch (SQLException e1) {
            e1.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(DROP_TABLE);
        } catch (SQLException e1) {
            e1.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = connection.prepareStatement(SAVE_USER)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.executeUpdate();
            System.out.printf("User с именем – %s добавлен в базу данных%n", name);
        } catch (SQLException e1) {
            e1.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement statement = connection.prepareStatement(REMOVE_USER)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery(GET_ALL);
            while (set.next()) {
                User user = new User(set.getString("name"), set.getString("lastName"), set.getByte("age"));
                user.setId(set.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(CLEAN_TABLE);
        } catch (SQLException e1) {
            e1.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
    }
}
