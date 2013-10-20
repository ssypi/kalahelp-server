package kloSpringServer.data;

import kloSpringServer.model.SupportTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kala
 * Date: 9.10.2013
 * Time: 1:39
 */
@Component
public class JdbcTicketDao implements TicketDao {
    private static final String TABLE_TICKET = "TICKET";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
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
                ticket.setDate(rs.getString("DATE"));
                ticket.setReply(rs.getString("REPLY_MESSAGE"));
                ticket.setReplyBy(rs.getString("REPLY_BY"));
                ticket.setReplyDate(rs.getString("REPLY_DATE"));

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
                + " VALUES (null, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP);";

        Object[] params = new Object[]{ticket.getStatus(),
                ticket.getCategory(),
                ticket.getSubject(),
                ticket.getSenderName(),
                ticket.getSenderEmail(),
                ticket.getMessage()
        };
        jdbcTemplate.update(sql, params);
    }
}
