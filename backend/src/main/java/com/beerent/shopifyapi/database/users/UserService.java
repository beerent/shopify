package com.beerent.shopifyapi.database.users;

import com.beerent.shopifyapi.model.users.User;

import java.util.List;

public class UserService {

    private static UserDao userDao;

    public UserService() {
        userDao = new UserDao();
    }

    public void persist(List<User> users) {
        userDao.openCurrentSessionwithTransaction();
        for (User user : users) {
            userDao.persist(user);
        }
        userDao.closeCurrentSessionwithTransaction();
    }

    public void persist(User user) {
        userDao.openCurrentSessionwithTransaction();
        userDao.persist(user);
        userDao.closeCurrentSessionwithTransaction();
    }

    public void update(User user) {
        userDao.openCurrentSessionwithTransaction();
        userDao.update(user);
        userDao.closeCurrentSessionwithTransaction();
    }

    public User findById(String id) {
        userDao.openCurrentSession();
        User user = userDao.findById(id);
        userDao.closeCurrentSession();

        return user;
    }

    public List<User> findAll() {
        userDao.openCurrentSession();
        List<User> users = userDao.findAll();
        userDao.closeCurrentSession();

        return users;
    }

    public void delete(String id) {
        userDao.openCurrentSessionwithTransaction();
        User user = userDao.findById(id);
        userDao.delete(user);
        userDao.closeCurrentSessionwithTransaction();
    }

    public void deleteAll() {
        userDao.openCurrentSessionwithTransaction();
        userDao.deleteAll();
        userDao.closeCurrentSessionwithTransaction();
    }
}
