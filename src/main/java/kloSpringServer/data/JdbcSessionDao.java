package kloSpringServer.data;

import kloSpringServer.model.Session;
import kloSpringServer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: kala
 * Date: 20.10.2013
 * Time: 0:18
 */
@Repository
public class JdbcSessionDao implements SessionDao {
    private static final String TABLE_SESSION = "SESSION";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
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
        return jdbcTemplate.queryForObject(sql, mapper, Session.class);
    }

    @Override
    public Session saveSession(Session session) {
        String sql = "INSERT INTO " + TABLE_SESSION
                + " VALUES(?, ?, CURRENT_TIMESTAMP);";

        System.out.println(session.getIpAddress());
        System.out.println(session.getToken());
        System.out.println(session.getExpireDate());

        jdbcTemplate.update(sql, session.getToken(), session.getIpAddress());
        return session;
    }

    @Override
    public void deleteSession(Session session) {
        String sql = "DELETE FROM " + TABLE_SESSION
                + " WHERE TOKEN=? AND IP_ADDRESS=?;";

        jdbcTemplate.update(sql, session.getToken(), session.getIpAddress());
    }


}
