package kloSpringServer.data;

import kloSpringServer.model.Session;

public interface SessionDao {
    public Session getSessionByToken(String token);
    public Session saveSession(Session session);
    public void deleteSession(Session session);
    Session createNewSessionForIp(String ip);

    boolean verifySession(Session session);
}
