package sg.edu.smu.cs203.pandanews.service.measurement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import sg.edu.smu.cs203.pandanews.model.Measurement;
import sg.edu.smu.cs203.pandanews.repository.MeasurementRepository;
import sg.edu.smu.cs203.pandanews.repository.UserRepository;
import sg.edu.smu.cs203.pandanews.service.TestUtils;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class MeasurementIntegrationTests {

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
    private MeasurementRepository measurementRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    TestUtils testUtils;


    @AfterEach
    void tearDown() {
        // clear the database after each test
        userRepo.deleteAll();
        measurementRepo.deleteAll();
        //test12
    }

    @Test
    public void getMeasurements_Success() throws Exception {

        URI uri = new URI(baseUrl + port + "/measurements");
        measurementRepo.save(new Measurement());

        // Need to use array with a ResponseEntity here
        ResponseEntity<Measurement[]> result = restTemplate.getForEntity(uri, Measurement[].class);
        Measurement[] newsList = result.getBody();

        assertNotNull(newsList);
        assertEquals(200, result.getStatusCode().value());
        assertEquals(1, newsList.length);
    }

    @Test
    public void getMeasurement_Success() throws Exception {

        Measurement m = new Measurement();
        m.setContent("123");
        Long id = measurementRepo.save(m).getId();
        URI uri = new URI(baseUrl + port + "/measurements/" + id);

        // Need to use array with a ResponseEntity here
        ResponseEntity<Measurement> result = restTemplate.getForEntity(uri, Measurement.class);
        Measurement newsList = result.getBody();

        assertNotNull(newsList);
        assertEquals(200, result.getStatusCode().value());
        assertEquals("123", newsList.getContent());
    }

//    @Test
//    public void updateMeasurement_Success() throws Exception {
//        Measurement m = new Measurement();
//        m.setContent("123");
//        Long id = measurementRepo.save(m).getId();
//        // Need to use array with a ResponseEntity here
//        HttpEntity<Measurement> request = new HttpEntity(m, testUtils.exchangeHeader());
//        URI uri1 = new URI(baseUrl + port + "/measurements/" + id);
//        ResponseEntity<Measurement> result1 = restTemplate.exchange(uri1, HttpMethod.PUT, request, Measurement.class);
//        m.setTitle("Updated Measurement");
//        URI uri2 = new URI(baseUrl + port + "/measurements/" + id);
//        HttpEntity<Measurement> request2 = new HttpEntity(m, testUtils.exchangeHeader());
//        ResponseEntity<Measurement> result2 = restTemplate.exchange(uri1, HttpMethod.PUT, request2, Measurement.class);
//
//        assertNotNull(result2);
//        assertNotNull(result1);
//        assertEquals(200, result1.getStatusCode().value());
//        assertEquals(200, result2.getStatusCode().value());
//        assertEquals("Updated Measurement", result2.getBody().getTitle());
//    }
}
