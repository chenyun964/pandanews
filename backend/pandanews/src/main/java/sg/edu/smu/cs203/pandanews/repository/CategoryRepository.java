package sg.edu.smu.cs203.pandanews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.smu.cs203.pandanews.model.category.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
