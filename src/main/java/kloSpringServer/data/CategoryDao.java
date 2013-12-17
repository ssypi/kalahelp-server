package kloSpringServer.data;

import java.util.List;

public interface CategoryDao {

    void saveCategory(String category);

    void deleteCategory(String category);

    List<String> getCategories();

}
