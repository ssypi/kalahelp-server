package kloSpringServer.data;

import kloSpringServer.model.User;

import java.util.List;

public interface UserDao {
    void saveUser(User user);
    User getUserByUsername(String username);
    byte[] getSaltForUser(String username);
    boolean verifyUser(String username, byte[] encryptedPassword);

    void saveUser(String username, byte[] encryptedPassword, byte[] salt);

    List<User> getUsers();

    void deleteUser(String username);

    void updateUser(String userName, byte[] encryptedPassword, byte[] salt);
//    public User getUserByName(String username);
}
