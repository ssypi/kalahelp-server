package kloSpringServer.data;

import kloSpringServer.controller.ControllerTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

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
