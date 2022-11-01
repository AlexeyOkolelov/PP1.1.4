package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;


public class Main {


    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        UserDao userDao = new UserDaoHibernateImpl();

        userDao.createUsersTable();

        userDao.saveUser("Ivan", "Ivanov", (byte) 24);
        userDao.saveUser("Petr", "Petrov", (byte) 25);
        userDao.saveUser("Alex", "Alexseev", (byte) 18);
        userDao.saveUser("name", "last Name", (byte) 30);

        List<User> list = userDao.getAllUsers();
        for (User user : list) {
            System.out.println(user.toString());
        }
        userDao.cleanUsersTable();
        userDao.dropUsersTable();

    }


}
