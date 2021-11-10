package sg.edu.smu.cs203.pandanews.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs203.pandanews.model.category.Category;
import sg.edu.smu.cs203.pandanews.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepo;

    @Override
    public List<Category> listAllCategory() {
        return categoryRepo.findAll();
    }

    @Override
    public Category createCategory(Category c) {
        return categoryRepo.save(c);
    }

    @Override
    public Category updateCategory(Long id, Category c) {
        return categoryRepo.findById(id).map(newCategory -> {
                    newCategory.setTitle(c.getTitle());
                    newCategory.setNewsList(c.getNewsList());
                    return categoryRepo.save(newCategory);
                }
        ).orElse(null);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepo.deleteById(id);
    }

    @Override
    public Category getCategory(Long id){
        return categoryRepo.findById(id).map(category -> category).orElse(null);
    }
}
