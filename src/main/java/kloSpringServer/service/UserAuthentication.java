package kloSpringServer.service;

import kloSpringServer.data.UserDao;
import kloSpringServer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthentication {
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncryption encryption;

    public boolean verifyUser(User user) {
        if (user == null) return false;
        return verifyUser(user.getUsername(), user.getPassword());
    }

    public boolean verifyUser(String username, String password) {
        byte[] salt = userDao.getSaltForUser(username);
        if (salt == null) {
            return false;
        }
        byte[] encryptedPassword = encryption.encryptPassword(password, salt);
        return userDao.verifyUser(username, encryptedPassword);
    }

    public void createUser(User user) {
        createUser(user.getUsername(), user.getPassword());
    }

    public void createUser(String username, String password) {
        byte[] salt = encryption.createSalt();
        byte[] encryptedPassword = encryption.encryptPassword(password, salt);
        userDao.saveUser(username, encryptedPassword, salt);
    }

    public void updateUser(String username, String password) {
        byte[] salt = encryption.createSalt();
        byte[] encryptedPassword = encryption.encryptPassword(password, salt);
        userDao.updateUser(username, encryptedPassword, salt);
    }
}
