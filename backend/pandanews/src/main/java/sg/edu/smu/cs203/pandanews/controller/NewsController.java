package sg.edu.smu.cs203.pandanews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.smu.cs203.pandanews.exception.news.NewsDuplicationException;
import sg.edu.smu.cs203.pandanews.exception.news.NewsNotFoundException;
import sg.edu.smu.cs203.pandanews.model.news.News;
import sg.edu.smu.cs203.pandanews.service.news.NewsServiceImpl;

import java.util.List;

@RestController
public class NewsController {

    @Autowired
    private NewsServiceImpl newsService;


    /**
     * Create news by manual input
     *
     * @param news
     * @return created news
     */
    @PostMapping(path = "/news")
    public ResponseEntity<?> createNewsByManual(@RequestBody News news) {
        News n = newsService.createNewsByManual(news);
        if (n == null) {
            throw new NewsDuplicationException("News Duplicated");
        }
        return ResponseEntity.ok(n);
    }

    /**
     * Create news by external api
     *
     * @return all news created by api
     */
    @PostMapping(path = "/news/api")
    public ResponseEntity<?> createNewsByAPI() {
        return ResponseEntity.ok(newsService.createNewsByAPI());
    }


    /**
     * update news information
     *
     * @param id
     * @param newNews
     * @return update news
     */
    @PostMapping(path = "/news/{id}")
    public ResponseEntity<?> updateNews(@PathVariable int id, @RequestBody News newNews) {
        News news = newsService.updateNews(id, newNews);
        if (news == null) {
            throw new NewsNotFoundException("News Not Found");
        }
        return ResponseEntity.ok(news);
    }

    @PostMapping(path = "/news/view/{slug}")
    public ResponseEntity<?> updateViewCount(@PathVariable String slug) {
        News news = newsService.increaseViewCount(slug);
        if (news == null) {
            throw new NewsNotFoundException("News Not Found");
        }
        return ResponseEntity.ok(news);
    }

    /**
     * list all news
     *
     * @return news list
     */
    @GetMapping(path = "/news")
    public ResponseEntity<?> listNews() {
        return ResponseEntity.ok(newsService.findAllNews());
    }

    /**
     * find all news by given keywords
     *
     * @param keyword
     * @return news with current keyword included
     */
    @GetMapping(path = "/news/keyword/{keyword}")
    public ResponseEntity<?> findNewsByKeyword(@PathVariable String keyword) {
        List<News> list = newsService.findNewsByKeywords(keyword);
        if (list.size() == 0)
            throw new NewsNotFoundException("News Not Found");
        return ResponseEntity.ok(list);
    }

    /**
     * find news by a id
     *
     * @param id
     * @return news with particular id
     */
    @GetMapping(path = "/news/{id}")
    public ResponseEntity<?> findNewsById(@PathVariable int id) {
        News n = newsService.findNewsById(id);
        if (n == null) {
            throw new NewsNotFoundException("News Not Found");
        }
        return ResponseEntity.ok(n);
    }


    /**
     * find news with top viewing count within past 7 days
     *
     * @return list of news with top viewing count within past 7 days
     */
    @GetMapping(path = "/news/top4news")
    public ResponseEntity<?> findTop4NewsPast7Days() {
        return ResponseEntity.ok(newsService.findTop4NewsPast7Days());
    }

    /**
     * find all news by given category
     *
     * @param category
     * @return news under particular category
     */
    @GetMapping(path = "/news/category/{category}")
    public ResponseEntity<?> findNewsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(newsService.findNewsByCategory(category));
    }

    /**
     * delete particular news
     *
     * @param id
     * @return null
     */
    @DeleteMapping(path = "/news/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable long id) {
        newsService.deleteNews(id);
        return ResponseEntity.ok(null);
    }
}
