package kloSpringServer.model;

import kloSpringServer.data.JdbcSessionDao;
import kloSpringServer.data.SessionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: kala
 * Date: 20.10.2013
 * Time: 1:18
 */
@Service
public class SessionHelper {

//    @Autowired
    private SessionDao sessionDao = new JdbcSessionDao();

    public boolean isExpired(Session session) {
        Date currentTime = new Date();
        return session.getExpireDate().compareTo(currentTime) >= 0;
    }

    public boolean verifySession(Session session) {
        Session actualSession = sessionDao.getSessionByToken(session.getToken());
        return session.equals(actualSession) && !isExpired(session);
    }

    public void saveSession(Session session) {
        sessionDao.saveSession(session);
    }

    public Session createSessionForIp(String ip) {
        Session session = new Session();
        session.setIpAddress(ip);
        session.setToken(generateToken());
        return session;
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }
}
