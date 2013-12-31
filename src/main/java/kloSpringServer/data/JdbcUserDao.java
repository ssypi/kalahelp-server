package kloSpringServer.data;

import kloSpringServer.model.User;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Repository
public class JdbcUserDao implements UserDao {
    private static final String TABLE_USER = "USER";
    private JdbcTemplate jdbcTemplate;

    private static final Logger logger = Logger.getLogger(JdbcUserDao.class);

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveUser(String username, byte[] encryptedPassword, byte[] salt) {
        logger.info("Saving new user: " + username);
        String sql = "INSERT INTO " + TABLE_USER
                + " VALUES(?, ?, ?);";
        jdbcTemplate.update(sql, username, encryptedPassword, salt);
    }

    @Override
    public List<User> getUsers() {
        String sql = "SELECT * FROM " + TABLE_USER + ";";
        RowMapper<User> mapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setUsername(rs.getString("USERNAME"));

                return user;
            }
        };

        List<User> results = jdbcTemplate.query(sql, mapper);
        if (results.isEmpty()) {
            return null;
        } else {
            return results;
        }


    }

    @Override
    public void deleteUser(String username) throws IllegalStateException {
        try {
            username = URLDecoder.decode(username, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // TODO: xxx
        String countSql = "SELECT COUNT(*) FROM " + TABLE_USER + ";";
        int count = jdbcTemplate.queryForObject(countSql, Integer.class);
        if (count == 1) {
            throw new IllegalStateException();
        }

        logger.info("deleting user: " + username);
        String sql = "DELETE FROM " + TABLE_USER +
                " WHERE USERNAME=?;";
        jdbcTemplate.update(sql, username);
    }


    @Override
    public void updateUser(String username, byte[] encryptedPassword, byte[] salt) {
        logger.info("DAO: Updating user " + username);
        String sql = "UPDATE " + TABLE_USER +
                " SET PASSWORD=?, SALT=?" +
                " WHERE USERNAME=?";

        jdbcTemplate.update(sql, encryptedPassword, salt, username);
    }

    @Override
    public void saveUser(User user) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public User getUserByUsername(String username) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Nullable
    public byte[] getSaltForUser(String username) {
        String sql = "SELECT SALT FROM " + TABLE_USER
                + " WHERE USERNAME = ? LIMIT 1;";
        byte[] salt;

        try {
            salt = jdbcTemplate.queryForObject(sql, byte[].class, username);
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.info("User not found: " + username);
            salt = null;
        }
        return salt;
    }

    @Override
    public boolean verifyUser(String username, byte[] encryptedPassword) {
        String sql = "SELECT PASSWORD FROM " + TABLE_USER
                + " WHERE USERNAME =? LIMIT 1;";

        byte[] password = null;
        try {
            password = jdbcTemplate.queryForObject(sql, byte[].class, username);
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.info("User not found: " + username);
        }
        return Arrays.equals(password, encryptedPassword);
    }
}
