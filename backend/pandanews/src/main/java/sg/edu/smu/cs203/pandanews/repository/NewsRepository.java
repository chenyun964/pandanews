package sg.edu.smu.cs203.pandanews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sg.edu.smu.cs203.pandanews.model.news.News;
import sg.edu.smu.cs203.pandanews.model.news.NewsListDAO;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    //SELECT * FROM employee WHERE name="employee name" OR location="location name";

    @Query("SELECT n FROM News n WHERE n.description LIKE %:keyword% OR n.title LIKE %:keyword%")
    List<News> findAllByKeyword(String keyword);

    @Query(value = "SELECT * FROM news n WHERE date <= DATE(NOW()) - INTERVAL 7 DAY AND cover_image IS NOT null ORDER BY n.view_Count DESC LIMIT 4;",
            nativeQuery = true)
    List<News> findByViewCountAndCreatedAtBetween();
}
