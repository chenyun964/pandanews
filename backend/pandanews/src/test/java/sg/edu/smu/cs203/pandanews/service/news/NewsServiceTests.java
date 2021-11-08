package sg.edu.smu.cs203.pandanews.service.news;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import io.swagger.models.auth.In;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import sg.edu.smu.cs203.pandanews.model.category.Category;
import sg.edu.smu.cs203.pandanews.model.news.News;
import sg.edu.smu.cs203.pandanews.repository.CategoryRepository;
import sg.edu.smu.cs203.pandanews.repository.NewsRepository;
import sg.edu.smu.cs203.pandanews.service.news.NewsServiceImpl;

import javax.swing.text.html.Option;

@ExtendWith(MockitoExtension.class)
public class NewsServiceTests {

    @Mock
    private NewsRepository newsRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private NewsServiceImpl newsService;


    @Test
    void createNewsByManual_ReturnSavedNews() {
        // arrange ** String title, String description, String content, String coverImage, Date date
        News news = newsFormatter("test");
        // mock the "findById" operation
        when(newsRepository.findByTitle(any(String.class))).thenReturn(new ArrayList<News>());
        // mock the "save" operation
        when(newsRepository.save(any(News.class))).thenReturn(news);
        // act ***
        News savedBook = newsService.createNewsByManual(news);
        // assert ***
        assertNotNull(savedBook);
        verify(newsRepository).findByTitle(savedBook.getTitle());
        verify(newsRepository).save(news);
    }


    @Test
    void createNewsByManual_ReturnNull() {
        // arrange *** String title, String description, String content, String coverImage, Date date
        News news = newsFormatter("test");
        List<News> someNews = new ArrayList<>();
        someNews.add(news);

        // mock the "findByTitle" operation
        when(newsRepository.findByTitle(news.getTitle())).thenReturn(someNews);
        // act ***
        News savedBook = newsService.createNewsByManual(news);
        // assert ***
        assertNull(savedBook);
        verify(newsRepository).findByTitle(news.getTitle());
    }

    @Test
    void updateNews_ReturnUpdatedBook() {
        final News news = newsFormatter("test");
        when(newsRepository.findById(any(Long.class))).thenReturn(Optional.of(news));
        News updateNews = newsFormatter("Updated");
        when(newsRepository.save(any(News.class))).thenReturn(updateNews);

        long id = 10L;
        News newNews = newsService.updateNews(id, updateNews);

        assertNotNull(newNews);
        assertEquals("Updated", newNews.getTitle());
        verify(newsRepository).findById(id);
        verify(newsRepository).save(news);
    }


    @Test
    void updateNews_ReturnNull() {
        News news = newsFormatter("test");
        Long newsId = 10L;
        when(newsRepository.findById(newsId)).thenReturn(Optional.empty());

        News newNews = newsService.updateNews(newsId, news);

        assertNull(newNews);
        verify(newsRepository).findById(newsId);
    }

    @Test
    void deleteNews_Success() {
        NewsServiceImpl mock = mock(NewsServiceImpl.class);
        doNothing().when(mock).deleteNews(isA(Long.class));
        mock.deleteNews(10L);
        verify(mock, times(1)).deleteNews(10L);
    }

    @Test
    void findAllNews_Success() {
        when(newsRepository.findAll()).thenReturn(new ArrayList<News>());
        List<News> list = newsService.findAllNews();
        assertNotNull(list);
        verify(newsRepository).findAll();
    }

    @Test
    void findNewsByKeywords_Success() {
        News n = newsFormatter("keyword");
        News n1 = newsFormatter("keyword2");
        List<News> list = new ArrayList<>();
        list.add(n);
        list.add(n1);
        when(newsRepository.findAllByKeyword("keyword")).thenReturn(list);
        List<News> result = newsService.findNewsByKeywords("keyword");
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(newsRepository).findAllByKeyword("keyword");
    }

    @Test
    void findNewsByCategory_Success() {
        Category c = new Category();
        List<Category> cList = new ArrayList<>();
        cList.add(c);
        News n = newsFormatter("test");
        List<News> nList = new ArrayList<>();
        nList.add(n);
        when(categoryRepository.findByTitle(any(String.class))).thenReturn(cList);
        when(newsRepository.findByCategory(any(Category.class))).thenReturn(nList);

        List<News> result = newsService.findNewsByCategory("keyword");
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(categoryRepository).findByTitle("keyword");
        verify(newsRepository).findByCategory(c);
    }

    @Test
    void findNewsByCategory_Failure() {
        when(categoryRepository.findByTitle(any(String.class))).thenReturn(new ArrayList<Category>());
        List<News> result = newsService.findNewsByCategory("keyword");
        assertNull(result);
        verify(categoryRepository).findByTitle("keyword");
    }

    @Test
    void findTop4NewsPast7Days_Success() {
        when(newsRepository.findByViewCountAndCreatedAtBetween()).thenReturn(new ArrayList<News>());
        List<News> list = newsService.findTop4NewsPast7Days();
        assertNotNull(list);
        verify(newsRepository).findByViewCountAndCreatedAtBetween();
    }

    @Test
    void findNewsById_Success() {
        News n = newsFormatter("Test");
        when(newsRepository.findById(any(Long.class))).thenReturn(Optional.of(n));

        News news = newsService.findNewsById(10L);

        assertNotNull(news);
        verify(newsRepository).findById(10L);
    }

    @Test
    void findNewsById_Failure() {
        when(newsRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        News n = newsService.findNewsById(10L);

        assertNull(n);
        verify(newsRepository).findById(10L);
    }

    @Test
    void updateNewsCategory_ReturnUpdatedBook() {
        News news = newsFormatter("test");
        News updateNews = newsFormatter("test");
        Category c = new Category();
        updateNews.setCategory(c);

        when(newsRepository.findById(any(Long.class))).thenReturn(Optional.of(news));
        when(newsRepository.save(any(News.class))).thenReturn(updateNews);

        long id = 10L;
        News newNews = newsService.updateNewsCategory(id, c);

        assertNotNull(newNews);
        assertEquals(c, newNews.getCategory());
        verify(newsRepository).findById(id);
        verify(newsRepository).save(news);
    }


    @Test
    void updateNewsCategory_ReturnNull() {
        News news = newsFormatter("test");
        Long newsId = 10L;
        when(newsRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        News newNews = newsService.updateNewsCategory(newsId, null);

        assertNull(newNews);
        verify(newsRepository).findById(newsId);
    }

    @Test
    void increaseViewCount_Success() {
        News news = newsFormatter("test");
        if (news != null) {
            news.setSlug("slug");
        }
        News updateNews = newsFormatter("test");
        if (updateNews != null) {
            updateNews.setSlug("slug");
            updateNews.incrementViewCount();
        }

        when(newsRepository.findBySlug(any(String.class))).thenReturn(news);
        when(newsRepository.save(any(News.class))).thenReturn(updateNews);

        News newNews = newsService.increaseViewCount(news.getSlug());

        assertNotNull(newNews);
        verify(newsRepository).findBySlug(news.getSlug());
        verify(newsRepository).save(news);

    }

    private static News newsFormatter(String title) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // arrange *** String title, String description, String content, String coverImage, Date date
        News news = null;
        try {
            news = new News(title, "123456", "1", "1", formatter.parse("2020-01-01"));
        } catch (Exception e) {
            return null;
        }
        return news;
    }

}