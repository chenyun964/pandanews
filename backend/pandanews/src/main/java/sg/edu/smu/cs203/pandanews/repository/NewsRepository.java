package sg.edu.smu.cs203.pandanews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sg.edu.smu.cs203.pandanews.model.news.News;

import java.util.List;


@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {

    //SELECT * FROM employee WHERE name="employee name" OR location="location name";

    @Query("SELECT n FROM News n WHERE n.description LIKE %:keyword% OR n.title LIKE %:keyword%")
    List<News> findAllByKeyword(String keyword);
}
