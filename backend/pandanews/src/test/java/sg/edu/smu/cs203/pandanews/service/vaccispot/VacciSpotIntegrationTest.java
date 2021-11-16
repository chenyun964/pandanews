package sg.edu.smu.cs203.pandanews.service.vaccispot;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import sg.edu.smu.cs203.pandanews.model.VacciSpot;
import sg.edu.smu.cs203.pandanews.repository.UserRepository;
import sg.edu.smu.cs203.pandanews.repository.VacciSpotRepository;
import sg.edu.smu.cs203.pandanews.service.TestUtils;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class VacciSpotIntegrationTest {

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
    private VacciSpotRepository vacciSpotRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    TestUtils testUtils;


    @AfterEach
    void tearDown() {
        // clear the database after each test
        userRepo.deleteAll();
        vacciSpotRepo.deleteAll();
        //test12
    }

    @Test
    public void getVacciSpots_Success() throws Exception {

        URI uri = new URI(baseUrl + port + "/vaccispots");
        vacciSpotRepo.save(new VacciSpot());

        // Need to use array with a ResponseEntity here
        ResponseEntity<VacciSpot[]> result = restTemplate.getForEntity(uri, VacciSpot[].class);
        VacciSpot[] vacciList = result.getBody();

        assertNotNull(vacciList);
        assertEquals(200, result.getStatusCode().value());
        assertEquals(1, vacciList.length);
    }

    @Test
    public void getVacciSpot_Success() throws Exception {

        VacciSpot m = new VacciSpot();
        m.setAddress("123");
        Long id = vacciSpotRepo.save(m).getId();
        URI uri = new URI(baseUrl + port + "/vaccispots/" + id);

        // Need to use array with a ResponseEntity here
        ResponseEntity<VacciSpot> result = restTemplate.getForEntity(uri, VacciSpot.class);
        VacciSpot vacciSpot = result.getBody();

        assertNotNull(vacciSpot);
        assertEquals(200, result.getStatusCode().value());
        assertEquals("123", vacciSpot.getAddress());
    }

}
