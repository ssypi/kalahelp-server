package kloSpringServer.data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Joona
 * Date: 30.11.2013
 * Time: 23:56
 * To change this template use File | Settings | File Templates.
 */
public interface CategoryDao {

    void saveCategory(String category);

    void deleteCategory(String category);

    List<String> getCategories();

}
