package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import java.util.LinkedList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    private SessionFactory factory = Util.getInstance().getSessionFactory();


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = factory.getCurrentSession()) {

            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (id  INT NOT NULL  AUTO_INCREMENT," +
                    "name VARCHAR(45) NOT NULL," +
                    "last_name VARCHAR(45) NOT NULL," +
                    "age INT NOT NULL," +
                    "PRIMARY KEY (id))").executeUpdate();

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;

        try (Session session = factory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = factory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            System.out.println("User с именем " + name + " добавлен в базу данных");

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException();
        }

    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = factory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException();
        }

    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> list = new LinkedList<>();

        try (Session session = factory.getCurrentSession()) {
            transaction = session.beginTransaction();
            list = session.createQuery("from User ").getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException() ;
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = factory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException();
        }

    }
}
