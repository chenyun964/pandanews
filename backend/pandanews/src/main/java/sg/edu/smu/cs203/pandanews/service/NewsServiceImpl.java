package sg.edu.smu.cs203.pandanews.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs203.pandanews.model.news.News;
import sg.edu.smu.cs203.pandanews.repository.NewsRepository;

import java.util.ArrayList;
import java.util.List;


@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsNewsAPIServiceImpl newsAPIServiceImpl;

    @Autowired
    private NewsRepository newsRepository;


    @Override
    public News createNewsByManual(News news) {
        return newsRepository.save(news);
    }

    @Override
    public List<News> createNewsByAPI() {
        return newsAPIServiceImpl.apiCall();
    }

    @Override
    public News updateNews(int id, News news) {
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
    public void deleteNews(int id) {
        newsRepository.deleteById(id);
    }

    @Override
    public List<News> findAllNews() {
        return newsRepository.findAll();
    }

    @Override
    public List<News> findNewsByKeywords(String keyword) {
        return newsRepository.findAllByKeyword(keyword);
    }

    @Override
    public News findNewsById(int id) {
        return newsRepository.findById(id).orElse(null);
    }
}
