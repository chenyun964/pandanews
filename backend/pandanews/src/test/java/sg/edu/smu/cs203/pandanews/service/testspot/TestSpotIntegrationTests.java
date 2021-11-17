package sg.edu.smu.cs203.pandanews.service.testspot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import sg.edu.smu.cs203.pandanews.model.TestSpot;
import sg.edu.smu.cs203.pandanews.repository.TestSpotRepository;
import sg.edu.smu.cs203.pandanews.repository.UserRepository;
import sg.edu.smu.cs203.pandanews.service.TestUtils;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TestSpotIntegrationTests {

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
    private TestSpotRepository testSpotRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    TestUtils testUtils;


    @AfterEach
    void tearDown() {
        // clear the database after each test
        userRepo.deleteAll();
        testSpotRepo.deleteAll();
        //test12
    }

    @Test
    public void getTestSpots_Success() throws Exception {

        URI uri = new URI(baseUrl + port + "/testspots");
        testSpotRepo.save(new TestSpot());

        // Need to use array with a ResponseEntity here
        ResponseEntity<TestSpot[]> result = restTemplate.getForEntity(uri, TestSpot[].class);
        TestSpot[] testSpots = result.getBody();

        assertNotNull(testSpots);
        assertEquals(200, result.getStatusCode().value());
        assertEquals(1, testSpots.length);
    }

    @Test
    public void getTestSpot_Success() throws Exception {

        TestSpot m = new TestSpot();
        m.setLatitude(123D);
        Long id = testSpotRepo.save(m).getId();
        URI uri = new URI(baseUrl + port + "/testspots/" + id);

        // Need to use array with a ResponseEntity here
        ResponseEntity<TestSpot> result = restTemplate.getForEntity(uri, TestSpot.class);
        TestSpot statistic = result.getBody();

        assertNotNull(statistic);
        assertEquals(200, result.getStatusCode().value());
        assertEquals(123, statistic.getLatitude());
    }


}
