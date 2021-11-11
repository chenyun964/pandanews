package sg.edu.smu.cs203.pandanews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.smu.cs203.pandanews.dto.NewsDTO;
import sg.edu.smu.cs203.pandanews.exception.NewsDuplicationException;
import sg.edu.smu.cs203.pandanews.exception.NewsNotFoundException;
import sg.edu.smu.cs203.pandanews.model.news.News;
import sg.edu.smu.cs203.pandanews.service.news.NewsServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@CrossOrigin
public class NewsController {

    @Autowired
    private NewsServiceImpl newsService;

    /**
     * Create news by manual input
     *
     * @param newsDTO with news and category id
     * @return
     */
    @PostMapping(path = "/news")
    public News createNewsByManual(@RequestBody NewsDTO newsDTO) {
        News n = newsService.createNewsByManual(newsDTO);
        if (n == null) {
            throw new NewsDuplicationException("News Duplicated");
        }
        return n;
    }

    /**
     * Create news by external api
     *
     * @return all news created by api
     */
    @PostMapping(path = "/news/api")
    public List<News> createNewsByAPI() {
        return newsService.createNewsByAPI();
    }

    /**
     * update news information
     *
     * @param id
     * @param newNews
     * @return update news
     */
    @PostMapping(path = "/news/{id}")
    public News updateNews(@PathVariable int id, @RequestBody NewsDTO newNews) {
        News news = newsService.updateNews(id, newNews);
        if (news == null) {
            throw new NewsNotFoundException();
        }
        return news;
    }

    @PostMapping(path = "/news/view/{slug}")
    public News updateViewCount(@PathVariable String slug) {
        News news = newsService.increaseViewCount(slug);
        if (news == null) {
            throw new NewsNotFoundException();
        }
        return news;
    }

    /**
     * list all news
     *
     * @return news list
     */
    @GetMapping(path = "/news")
    public List<News> listNews() {
        return newsService.findAllNews();
    }

    /**
     * find all news by given keywords
     *
     * @param keyword
     * @return news with current keyword included
     */
    @GetMapping(path = "/news/keyword/{keyword}")
    public List<News> findNewsByKeyword(@PathVariable String keyword) {
        List<News> list = newsService.findNewsByKeywords(keyword);
        if (list.size() == 0)
            throw new NewsNotFoundException();
        return list;
    }


    /**
     * find news by a id
     *
     * @param id
     * @return news with particular id
     */
    @GetMapping(path = "/news/{id}")
    public News findNewsById(@PathVariable int id) {
        News n = newsService.findNewsById(id);
        if (n == null) {
            throw new NewsNotFoundException();
        }
        return n;
    }

    /**
     * find news by a slug
     *
     * @param slug
     * @returnnews with particular slug
     */
    @GetMapping(path = "/news/slug/{slug}")
    public News findNewsBySlug(@PathVariable String slug) {
        News n = newsService.findBySlug(slug);
        if (n == null) {
            throw new NewsNotFoundException();
        }
        return n;
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
    public List<News> findNewsByCategory(@PathVariable String category) {
        List<News> newsList = newsService.findNewsByCategory(category);
        if (newsList == null) {
            throw new NewsNotFoundException();
        }
        return newsList;
    }

    /**
     * delete particular news
     *
     * @param id
     * @return null
     */
    @DeleteMapping(path = "/news/{id}")
    public void deleteNews(@PathVariable long id) {
        newsService.deleteNews(id);
    }
}
