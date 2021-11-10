package sg.edu.smu.cs203.pandanews.service.news;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
<<<<<<< Updated upstream
import java.text.SimpleDateFormat;
import java.util.*;
=======

import java.util.*;

>>>>>>> Stashed changes
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sg.edu.smu.cs203.pandanews.dto.NewsDTO;
import sg.edu.smu.cs203.pandanews.model.category.Category;
import sg.edu.smu.cs203.pandanews.model.news.News;
import sg.edu.smu.cs203.pandanews.repository.CategoryRepository;
import sg.edu.smu.cs203.pandanews.repository.NewsRepository;
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes

@ExtendWith(MockitoExtension.class)
public class NewsServiceTests {

    @Mock
    private NewsRepository newsRepo;
    @Mock
    private CategoryRepository categoryRepo;
    @InjectMocks
    private NewsServiceImpl newsService;


    @Test
    void createNewsByManual_ReturnSavedNews() {
        // arrange ** String title, String description, String content, String coverImage, Date date
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setTitle("title");
        News news = newsFormatter("title");

        // mock the "findById" operation
        when(newsRepo.findByTitle(any(String.class))).thenReturn(new ArrayList<News>());
        // mock the "save" operation
        when(newsRepo.save(any(News.class))).thenReturn(news);
        // act ***
        News savedNews = newsService.createNewsByManual(newsDTO);
        // assert ***
        assertNotNull(savedNews);
        verify(newsRepo).findByTitle(savedNews.getTitle());
    }


    @Test
    void createNewsByManual_ReturnNull() {
        // arrange *** String title, String description, String content, String coverImage, Date date
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setTitle("test");
        News news = newsFormatter("test");
        List<News> someNews = new ArrayList<>();
        someNews.add(news);

        // mock the "findByTitle" operation
        when(newsRepo.findByTitle(news.getTitle())).thenReturn(someNews);
        // act ***
        News savedNews = newsService.createNewsByManual(newsDTO);
        // assert ***
        assertNull(savedNews);
        verify(newsRepo).findByTitle(news.getTitle());
    }

    @Test
    void createNewsByManualWithCategory_ReturnSavedNews() {
        // arrange ** String title, String description, String content, String coverImage, Date date
        News news = newsFormatter("test");
        // mock the "findById" operation
        when(categoryRepo.findById(any(Long.class))).thenReturn(Optional.of(new Category("category")));
        when(newsRepo.findByTitle(any(String.class))).thenReturn(new ArrayList<News>());
        // mock the "save" operation
        when(newsRepo.save(any(News.class))).thenReturn(news);
        // act ***
        News savedNews = newsService.createNewsByManualWithCategory(news, 10L);
        // assert ***
        assertNotNull(savedNews);
        verify(newsRepo).findByTitle(savedNews.getTitle());
        verify(newsRepo).save(news);
        verify(categoryRepo).findById(10L);
        assertEquals("category", savedNews.getCategory().getTitle());
    }


    @Test
    void createNewsByManualWithCategory_ReturnNull() {
        // arrange *** String title, String description, String content, String coverImage, Date date
        News news = newsFormatter("test");
        List<News> someNews = new ArrayList<>();
        someNews.add(news);

        // mock the "findByTitle" operation
        when(newsRepo.findByTitle(news.getTitle())).thenReturn(someNews);
        // act ***
        News savedNews = newsService.createNewsByManualWithCategory(news, 10L);
        // assert ***
        assertNull(savedNews);
        verify(newsRepo).findByTitle(news.getTitle());
    }

    @Test
    void updateNews_ReturnUpdatedNews() {
        final News news = newsFormatter("test");
        when(newsRepo.findById(any(Long.class))).thenReturn(Optional.of(news));
        News updateNews = newsFormatter("Updated");
        when(newsRepo.save(any(News.class))).thenReturn(updateNews);

        long id = 10L;
        News newNews = newsService.updateNews(id, updateNews);

        assertNotNull(newNews);
        assertEquals("Updated", newNews.getTitle());
        verify(newsRepo).findById(id);
        verify(newsRepo).save(news);
    }


