package sg.edu.smu.cs203.pandanews.service.category;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sg.edu.smu.cs203.pandanews.model.category.Category;
import sg.edu.smu.cs203.pandanews.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {

    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Mock
    private CategoryRepository categoryRepo;

    @Test
    void createCategory_Success() {
        Category p = new Category();
        when(categoryRepo.save(any(Category.class))).thenReturn(p);
        Category result = categoryService.createCategory(p);
        assertNotNull(result);
        verify(categoryRepo).save(p);
    }

    @Test
    void createCategory_Failure() {
        Category p = new Category();
        when(categoryRepo.save(any(Category.class))).thenReturn(null);
        Category result = categoryService.createCategory(p);
        assertNull(result);
        verify(categoryRepo).save(p);
    }

    @Test
    void getCategory_Success() {
        Category category = new Category();
        when(categoryRepo.findById(any(Long.class))).thenReturn(Optional.of(category));

        Category result = categoryService.getCategory(10L);
        assertNotNull(result);
        verify(categoryRepo).findById(10L);
    }

    @Test
    void getCategory_Failure() {
        when(categoryRepo.findById(any(Long.class))).thenReturn(Optional.empty());
        Category result = categoryService.getCategory(10L);
        assertNull(result);
        verify(categoryRepo).findById(10L);
    }

    @Test
    void deleteCategory_Success() {
        CategoryServiceImpl mock = mock(CategoryServiceImpl.class);
        doNothing().when(mock).deleteCategory(isA(Long.class));
        mock.deleteCategory(10L);
        verify(mock, times(1)).deleteCategory(10L);
    }

    @Test
    void updateCategory_ReturnUpdatedResult() {
        Category category = new Category();
        Category updatedCategory = new Category();

        updatedCategory.setTitle("updated");

        when(categoryRepo.findById(any(Long.class))).thenReturn(Optional.of(category));
        when(categoryRepo.save(any(Category.class))).thenReturn(updatedCategory);

        Category result = categoryService.updateCategory(10L, updatedCategory);

        assertNotNull(result);
        assertEquals("updated", result.getTitle());
        verify(categoryRepo).findById(10L);
        verify(categoryRepo).save(category);
    }

    @Test
    void updateCategory_ReturnNull() {
        when(categoryRepo.findById(any(Long.class))).thenReturn(Optional.empty());
        Category result = categoryService.updateCategory(10L, new Category());

        assertNull(result);
        verify(categoryRepo).findById(10L);
    }

    @Test
    void listAllCategory_Success() {
        List<Category> categoryArrayList = new ArrayList<>();
        Category s = new Category();
        categoryArrayList.add(s);
        when(categoryRepo.findAll()).thenReturn(categoryArrayList);
        List<Category> result = categoryService.listAllCategory();
        assertNotNull(result);
        verify(categoryRepo).findAll();
    }
}
