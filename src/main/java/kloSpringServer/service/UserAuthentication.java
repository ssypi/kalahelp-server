package kloSpringServer.service;

import kloSpringServer.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: kala
 * Date: 20.10.2013
 * Time: 21:38
 */
@Service
public class UserAuthentication {
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncryption encrypter;

    public boolean verifyUserAndPass(String username, String password) {
        byte[] salt = userDao.getSaltForUser(username);
        if (salt == null) {
            return false;
        }
        byte[] encryptedPassword = encrypter.encryptPassword(password, salt);
        return userDao.verifyUser(username, encryptedPassword);
    }

}
