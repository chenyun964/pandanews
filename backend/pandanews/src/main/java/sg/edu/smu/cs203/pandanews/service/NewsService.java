package sg.edu.smu.cs203.pandanews.service;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs203.pandanews.model.news.News;

import java.util.ArrayList;
import java.util.List;

public interface NewsService {

    //TODO CRUD
    News createNewsByManual(News news);

    List<News> createNewsByAPI();

    News updateNews(int id, News news);

    void deleteNews(int id);

    List<News> findAllNews();

    List<News> findNewsByKeywords(String keyword);

    News findNewsById(int id);


}
