package sg.edu.smu.cs203.pandanews.service.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs203.pandanews.exception.news.NewsDuplicationException;
import sg.edu.smu.cs203.pandanews.model.category.Category;
import sg.edu.smu.cs203.pandanews.model.news.News;
import sg.edu.smu.cs203.pandanews.repository.CategoryRepository;
import sg.edu.smu.cs203.pandanews.repository.NewsRepository;
import sg.edu.smu.cs203.pandanews.dto.NewsDTO;

import java.util.List;
import java.util.Date;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsNewsAPIServiceImpl newsAPIService;

    @Autowired
    private NewsRepository newsRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Override
    public News createNewsByManual(NewsDTO newsDTO) {
        News news = new News();
        news.setTitle(newsDTO.getTitle());
        news.setDescription(newsDTO.getDescription());
        news.setContent(newsDTO.getContent());
        news.setSource("Manual");
        news.setCoverImage(newsDTO.getCoverImage());
        news.setCategory(categoryRepo.findById(newsDTO.getCategory()).orElse(null));
        news.setPinned(newsDTO.getPinned());
        news.setDate(new Date());
        return newsRepo.findByTitle(news.getTitle()).size() == 0 ? newsRepo.save(news) : null;
    }

    @Override
    public News createNewsByManualWithCategory(News news, Long categoryId) {
        news.setSource("Manual");
        news.setCategory(categoryRepo.findById(categoryId).orElse(null));
        return newsRepo.findByTitle(news.getTitle()).size() == 0 ? newsRepo.save(news) : null;
    }

    @Override
    public List<News> createNewsByAPI() {
        List<News> result = newsAPIService.apiCall();
        if (result == null){
            throw new NewsDuplicationException("News Duplicated");
        }
        return newsAPIService.apiCall().size() == 0 ? null : result;
    }

    @Override
    public News updateNews(long id, NewsDTO news) {
        return newsRepo.findById(id).map(newNews -> {
            newNews.setTitle(news.getTitle());
            newNews.setContent(news.getContent());
            newNews.setCoverImage(news.getCoverImage());
            newNews.setDate(news.getDate());
            newNews.setDescription(news.getDescription());
            return newsRepo.save(newNews);
        }).orElse(null);
    }

    @Override
    public void deleteNews(long id) {
        newsRepo.deleteById(id);
    }

    @Override
    public List<News> findAllNews() {
        return newsRepo.findAllByOrderByUpdatedAtDesc();
    }

    @Override
    public List<News> findNewsByKeywords(String keyword) {
        return newsRepo.findAllByKeyword(keyword);
    }

    @Override
    public List<News> findNewsByCategory(String s) {
        List<Category> c = categoryRepo.findByTitle(s);
        if (c.size() != 1) {
            return null;
        }
        return newsRepo.findByCategory(c.get(0));
    }

    @Override
    public News findNewsById(long id) {
        return newsRepo.findById(id).orElse(null);
    }

    @Override
    public News updateNewsCategory(long id, Category c) {
        return newsRepo.findById(id).map(newNews -> {
            newNews.setCategory(c);
            return newsRepo.save(newNews);
        }).orElse(null);
    }

    @Override
    public List<News> findTop4NewsPast7Days() {
        return newsRepo.findByViewCountAndCreatedAtBetween();
    }

    @Override
    public News increaseViewCount(String slug) {
        News n = newsRepo.findBySlug(slug);
        n.incrementViewCount();
        return newsRepo.save(n);

    }

}
