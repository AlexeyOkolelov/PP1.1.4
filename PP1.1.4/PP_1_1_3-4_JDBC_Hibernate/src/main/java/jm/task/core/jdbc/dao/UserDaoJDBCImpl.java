package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;


public class UserDaoJDBCImpl implements UserDao {


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS users (id  INT NOT NULL  AUTO_INCREMENT," +
                "name VARCHAR(45) NOT NULL," +
                "last_name VARCHAR(45) NOT NULL," +
                "age INT NOT NULL," +
                "PRIMARY KEY (id))")) {

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() {

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE IF EXISTS users")) {
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO  users (name, last_name, age) VALUE (?,?,?)")) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();

            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users where id = ?")) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {
        List<User> list = new LinkedList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from users");
             ResultSet resultSet = preparedStatement.executeQuery()){

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("last_name");
                byte age = resultSet.getByte("age");

                User user = new User(id, name, lastName, age);
                list.add(user);

            }

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }

    public void cleanUsersTable() {

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("TRUNCATE TABLE users")) {
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
