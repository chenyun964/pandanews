package sg.edu.smu.cs203.pandanews.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sg.edu.smu.cs203.pandanews.model.news.News;
import sg.edu.smu.cs203.pandanews.service.NewsServiceImpl;

@RestController
public class NewsController {

    @Autowired
    private NewsServiceImpl newsService;

    @PostMapping(path = "news/create")
    public ResponseEntity<?> createNewsByManual(@RequestBody News news) {
        return ResponseEntity.ok(newsService.createNewsByManual(news));
    }

    @PostMapping(path = "news/api")
    public ResponseEntity<?> createNewsByAPI() {
        return ResponseEntity.ok(newsService.createNewsByAPI());
    }

    @PostMapping(path = "news/update/{id}")
    public ResponseEntity<?> updateNews(@PathVariable int id, @RequestBody News newNews) {
        News news = newsService.updateNews(id, newNews);
        return ResponseEntity.ok(news);
    }

    @GetMapping(path = "news/list")
    public ResponseEntity<?> listNews() {
        return ResponseEntity.ok(newsService.findAllNews());
    }

    @GetMapping(path = "news/find/keyword/{keyword}")
    public ResponseEntity<?> findNewsByKeyword(@PathVariable String keyword) {
        return ResponseEntity.ok(newsService.findNewsByKeywords(keyword));
    }

    @GetMapping(path = "news/find/id/{id}")
    public ResponseEntity<?> findNewsById(@PathVariable int id) {
        return ResponseEntity.ok(newsService.findNewsById(id));
    }
}