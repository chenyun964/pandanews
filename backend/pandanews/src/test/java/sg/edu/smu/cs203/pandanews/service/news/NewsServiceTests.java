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


    @Test
    void createNewsByManual_ReturnSavedNews() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // arrange ***
        //String title, String description, String content, String coverImage, Date date
        News news = null;
        try {
            news = new News("123456", "123456", "1", "1", formatter.parse("2020-01-01"));
        } catch (Exception e) {
            return;
        }
        // mock the "findById" operation
        when(newsRepository.findById(any(Long.class))).thenReturn(Optional.of(new News()));
        // mock the "save" operation
        when(newsRepository.save(any(News.class))).thenReturn(news);
        System.out.println(news.getContent());
        // act ***
        News savedBook = newsService.createNewsByManual(news);

        // assert ***
        assertNotNull(savedBook);
        verify(newsRepository).findById(savedBook.getId());
        verify(newsRepository).save(news);
    }
}