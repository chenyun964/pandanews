package sg.edu.smu.cs203.pandanews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import sg.edu.smu.cs203.pandanews.model.category.Category;
import sg.edu.smu.cs203.pandanews.service.category.CategoryServiceImpl;

@RestController
@CrossOrigin
public class CategoryController {
    @Autowired
    private CategoryServiceImpl categoryService;

    /**
     * Create new category
     * 
     * @param category
     * @return The created category
     */
    @PostMapping(path = "/category")
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    /**
     * Update existing category
     * 
     * @param id
     * @param newCategory
     * @return Updated category
     */
    @PutMapping(path = "/category/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable long id, @RequestBody Category newCategory) {
        Category c = categoryService.updateCategory(id, newCategory);
        return ResponseEntity.ok(c);
    }

    /**
     * List all category
     * 
     * @return list of category
     */
    @GetMapping(path = "/category")
    public ResponseEntity<?> listCategory() {
        return ResponseEntity.ok(categoryService.listAllCategory());
    }

    /**
     * Get category by id
     * 
     * @param id
     * @return category with given id
     */
    @GetMapping(path = "/category/{id}")
    public ResponseEntity<?> getCategory(@PathVariable long id) {
        Category category = categoryService.getCategory(id);
        if (category == null)
            return null;
        return ResponseEntity.ok(category);
    }

    /**
     * Delete existing category
     * 
     * @param id
     */
    @DeleteMapping(path = "/category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(null);
    }
}
