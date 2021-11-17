package sg.edu.smu.cs203.pandanews.service.statistic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import sg.edu.smu.cs203.pandanews.model.Statistic;
import sg.edu.smu.cs203.pandanews.repository.StatisticRepository;
import sg.edu.smu.cs203.pandanews.repository.UserRepository;
import sg.edu.smu.cs203.pandanews.service.TestUtils;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StatisticIntegrationTests {

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
    private StatisticRepository statisticRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    TestUtils testUtils;


    @AfterEach
    void tearDown() {
        // clear the database after each test
        userRepo.deleteAll();
        statisticRepo.deleteAll();
        //test12
    }

    @Test
    public void getStatistics_Success() throws Exception {

        URI uri = new URI(baseUrl + port + "/statistic");
        statisticRepo.save(new Statistic());

        // Need to use array with a ResponseEntity here
        ResponseEntity<Statistic[]> result = restTemplate.getForEntity(uri, Statistic[].class);
        Statistic[] statistics = result.getBody();

        assertNotNull(statistics);
        assertEquals(200, result.getStatusCode().value());
        assertEquals(1, statistics.length);
    }

    @Test
    public void getStatistic_Success() throws Exception {

        Statistic m = new Statistic();
        m.setNewRecovery(123);
        Long id = statisticRepo.save(m).getId();
        URI uri = new URI(baseUrl + port + "/statistic/" + id);

        // Need to use array with a ResponseEntity here
        ResponseEntity<Statistic> result = restTemplate.getForEntity(uri, Statistic.class);
        Statistic statistic = result.getBody();

        assertNotNull(statistic);
        assertEquals(200, result.getStatusCode().value());
        assertEquals(123, statistic.getNewRecovery());
    }

    @Test
    public void updateStatistic_Success() throws Exception {
        Statistic m = new Statistic();
        m.setNewCases(123);
        Long id = statisticRepo.save(m).getId();
        // Need to use array with a ResponseEntity here
        HttpHeaders header = testUtils.exchangeHeader();
        HttpEntity<Statistic> request = new HttpEntity(m, header);
        URI uri1 = new URI(baseUrl + port + "/statistic/" + id);
        ResponseEntity<Statistic> result1 = restTemplate.exchange(uri1, HttpMethod.PUT, request, Statistic.class);
        m.setNewDeaths(123);
        URI uri2 = new URI(baseUrl + port + "/statistic/" + id);
        HttpEntity<Statistic> request2 = new HttpEntity(m, header);
        ResponseEntity<Statistic> result2 = restTemplate.exchange(uri1, HttpMethod.PUT, request2, Statistic.class);

        assertNotNull(result2);
        assertNotNull(result1);
        assertEquals(200, result1.getStatusCode().value());
        assertEquals(200, result2.getStatusCode().value());
        assertEquals(123, result2.getBody().getNewDeaths());
    }
}
