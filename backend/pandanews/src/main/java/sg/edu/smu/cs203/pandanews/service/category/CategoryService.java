package sg.edu.smu.cs203.pandanews.service.category;

import sg.edu.smu.cs203.pandanews.model.category.Category;

import java.util.List;

public interface CategoryService {

    List<Category> listAllCategory();

    Category createCategory(Category c);

    Category updateCategory(Long id, Category c);

    void deleteCategory (Long id);

    Category getCategory (Long id);

}
