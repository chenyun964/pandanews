package sg.edu.smu.cs203.pandanews.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs203.pandanews.model.category.Category;
import sg.edu.smu.cs203.pandanews.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> listAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(Category c) {
        return categoryRepository.save(c);
    }

    @Override
    public Category updateCategory(Long id, Category c) {
        return categoryRepository.findById(id).map(newCategory -> {
                    newCategory.setTitle(c.getTitle());
                    newCategory.setNewsList(c.getNewsList());
                    return categoryRepository.save(newCategory);
                }
        ).orElse(null);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
