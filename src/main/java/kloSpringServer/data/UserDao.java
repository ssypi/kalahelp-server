package kloSpringServer.data;

import kloSpringServer.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: kala
 * Date: 20.10.2013
 * Time: 21:38
 */
public interface UserDao {
    void saveUser(User user);
    User getUserByUsername(String username);
    byte[] getSaltForUser(String username);
    boolean verifyUser(String username, byte[] encryptedPassword);
//    public User getUserByName(String username);
}
