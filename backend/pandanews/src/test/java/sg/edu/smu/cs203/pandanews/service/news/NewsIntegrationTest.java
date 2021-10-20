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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sg.edu.smu.cs203.pandanews.model.news.News;
import sg.edu.smu.cs203.pandanews.repository.NewsRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class NewsIntegrationTest {

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
    BCryptPasswordEncoder encoder;

    @AfterEach
    void tearDown() {
        // clear the database after each test
        newsRepository.deleteAll();
    }

    //Test pass
    @Test
    public void listNews_Success() throws Exception {

        URI uri = new URI(baseUrl + port + "/news/list");
        newsRepository.save(NewsIntegrationTest.newsFormatter());

        // Need to use array with a ResponseEntity here
        ResponseEntity<News[]> result = restTemplate.getForEntity(uri, News[].class);
        News[] newsList = result.getBody();

        assertEquals(200, result.getStatusCode().value());
        assertEquals(1, newsList.length);
    }

    //Test pass
    @Test
    public void getNewsById_Success() throws Exception {

        Long id = newsRepository.save(NewsIntegrationTest.newsFormatter()).getId();
        URI uri = new URI(baseUrl + port + "/news/find/id/" + id);

        // Need to use array with a ResponseEntity here
        ResponseEntity<News> result = restTemplate.getForEntity(uri, News.class);
        News news = result.getBody();

        assertEquals(200, result.getStatusCode().value());
        assertEquals(news.getTitle(), result.getBody().getTitle());
    }

    //Test pass
    @Test
    public void getNewsById_Failure() throws Exception {

        URI uri = new URI(baseUrl + port + "/news/find/id/1");

        // Need to use array with a ResponseEntity here
        ResponseEntity<News> result = restTemplate.getForEntity(uri, News.class);

        assertEquals(404, result.getStatusCode().value());
    }

    //Test pass
    @Test
    public void addNewsByManual_Success() throws Exception {
        News n = NewsIntegrationTest.newsFormatter();
        URI uri = new URI(baseUrl + port + "/news/create");

        // Need to use array with a ResponseEntity here
        ResponseEntity<News> result = restTemplate.postForEntity(uri, n, News.class);
        News news = result.getBody();

        assertEquals(200, result.getStatusCode().value());
        assertEquals(news.getTitle(), result.getBody().getTitle());
    }

    //Test pass
    @Test
    public void addNewsByManual_Failure_DuplicationOfTitle() throws Exception {

        News n = NewsIntegrationTest.newsFormatter();
        URI uri = new URI(baseUrl + port + "/news/create");
        // Need to use array with a ResponseEntity here
        restTemplate.postForEntity(uri, n, News.class);
        ResponseEntity<News> result = restTemplate.postForEntity(uri, n, News.class);
        assertEquals(400, result.getStatusCode().value());
    }




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
