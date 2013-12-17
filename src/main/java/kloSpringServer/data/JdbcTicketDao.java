package kloSpringServer.data;

import kloSpringServer.model.SupportTicket;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kala
 * Date: 9.10.2013
 * Time: 1:39
 */

@Repository
public class JdbcTicketDao implements TicketDao {
    private static final Logger logger = Logger.getLogger(JdbcTicketDao.class);
    private static final String TABLE_TICKET = "TICKET";
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public SupportTicket getTicketById(String ticketId) {
        String query = "SELECT * FROM " + TABLE_TICKET +
                " WHERE TICKET_NUMBER=? LIMIT 1;";

        RowMapper<SupportTicket> mapper = new RowMapper<SupportTicket>() {
            @Override
            public SupportTicket mapRow(ResultSet rs, int rowNum) throws SQLException {
                SupportTicket ticket = new SupportTicket();
                ticket.setTicketNumber(rs.getInt("TICKET_NUMBER"));
                ticket.setStatus(rs.getString("STATUS"));
                ticket.setMessage(rs.getString("MESSAGE"));
                ticket.setSubject(rs.getString("SUBJECT"));
                ticket.setCategory(rs.getString("CATEGORY"));
                ticket.setSenderName(rs.getString("SENDER_NAME"));
                ticket.setSenderEmail(rs.getString("SENDER_EMAIL"));
                ticket.setDate(rs.getDate("DATE"));
                ticket.setReply(rs.getString("REPLY_MESSAGE"));
                ticket.setReplyBy(rs.getString("REPLY_BY"));
                ticket.setReplyDate(rs.getDate("REPLY_DATE"));

                return ticket;
            }
        };
        List results = jdbcTemplate.query(query, mapper, ticketId);
        if (results.isEmpty()) {
            return null;
        } else {
            return (SupportTicket) results.get(0);
        }
    }

    @Override
    public void addTicket(SupportTicket ticket) {
        String sql = "INSERT INTO " + TABLE_TICKET
                + " (STATUS, CATEGORY, SUBJECT, SENDER_NAME, SENDER_EMAIL, MESSAGE, DATE)"
                + " VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP);";

        Object[] params = new Object[]{
                ticket.getStatus(),
                ticket.getCategory(),
                ticket.getSubject(),
                ticket.getSenderName(),
                ticket.getSenderEmail(),
                ticket.getMessage(),
        };
        try {
            jdbcTemplate.update(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SupportTicket> getLatestTickets(int count) {
        String sql = "SELECT * FROM " + TABLE_TICKET +
                " ORDER BY DATE DESC" +
                " LIMIT ?;";

        RowMapper<SupportTicket> mapper = new RowMapper<SupportTicket>() {
            @Override
            public SupportTicket mapRow(ResultSet rs, int rowNum) throws SQLException {
                SupportTicket ticket = new SupportTicket();
                ticket.setTicketNumber(rs.getInt("TICKET_NUMBER"));
                ticket.setStatus(rs.getString("STATUS"));
                ticket.setMessage(rs.getString("MESSAGE"));
                ticket.setSubject(rs.getString("SUBJECT"));
                ticket.setCategory(rs.getString("CATEGORY"));
                ticket.setSenderName(rs.getString("SENDER_NAME"));
                ticket.setSenderEmail(rs.getString("SENDER_EMAIL"));
                ticket.setDate(rs.getDate("DATE"));
                ticket.setReply(rs.getString("REPLY_MESSAGE"));
                ticket.setReplyBy(rs.getString("REPLY_BY"));
                ticket.setReplyDate(rs.getDate("REPLY_DATE"));

                return ticket;
            }
        };

        List<SupportTicket> tickets = jdbcTemplate.query(sql, mapper, count);
        if (tickets.isEmpty()) {
            return null;
        } else {
            return tickets;
        }
    }

    @Override
    public void updateTicket(SupportTicket ticket, int ticketId) {
        logger.info("DAO: Updating ticket " + ticketId);
        String sql = "UPDATE " + TABLE_TICKET +
                " SET REPLY_BY=:writer, REPLY_MESSAGE=:reply, REPLY_DATE=CURRENT_TIMESTAMP, STATUS=:status " +
                " WHERE TICKET_NUMBER=:id;";

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("writer", ticket.getReplyBy());
        arguments.put("reply", ticket.getReply());
        arguments.put("status", ticket.getStatus());
        arguments.put("id", ticket.getTicketNumber());

        for(Object o : arguments.values()) {
            logger.info(o.toString());
        }

        try {
            namedTemplate.update(sql, arguments);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
