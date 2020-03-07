package com.beerent.shopifyapi.database.users;

import com.beerent.shopifyapi.model.users.UserModel;

import java.util.List;

public class UserService {

    private static UserDao userDao;

    public UserService() {
        userDao = new UserDao();
    }

    public void persist(List<UserModel> users) {
        userDao.openCurrentSessionwithTransaction();
        for (int i = 0; i < users.size(); i++) {
            UserModel user = users.get(i);
            UserModel existingUser = userDao.findByEmail(user.getEmail());
            if  (existingUser != null) {
                users.set(i, existingUser);
                continue;
            }

            userDao.persist(user);
        }

        userDao.closeCurrentSessionwithTransaction();
    }

    public void persist(UserModel user) {
        userDao.openCurrentSessionwithTransaction();
        userDao.persist(user);
        userDao.closeCurrentSessionwithTransaction();
    }

    public void update(UserModel user) {
        userDao.openCurrentSessionwithTransaction();
        userDao.update(user);
        userDao.closeCurrentSessionwithTransaction();
    }

    public UserModel findById(String id) {
        userDao.openCurrentSession();
        UserModel user = userDao.findById(id);
        userDao.closeCurrentSession();

        return user;
    }

    public List<UserModel> findAll() {
        userDao.openCurrentSession();
        List<UserModel> users = userDao.findAll();
        userDao.closeCurrentSession();

        return users;
    }

    public void delete(String id) {
        userDao.openCurrentSessionwithTransaction();
        UserModel user = userDao.findById(id);
        userDao.delete(user);
        userDao.closeCurrentSessionwithTransaction();
    }

    public void deleteAll() {
        userDao.openCurrentSessionwithTransaction();
        userDao.deleteAll();
        userDao.closeCurrentSessionwithTransaction();
    }
}
