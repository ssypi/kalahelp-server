package kloSpringServer.data;

import kloSpringServer.model.ReplyTemplate;
import kloSpringServer.model.SupportTicket;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Evige
 * Date: 17.12.2013
 * Time: 19:36
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class JdbcTemplateDao implements TemplateDao {
    private static final Logger logger = Logger.getLogger(JdbcTemplateDao.class);
    private static final String TABLE_TEMPLATE = "TEMPLATE";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void addTemplate(ReplyTemplate template) {
        logger.info("Saving new template: " + template);
        String sql = "INSERT INTO " + TABLE_TEMPLATE
                + " (NAME, BEGINNING, END, CREATED_BY)"
                + " VALUES(?, ?, ?, ?);";

        Object[] params = new Object[]{
                template.getName(),
                template.getBeginningPart(),
                template.getEndPart(),
                template.getCreator()
        };

        jdbcTemplate.update(sql, params);

    }

    @Override
    public void deleteTemplate(String templateName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ReplyTemplate> getTemplates() {
        String sql = "SELECT * FROM " + TABLE_TEMPLATE +
                " ORDER BY NAME ASC ";

        RowMapper<ReplyTemplate> mapper = new RowMapper<ReplyTemplate>() {
            @Override
            public ReplyTemplate mapRow(ResultSet rs, int rowNum) throws SQLException {
                ReplyTemplate template = new ReplyTemplate();
                template.setName(rs.getString("NAME"));
                template.setBeginningPart(rs.getString("BEGINNING"));
                template.setEndPart(rs.getString("END"));
                template.setCreator(rs.getString("CREATED_BY"));

                return template;
            }
        };
        List<ReplyTemplate> templateList = jdbcTemplate.query(sql, mapper);
        return templateList;
    }
}
