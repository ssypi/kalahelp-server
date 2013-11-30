package kloSpringServer.data;

import kloSpringServer.controller.ControllerTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Joona
 * Date: 1.12.2013
 * Time: 0:10
 * To change this template use File | Settings | File Templates.
 */
public class JdbcCategoryDaoTest extends ControllerTest {
    @Autowired
    public CategoryDao categoryDao;

    @Test
    public void testAddCategory() throws Exception {
        categoryDao.saveCategory("category");
    }

    @Test
    public void testGetCategories() throws Exception {
        List<String> categories = categoryDao.getCategories();
        assertFalse(categories.isEmpty());
        assertEquals("kalamiehen gatogory", categories.get(0));

    }

    @Test
    public void testDeleteCategory() throws Exception {
        categoryDao.deleteCategory("kalamiehen gatogory");

    }
}
