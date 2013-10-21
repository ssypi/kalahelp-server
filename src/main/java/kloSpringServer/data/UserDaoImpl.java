package kloSpringServer.data;

import kloSpringServer.model.User;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: kala
 * Date: 20.10.2013
 * Time: 22:31
 */
@Repository
public class UserDaoImpl implements UserDao {
    @Override
    public void saveUser(User user) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public User getUserByUsername(String username) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public byte[] getSaltForUser(String username) {
        return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean verifyUser(String username, byte[] encryptedPassword) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
