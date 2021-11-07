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

    @PostMapping(path = "/news")
    public ResponseEntity<?> createNewsByManual(@RequestBody News news) {
        News n = newsService.createNewsByManual(news);
        if (n == null) {
            throw new NewsDuplicationException("News Duplicated");
        }
        return ResponseEntity.ok(n);
    }

    @PostMapping(path = "/news/api")
    public ResponseEntity<?> createNewsByAPI() {
        return ResponseEntity.ok(newsService.createNewsByAPI());
    }

    @PostMapping(path = "/news/{id}")
    public ResponseEntity<?> updateNews(@PathVariable int id, @RequestBody News newNews) {
        News news = newsService.updateNews(id, newNews);
        if (news == null) {
            throw new NewsNotFoundException("News Not Found");
        }
        return ResponseEntity.ok(news);
    }

    @GetMapping(path = "/news")
    public ResponseEntity<?> listNews() {
        return ResponseEntity.ok(newsService.findAllNews());
    }

    @GetMapping(path = "/news/keyword/{keyword}")
    public ResponseEntity<?> findNewsByKeyword(@PathVariable String keyword) {
        List<News> list = newsService.findNewsByKeywords(keyword);
        if (list.size() == 0)
            throw new NewsNotFoundException("News Not Found");
        return ResponseEntity.ok(list);
    }

    @GetMapping(path = "/news/id/{id}")
    public ResponseEntity<?> findNewsById(@PathVariable int id) {
        News n = newsService.findNewsById(id);
        if (n == null) {
            throw new NewsNotFoundException("News Not Found");
        }
        return ResponseEntity.ok(n);
    }

    @GetMapping(path = "/news/top4news")
    public ResponseEntity<?> findTop4NewsPast7Days() {
        return ResponseEntity.ok(newsService.findTop4NewsPast7Days());
    }

    @GetMapping(path = "/news/category/{category}")
    public ResponseEntity<?> findNewsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(newsService.findNewsByCategory(category));
    }

    @DeleteMapping(path = "/news/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable long id) {
        newsService.deleteNews(id);
        return ResponseEntity.ok(null);
    }
}
