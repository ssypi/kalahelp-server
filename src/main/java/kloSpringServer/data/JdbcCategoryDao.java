package kloSpringServer.data;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Joona
 * Date: 1.12.2013
 * Time: 0:01
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class JdbcCategoryDao implements CategoryDao {
    private static final Logger logger = Logger.getLogger(JdbcCategoryDao.class);
    private String TABLE_CATEGORY = "category";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveCategory(String category) {
        logger.info("Saving new category: " + category);
        String sql = "INSERT INTO " + TABLE_CATEGORY
                + " VALUES(?);";
        jdbcTemplate.update(sql, category);
    }

    @Override
    public void deleteCategory(String category) {
        String sql = "DELETE FROM " + TABLE_CATEGORY + " WHERE NAME = ? ";
        jdbcTemplate.update(sql, category);
    }

    @Override
    public List<String> getCategories() {
        String sql = "SELECT * FROM " + TABLE_CATEGORY;
        List<String> categories = jdbcTemplate.queryForList(sql, String.class);
//        RowMapper<Object> rowMapper;

        return categories;
    }

}