package kloSpringServer.service;

import kloSpringServer.data.SessionDao;
import kloSpringServer.model.Session;
import kloSpringServer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Autowired
    private SessionDao sessionDao;

    public SessionHelper() {
    }

    public boolean isExpired(Session session) {
        Date currentTime = new Date();
        return session.getExpireDate().before(currentTime);
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
        Date date = new Date();
        long hours = 5 * 3600000L;
        date.setTime(date.getTime() + hours);
        session.setExpireDate(date);
        return session;
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }
}
