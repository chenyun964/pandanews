package sg.edu.smu.cs203.pandanews.service.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs203.pandanews.model.category.Category;
import sg.edu.smu.cs203.pandanews.model.news.News;
import sg.edu.smu.cs203.pandanews.repository.CategoryRepository;
import sg.edu.smu.cs203.pandanews.repository.NewsRepository;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsNewsAPIServiceImpl newsAPIServiceImpl;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public News createNewsByManual(News news) {
        news.setSource("Manual");
        return newsRepository.findByTitle(news.getTitle()).size() == 0 ? newsRepository.save(news) : null;
    }

    @Override
    public News createNewsByManualWithCategory(News news, Long categoryId) {
        news.setSource("Manual");
        news.setCategory(categoryRepository.findById(categoryId).orElse(null));
        return newsRepository.findByTitle(news.getTitle()).size() == 0 ? newsRepository.save(news) : null;
    }

    @Override
    public List<News> createNewsByAPI() {
        List<News> result = newsAPIServiceImpl.apiCall();
        return newsAPIServiceImpl.apiCall().size() == 0 ? null : result;
    }

    @Override
    public News updateNews(long id, News news) {
        return newsRepository.findById(id).map(newNews -> {
            newNews.setTitle(news.getTitle());
            newNews.setContent(news.getContent());
            newNews.setCoverImage(news.getCoverImage());
            newNews.setDate(news.getDate());
            newNews.setDescription(news.getDescription());
            newNews.setPinned(news.isPinned());
            return newsRepository.save(newNews);
        }).orElse(null);
    }

    @Override
    public void deleteNews(long id) {
        newsRepository.deleteById(id);
    }

    @Override
    public List<News> findAllNews() {
        return newsRepository.findAllByOrderByUpdatedAtDesc();
    }

    @Override
    public List<News> findNewsByKeywords(String keyword) {
        return newsRepository.findAllByKeyword(keyword);
    }

    @Override
    public List<News> findNewsByCategory(String s) {
        List<Category> c = categoryRepository.findByTitle(s);
        if (c.size() != 1) {
            return null;
        }
        return newsRepository.findByCategory(c.get(0));
    }

    @Override
    public News findNewsById(long id) {
        return newsRepository.findById(id).orElse(null);
    }

    @Override
    public News updateNewsCategory(long id, Category c) {
        return newsRepository.findById(id).map(newNews -> {
            newNews.setCategory(c);
            return newsRepository.save(newNews);
        }).orElse(null);
    }

    @Override
    public List<News> findTop4NewsPast7Days() {
        return newsRepository.findByViewCountAndCreatedAtBetween();
    }

    @Override
    public News increaseViewCount(String slug) {
        News n = newsRepository.findBySlug(slug);
        n.incrementViewCount();
        return newsRepository.save(n);

    }

}