    @Test
    void updateNews_ReturnNull() {
        News news = newsFormatter("test");
        Long newsId = 10L;
        when(newsRepo.findById(newsId)).thenReturn(Optional.empty());

        News newNews = newsService.updateNews(newsId, news);

        assertNull(newNews);
        verify(newsRepo).findById(newsId);
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
        when(newsRepo.findAllByOrderByUpdatedAtDesc()).thenReturn(new ArrayList<News>());
        List<News> list = newsService.findAllNews();
        assertNotNull(list);
        verify(newsRepo).findAllByOrderByUpdatedAtDesc();
    }

    @Test
    void findNewsByKeywords_Success() {
        News n = newsFormatter("keyword");
        News n1 = newsFormatter("keyword2");
        List<News> list = new ArrayList<>();
        list.add(n);
        list.add(n1);
        when(newsRepo.findAllByKeyword("keyword")).thenReturn(list);
        List<News> result = newsService.findNewsByKeywords("keyword");
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(newsRepo).findAllByKeyword("keyword");
    }

    @Test
    void findNewsByCategory_Success() {
        Category c = new Category();
        List<Category> cList = new ArrayList<>();
        cList.add(c);
        News n = newsFormatter("test");
        List<News> nList = new ArrayList<>();
        nList.add(n);
        when(categoryRepo.findByTitle(any(String.class))).thenReturn(cList);
        when(newsRepo.findByCategory(any(Category.class))).thenReturn(nList);

        List<News> result = newsService.findNewsByCategory("keyword");
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(categoryRepo).findByTitle("keyword");
        verify(newsRepo).findByCategory(c);
    }

    @Test
    void findNewsByCategory_Failure() {
        when(categoryRepo.findByTitle(any(String.class))).thenReturn(new ArrayList<Category>());
        List<News> result = newsService.findNewsByCategory("keyword");
        assertNull(result);
        verify(categoryRepo).findByTitle("keyword");
    }

    @Test
    void findTop4NewsPast7Days_Success() {
        when(newsRepo.findByViewCountAndCreatedAtBetween()).thenReturn(new ArrayList<News>());
        List<News> list = newsService.findTop4NewsPast7Days();
        assertNotNull(list);
        verify(newsRepo).findByViewCountAndCreatedAtBetween();
    }

    @Test
    void findNewsById_Success() {
        News n = newsFormatter("Test");
        when(newsRepo.findById(any(Long.class))).thenReturn(Optional.of(n));

        News news = newsService.findNewsById(10L);

        assertNotNull(news);
        verify(newsRepo).findById(10L);
    }

    @Test
    void findNewsById_Failure() {
        when(newsRepo.findById(any(Long.class))).thenReturn(Optional.empty());
        News n = newsService.findNewsById(10L);

        assertNull(n);
        verify(newsRepo).findById(10L);
    }

    @Test
    void updateNewsCategory_ReturnUpdatedNews() {
        News news = newsFormatter("test");
        News updateNews = newsFormatter("test");
        Category c = new Category();
        updateNews.setCategory(c);

        when(newsRepo.findById(any(Long.class))).thenReturn(Optional.of(news));
        when(newsRepo.save(any(News.class))).thenReturn(updateNews);

        long id = 10L;
        News newNews = newsService.updateNewsCategory(id, c);

        assertNotNull(newNews);
        assertEquals(c, newNews.getCategory());
        verify(newsRepo).findById(id);
        verify(newsRepo).save(news);
    }


    @Test
    void updateNewsCategory_ReturnNull() {
        News news = newsFormatter("test");
        Long newsId = 10L;
        when(newsRepo.findById(any(Long.class))).thenReturn(Optional.empty());

        News newNews = newsService.updateNewsCategory(newsId, null);

        assertNull(newNews);
        verify(newsRepo).findById(newsId);
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

        when(newsRepo.findBySlug(any(String.class))).thenReturn(news);
        when(newsRepo.save(any(News.class))).thenReturn(updateNews);

        News newNews = newsService.increaseViewCount(news.getSlug());

        assertNotNull(newNews);
        verify(newsRepo).findBySlug(news.getSlug());
        verify(newsRepo).save(news);

    }

    private static News newsFormatter(String title) {
        News news = null;
        try {
            news = new News(title, "123456", "1", "1", new Date());
        } catch (Exception e) {
            return null;
        }
        return news;
    }

}