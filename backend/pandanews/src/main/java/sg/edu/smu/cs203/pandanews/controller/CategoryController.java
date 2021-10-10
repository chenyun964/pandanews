package sg.edu.smu.cs203.pandanews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.smu.cs203.pandanews.model.category.Category;
import sg.edu.smu.cs203.pandanews.service.Category.CategoryServiceImpl;

@RestController
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @PostMapping(path = "/category/create")
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.createCategory(category));
    }


    @PostMapping(path = "/category/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable long id, @RequestBody Category newCategory) {
        Category c = categoryService.updateCategory(id, newCategory);
        return ResponseEntity.ok(c);
    }

    @GetMapping(path = "/category/list")
    public ResponseEntity<?> listCategory() {
        return ResponseEntity.ok(categoryService.listAllCategory());
    }

    @DeleteMapping(path = "/category/delete")
    public ResponseEntity<?> deleteCategory(@PathVariable long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(null);
    }
}
