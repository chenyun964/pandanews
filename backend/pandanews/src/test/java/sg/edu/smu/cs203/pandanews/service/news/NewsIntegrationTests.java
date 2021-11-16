package sg.edu.smu.cs203.pandanews.service.news;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import sg.edu.smu.cs203.pandanews.model.category.Category;
import sg.edu.smu.cs203.pandanews.model.news.News;
import sg.edu.smu.cs203.pandanews.repository.CategoryRepository;
import sg.edu.smu.cs203.pandanews.repository.NewsRepository;
import sg.edu.smu.cs203.pandanews.repository.UserRepository;
import sg.edu.smu.cs203.pandanews.service.TestUtils;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
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
    private NewsRepository newsRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    TestUtils testUtils;


    @AfterEach
    void tearDown() {
        // clear the database after each test
        newsRepo.deleteAll();
        categoryRepo.deleteAll();
        userRepo.deleteAll();
        //test12
    }

    //Test pass
    @Test
    public void listNews_Success() throws Exception {

        URI uri = new URI(baseUrl + port + "/news");
        newsRepo.save(NewsIntegrationTests.newsFormatter());

        // Need to use array with a ResponseEntity here
        ResponseEntity<News[]> result = restTemplate.getForEntity(uri, News[].class);
        News[] newsList = result.getBody();

        assertEquals(200, result.getStatusCode().value());
        assertEquals(1, newsList.length);
    }

    //
    @Test
    public void findNewsByCategory_Success() throws Exception {
        News n = NewsIntegrationTests.newsFormatter();
        Category c = categoryRepo.save(new Category("business"));
        if (n != null) {
            n.setCategory(c);
            newsRepo.save(n);
        }
        // Need to use array with a ResponseEntity here
        URI uri = new URI(baseUrl + port + "/news/category/" + c.getTitle());
        ResponseEntity<News[]> result = restTemplate.getForEntity(uri, News[].class);
        News[] newsList = result.getBody();

        assertEquals(200, result.getStatusCode().value());
        assertEquals(1, newsList.length);
    }


    @Test
    public void deleteNews_Success() throws Exception {
        News n = NewsIntegrationTests.newsFormatter();
        long id = newsRepo.save(n).getId();
        // Need to use array with a ResponseEntity here
        URI uri = new URI(baseUrl + port + "/news/" + id);
        restTemplate.delete(uri);
    }

    //Test pass
    @Test
    public void getNewsById_Success() throws Exception {
        Long id = newsRepo.save(NewsIntegrationTests.newsFormatter()).getId();
        URI uri = new URI(baseUrl + port + "/news/" + id);

        // Need to use array with a ResponseEntity here
        ResponseEntity<News> result = restTemplate.getForEntity(uri, News.class);
        News news = result.getBody();

        assertEquals(200, result.getStatusCode().value());
        assertEquals(news.getTitle(), result.getBody().getTitle());
    }

    //Test pass
    @Test
    public void getNewsById_Failure() throws Exception {

        URI uri = new URI(baseUrl + port + "/news/1");

        // Need to use array with a ResponseEntity here
        ResponseEntity<News> result = restTemplate.getForEntity(uri, News.class);

        assertEquals(404, result.getStatusCode().value());
    }

    //Test pass
    @Test
    public void addNewsByManual_Success() throws Exception {
        News n = NewsIntegrationTests.newsFormatter();
        URI uri = new URI(baseUrl + port + "/news");

        // Need to use array with a ResponseEntity here
        HttpEntity<News> request = new HttpEntity(n, testUtils.exchangeHeader());
        ResponseEntity<News> result = restTemplate.exchange(uri, HttpMethod.POST, request, News.class);

        News news = result.getBody();
        assertEquals(200, result.getStatusCode().value());
        assertEquals(news.getTitle(), result.getBody().getTitle());
    }

    //Test pass
    @Test
    public void addNewsByManual_Failure_DuplicationOfTitle() throws Exception {
        News n = NewsIntegrationTests.newsFormatter();
        URI uri = new URI(baseUrl + port + "/news");
        HttpEntity<Object> header = new HttpEntity(testUtils.exchangeHeader());
        restTemplate.exchange(uri, HttpMethod.POST, header, News.class);
        ResponseEntity<News> result = restTemplate.exchange(uri, HttpMethod.POST, header, News.class);
        //ResponseEntity<News> result = restTemplate.postForEntity(uri, n, News.class);
        assertEquals(400, result.getStatusCode().value());
    }

    //Test pass
    @Test
    public void updateNews_Success() throws Exception {
        News n = NewsIntegrationTests.newsFormatter();
        URI uri1 = new URI(baseUrl + port + "/news");
        // Need to use array with a ResponseEntity here
        HttpEntity<News> request = new HttpEntity(n, testUtils.exchangeHeader());
        ResponseEntity<News> result1 = restTemplate.exchange(uri1, HttpMethod.POST, request, News.class);
        //ResponseEntity<News> result1 = restTemplate.postForEntity(uri1, n, News.class);
        News news = result1.getBody();
        long id = news.getId();
        n.setTitle("Updated News");
        URI uri2 = new URI(baseUrl + port + "/news/" + id);
        HttpEntity<News> request2 = new HttpEntity(n, testUtils.exchangeHeader());
        ResponseEntity<News> result2 = restTemplate.exchange(uri1, HttpMethod.POST, request2, News.class);

        assertEquals(200, result1.getStatusCode().value());
        assertEquals(200, result2.getStatusCode().value());
        assertEquals("Updated News", result2.getBody().getTitle());
    }

    //Test pass
    @Test
    public void updateNews_Failure_NotFound() throws Exception {

        News n = NewsIntegrationTests.newsFormatter();
        URI uri = new URI(baseUrl + port + "/news/" + 10L);
        HttpEntity<News> request2 = new HttpEntity(n, testUtils.exchangeHeader());
        ResponseEntity<News> result = restTemplate.exchange(uri, HttpMethod.POST, request2, News.class);
        assertEquals(404, result.getStatusCode().value());
    }


    @Test
    public void findNewsByKeyword_Success() throws Exception {
        News n = NewsIntegrationTests.newsFormatter();
        String title = newsRepo.save(n).getTitle();
        News n1 = NewsIntegrationTests.newsFormatter();
        n1.setTitle(title + "1");
        newsRepo.save(n1);
        News n2 = NewsIntegrationTests.newsFormatter();
        n2.setTitle(title + "2");
        newsRepo.save(n2);
        URI uri = new URI(baseUrl + port + "/news/keyword/" + title);

        // Need to use array with a ResponseEntity here
        ResponseEntity<News[]> result = restTemplate.getForEntity(uri, News[].class);
        News[] news = result.getBody();

        assertEquals(200, result.getStatusCode().value());
        assertEquals(3, news.length);
    }

    @Test
    public void findNewsByKeyword_Failure() throws Exception {
        URI uri = new URI(baseUrl + port + "/news/keyword/" + "ABCD");

        // Need to use array with a ResponseEntity here
        ResponseEntity<Object> result = restTemplate.getForEntity(uri, Object.class);

        assertEquals(404, result.getStatusCode().value());
    }



    private static News newsFormatter() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // arrange *** String title, String description, String content, String coverImage, Date date
        News news = null;
        try {
            news = new News("updated", "123456", "1", "1", LocalDate.now());
        } catch (Exception e) {
            return null;
        }
        return news;
    }


}
