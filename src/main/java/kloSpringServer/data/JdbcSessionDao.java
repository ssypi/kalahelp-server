package kloSpringServer.data;

import kloSpringServer.model.Session;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcSessionDao implements SessionDao {
    private static final String TABLE_SESSION = "SESSION";
    private static final Logger logger = Logger.getLogger(JdbcSessionDao.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    @Nullable
    public Session getSessionByToken(String token) {
        String sql = "SELECT * FROM " + TABLE_SESSION
                + " WHERE TOKEN=? LIMIT 1";

        RowMapper<Session> mapper = new RowMapper<Session>() {
            @Override
            public Session mapRow(ResultSet rs, int rowNum) throws SQLException {
                Session session = new Session();
                session.setToken(rs.getString("TOKEN"));
                session.setIpAddress(rs.getString("IP_ADDRESS"));
                session.setExpireDate(rs.getTimestamp("EXPIRE_DATE"));
                return session;
            }
        };

        try {
            return jdbcTemplate.queryForObject(sql, mapper, token);
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.warn("Session not found for token: " + token);
        }
        return null;
    }

    @Override
    public Session saveSession(Session session) {
        String sql = "INSERT INTO " + TABLE_SESSION
                + " VALUES(?, ?, ?);";

        jdbcTemplate.update(sql, session.getToken(), session.getIpAddress(), session.getExpireDate());
        return session;
    }

    @Override
    public boolean verifySession(Session session) {
        if (session != null && session.validate()) {
            Session actualSession = getSessionByToken(session.getToken());
            return actualSession != null && session.equals(actualSession) && !actualSession.isExpired();
        } else {
            return false;
        }
    }

    @Override
    public void deleteSession(Session session) {
        String sql = "DELETE FROM " + TABLE_SESSION
                + " WHERE TOKEN=? AND IP_ADDRESS=?;";

        jdbcTemplate.update(sql, session.getToken(), session.getIpAddress());
    }

    /**
     * Creates a new session for the specified IP address
     * and saves it to database.
     *
     * @param ip ipAddress as String
     * @return returns the created Session object
     *
     * @see kloSpringServer.data.JdbcSessionDao#saveSession(kloSpringServer.model.Session session)
     */
    @Override
    public Session createNewSessionForIp(String ip) {
        Session session = new Session();
        session.setIpAddress(ip);
        session.setToken(Session.generateToken());
        session.setExpireDate(Session.newExpireDate());
        saveSession(session);
        return session;
    }
}
