package sg.edu.smu.cs203.pandanews.service.news;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sg.edu.smu.cs203.pandanews.model.category.Category;
import sg.edu.smu.cs203.pandanews.model.news.News;
import sg.edu.smu.cs203.pandanews.repository.CategoryRepository;
import sg.edu.smu.cs203.pandanews.repository.NewsRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class NewsIntegrationTests {

    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:";

    @Autowired
    /**
     * Use TestRestTemplate for testing a real instance of your application as an external actor.
     * TestRestTemplate is just a convenient subclass of RestTemplate that is suitable for integration tests.
     * It is fault tolerant, and optionally can carry Basic authentication headers.
     */
    private TestRestTemplate restTemplate;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    NewsServiceImpl newsService;

    @AfterEach
    void tearDown() {
        // clear the database after each test
        newsRepository.deleteAll();
        categoryRepository.deleteAll();

    }

    //Test pass
//    @Test
//    public void listNews_Success() throws Exception {
//
//        URI uri = new URI(baseUrl + port + "/news/list");
//        newsRepository.save(NewsIntegrationTests.newsFormatter());
//
//        // Need to use array with a ResponseEntity here
//        ResponseEntity<News[]> result = restTemplate.getForEntity(uri, News[].class);
//        News[] newsList = result.getBody();
//
//        assertEquals(200, result.getStatusCode().value());
//        assertEquals(1, newsList.length);
//    }
//
//    @Test
//    public void findNewsByCategory_Success() throws Exception {
//        News n = NewsIntegrationTests.newsFormatter();
//        Category c = categoryRepository.save(new Category("business"));
//        n.setCategory(c);
//        newsRepository.save(n);
//        // Need to use array with a ResponseEntity here
//        URI uri = new URI(baseUrl + port + "/news/find/category/" + c.getTitle());
//        ResponseEntity<News[]> result = restTemplate.getForEntity(uri, News[].class);
//        News[] newsList = result.getBody();
//
//        assertEquals(200, result.getStatusCode().value());
//        assertEquals(1, newsList.length);
//    }
//
//    @Test
//    public void findNewsByCategory_Failure() throws Exception {
//        URI uri = new URI(baseUrl + port + "/news/find/category/" + "ANY");
//        ResponseEntity<News[]> result = restTemplate.getForEntity(uri, News[].class);
//
//        assertEquals(200, result.getStatusCode().value());
//    }
//
//    @Test
//    public void deleteNews_Success() throws Exception {
//        News n = NewsIntegrationTests.newsFormatter();
//        long id = newsRepository.save(n).getId();
//        // Need to use array with a ResponseEntity here
//        URI uri = new URI(baseUrl + port + "/news/delete/" + id);
//        restTemplate.delete(uri);
//        assertEquals(Optional.empty(),newsRepository.findById(id));
//    }
//
//    //Test pass
//    @Test
//    public void getNewsById_Success() throws Exception {
//
//        Long id = newsRepository.save(NewsIntegrationTests.newsFormatter()).getId();
//        URI uri = new URI(baseUrl + port + "/news/find/id/" + id);
//
//        // Need to use array with a ResponseEntity here
//        ResponseEntity<News> result = restTemplate.getForEntity(uri, News.class);
//        News news = result.getBody();
//
//        assertEquals(200, result.getStatusCode().value());
//        assertEquals(news.getTitle(), result.getBody().getTitle());
//    }
//
//    //Test pass
//    @Test
//    public void getNewsById_Failure() throws Exception {
//
//        URI uri = new URI(baseUrl + port + "/news/find/id/1");
//
//        // Need to use array with a ResponseEntity here
//        ResponseEntity<News> result = restTemplate.getForEntity(uri, News.class);
//
//        assertEquals(404, result.getStatusCode().value());
//    }
//
//    //Test pass
//    @Test
//    public void addNewsByManual_Success() throws Exception {
//        News n = NewsIntegrationTests.newsFormatter();
//        URI uri = new URI(baseUrl + port + "/news/create");
//
//        // Need to use array with a ResponseEntity here
//        ResponseEntity<News> result = restTemplate.postForEntity(uri, n, News.class);
//        News news = result.getBody();
//
//        assertEquals(200, result.getStatusCode().value());
//        assertEquals(news.getTitle(), result.getBody().getTitle());
//    }
//
//    //Test pass
//    @Test
//    public void addNewsByManual_Failure_DuplicationOfTitle() throws Exception {
//
//        News n = NewsIntegrationTests.newsFormatter();
//        URI uri = new URI(baseUrl + port + "/news/create");
//        // Need to use array with a ResponseEntity here
//        restTemplate.postForEntity(uri, n, News.class);
//        ResponseEntity<News> result = restTemplate.postForEntity(uri, n, News.class);
//        assertEquals(400, result.getStatusCode().value());
//    }
//
//    //Test pass
//    @Test
//    public void updateNews_Success() throws Exception {
//        News n = NewsIntegrationTests.newsFormatter();
//        URI uri1 = new URI(baseUrl + port + "/news/create");
//
//        // Need to use array with a ResponseEntity here
//        ResponseEntity<News> result1 = restTemplate.postForEntity(uri1, n, News.class);
//        News news = result1.getBody();
//        long id = news.getId();
//        n.setTitle("Updated News");
//        URI uri2 = new URI(baseUrl + port + "/news/update/" + id);
//        ResponseEntity<News> result2 = restTemplate.postForEntity(uri2, n, News.class);
//
//        assertEquals(200, result1.getStatusCode().value());
//        assertEquals(200, result2.getStatusCode().value());
//        assertEquals("Updated News", result2.getBody().getTitle());
//    }
//
//    //Test pass
//    @Test
//    public void updateNews_Failure_NotFound() throws Exception {
//
//        News n = NewsIntegrationTests.newsFormatter();
//        URI uri = new URI(baseUrl + port + "/news/update/" + 10L);
//        ResponseEntity<News> result = restTemplate.postForEntity(uri, n, News.class);
//
//        assertEquals(404, result.getStatusCode().value());
//    }
//
//    @Test
//    public void findNewsByKeyword_Success() throws Exception {
//        News n = NewsIntegrationTests.newsFormatter();
//        String title = newsRepository.save(n).getTitle();
//        News n1 = NewsIntegrationTests.newsFormatter();
//        n1.setTitle(title + "1");
//        newsRepository.save(n1);
//        News n2 = NewsIntegrationTests.newsFormatter();
//        n2.setTitle(title + "2");
//        newsRepository.save(n2);
//        URI uri = new URI(baseUrl + port + "/news/find/keyword/" + title);
//
//        // Need to use array with a ResponseEntity here
//        ResponseEntity<News[]> result = restTemplate.getForEntity(uri, News[].class);
//        News[] news = result.getBody();
//
//        assertEquals(200, result.getStatusCode().value());
//        assertEquals(3, news.length);
//    }
//
//    @Test
//    public void findNewsByKeyword_Failure() throws Exception {
//        URI uri = new URI(baseUrl + port + "/news/find/keyword/" + "ABCD");
//
//        // Need to use array with a ResponseEntity here
//        ResponseEntity<Object> result = restTemplate.getForEntity(uri, Object.class);
//
//        assertEquals(404, result.getStatusCode().value());
//    }
//
//    @Test
//    public void findTop4NewsPast7Days_Success_NoValue() throws Exception {
//        URI uri = new URI(baseUrl + port + "/news/find/top4news");
//
//        // Need to use array with a ResponseEntity here
//        ResponseEntity<News[]> result = restTemplate.getForEntity(uri, News[].class);
//        News[] news = result.getBody();
//
//        assertEquals(200, result.getStatusCode().value());
//        assertEquals(0, news.length);
//    }
//
//    @Test
//    public void findTop4NewsPast7Days_Success() throws Exception {
//        News n = NewsIntegrationTests.newsFormatter();
//        String title = newsRepository.save(n).getTitle();
//        News n1 = NewsIntegrationTests.newsFormatter();
//        n1.setTitle(title + "1");
//        n1.setViewCount(200);
//        newsRepository.save(n1);
//        News n2 = NewsIntegrationTests.newsFormatter();
//        n2.setTitle(title + "2");
//        n2.setViewCount(200);
//        newsRepository.save(n2);
//        News n3 = NewsIntegrationTests.newsFormatter();
//        n3.setTitle(title + "3");
//        n3.setViewCount(200);
//        newsRepository.save(n3);
//        URI uri = new URI(baseUrl + port + "/news/find/top4news");
//
//        // Need to use array with a ResponseEntity here
//        ResponseEntity<News[]> result = restTemplate.getForEntity(uri, News[].class);
//        News[] news = result.getBody();
//
//        assertEquals(200, result.getStatusCode().value());
//        assertEquals(4, news.length);
//    }


    private static News newsFormatter() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // arrange *** String title, String description, String content, String coverImage, Date date
        News news = null;
        try {
            news = new News("updated", "123456", "1", "1", formatter.parse("2020-01-01"));
        } catch (Exception e) {
            return null;
        }
        return news;
    }


}
