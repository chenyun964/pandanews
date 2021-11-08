package sg.edu.smu.cs203.pandanews.service.news;


import sg.edu.smu.cs203.pandanews.model.category.Category;
import sg.edu.smu.cs203.pandanews.model.news.News;

import java.util.List;

public interface NewsService {

    News createNewsByManual(News news);

    List<News> createNewsByAPI();

    News updateNews(long id, News news);

    void deleteNews(long id);

    List<News> findAllNews();

    List<News> findNewsByKeywords(String keyword);

    List<News> findNewsByCategory(String s);

    News findNewsById(long id);

    News updateNewsCategory(long id, Category c);

    List<News> findTop4NewsPast7Days();

    News increaseViewCount(String slug);


}
