package sg.edu.smu.cs203.pandanews.service.news;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import io.swagger.models.auth.In;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sg.edu.smu.cs203.pandanews.model.news.News;
import sg.edu.smu.cs203.pandanews.repository.NewsRepository;
import sg.edu.smu.cs203.pandanews.service.news.NewsServiceImpl;

@ExtendWith(MockitoExtension.class)
public class NewsServiceTests {

    @Mock
    private NewsRepository newsRepository;

    @InjectMocks
    private NewsServiceImpl newsService;


//    @Test
//    void createNewsByManual_ReturnSavedNews() {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        // arrange ** String title, String description, String content, String coverImage, Date date
//        News news = null;
//        try {
//            news = new News("123456", "123456", "1", "1", formatter.parse("2020-01-01"));
//        } catch (Exception e) {
//            return;
//        }
//        // mock the "findById" operation
//        when(newsRepository.findByTitle(any(String.class))).thenReturn(new ArrayList<News>());
//        // mock the "save" operation
//        when(newsRepository.save(any(News.class))).thenReturn(news);
//        // act ***
//        News savedBook = newsService.createNewsByManual(news);
//        // assert ***
//        assertNotNull(savedBook);
//        verify(newsRepository).findByTitle(savedBook.getTitle());
//        verify(newsRepository).save(news);
//    }
//
//
    @Test
    void createNewsByManual_ReturnNull() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // arrange *** String title, String description, String content, String coverImage, Date date
        News news = null;
        try {
            news = new News("exists", "123456", "1", "1", formatter.parse("2020-01-01"));
        } catch (Exception e) {
            return;
        }
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

//     @Test
//     void updateNews_ReturnUpdatedBook(){
//         SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//         // arrange *** String title, String description, String content, String coverImage, Date date
//         News news = null;
//         News oldNews = null;
//         try {
//             news = new News("updated", "123456", "1", "1", formatter.parse("2020-01-01"));
//         } catch (ParseException e) {
//             return;
//         }
//         try {
//              oldNews = new News(1L, "Not updated", "123456", "1", "1", formatter.parse("2020-01-01"));
//         } catch (ParseException e) {
//             return;
//         }
//         long newsId = 1L;
//
//         when(newsRepository.findById(any(long.class))).thenReturn(Optional.of(oldNews));
//
//         when(newsRepository.save(any(News.class))).thenReturn(news);
//
//         News newNews = newsService.updateNews(newsId, news);
//
//         assertNotNull(newNews);
//         verify(newsRepository).findById(newsId);
//         verify(newsRepository).save(news);
//     }

    @Test
    void updateNews_ReturnNull(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // arrange *** String title, String description, String content, String coverImage, Date date
        News news = null;
        try {
            news = new News("updated", "123456", "1", "1", formatter.parse("2020-01-01"));
        } catch (Exception e) {
            return;
        }
        Long newsId = 10L;
        when(newsRepository.findById(newsId)).thenReturn(Optional.empty());

        News newNews = newsService.updateNews(newsId, news);

        assertNull(newNews);
        verify(newsRepository).findById(newsId);
    }


}