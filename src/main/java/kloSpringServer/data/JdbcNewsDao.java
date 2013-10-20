package kloSpringServer.data;

import kloSpringServer.model.NewsItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kala
 * Date: 10.10.2013
 * Time: 0:37
 */
@Component
public class JdbcNewsDao implements NewsDao {
    private static final String TABLE_NEWS = "NEWS";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public NewsItem getNewsById(int newsId) {
        String sql = "SELECT * FROM " + TABLE_NEWS
                + " WHERE ID=? LIMIT 1;";

        RowMapper<NewsItem> mapper = new RowMapper<NewsItem>() {
            @Override
            public NewsItem mapRow(ResultSet rs, int rowNum) throws SQLException {
                NewsItem newsItem = new NewsItem();
                newsItem.setId(rs.getInt("ID"));
                newsItem.setContent(rs.getString("CONTENT"));
                newsItem.setDate(rs.getTimestamp("DATE"));
                newsItem.setWriter(rs.getInt("WRITTEN_BY"));
                return newsItem;
            }
        };

        List<NewsItem> results = jdbcTemplate.query(sql, mapper, newsId);
        if (results.isEmpty()) {
            return null;
        } else {
            return results.get(0);
        }
    }

    @Override
    public List<NewsItem> getLatestNews(int count) {
        String sql = "SELECT * FROM " + TABLE_NEWS
                + " ORDER BY DATE DESC"
                + " LIMIT ?;";

        RowMapper<NewsItem> mapper = new RowMapper<NewsItem>() {
            @Override
            public NewsItem mapRow(ResultSet rs, int rowNum) throws SQLException {
                NewsItem newsItem = new NewsItem();
                newsItem.setId(rs.getInt("ID"));
                newsItem.setContent(rs.getString("CONTENT"));
                newsItem.setDate(rs.getTimestamp("DATE"));
                newsItem.setWriter(rs.getInt("WRITTEN_BY"));
                return newsItem;
            }
        };

        List<NewsItem> results = jdbcTemplate.query(sql, mapper, count);
        if (results.isEmpty()) {
            return null;
        } else {
            return results;
        }
    }

    @Override
    @Transactional
    public NewsItem addNews(NewsItem newsItem) {
        String sql = "INSERT INTO " + TABLE_NEWS
                + " VALUES(null, ?, ?, CURRENT_TIMESTAMP);";
        jdbcTemplate.update(sql, newsItem.getContent(), newsItem.getWriter());
        int id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        newsItem.setId(id);
        return newsItem;
    }
}
