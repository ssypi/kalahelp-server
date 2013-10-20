package kloSpringServer.data;

import kloSpringServer.model.Session;
import kloSpringServer.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: kala
 * Date: 20.10.2013
 * Time: 0:14
 */
public interface SessionDao {
    public Session getSessionByToken(String token);
    public Session saveSession(Session session);
    public void deleteSession(Session session);
}
