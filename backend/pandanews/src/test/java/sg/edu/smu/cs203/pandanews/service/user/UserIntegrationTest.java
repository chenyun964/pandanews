package sg.edu.smu.cs203.pandanews.service.user;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import sg.edu.smu.cs203.pandanews.controller.JwtAuthenticationController;
import sg.edu.smu.cs203.pandanews.dto.UserDTO;
import sg.edu.smu.cs203.pandanews.model.user.JwtRequest;
import sg.edu.smu.cs203.pandanews.model.user.User;
import sg.edu.smu.cs203.pandanews.repository.UserRepository;
import sg.edu.smu.cs203.pandanews.util.JwtTokenUtil;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserIntegrationTest {

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
    BCryptPasswordEncoder encoder;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    JwtAuthenticationController jwtAuthenticationController;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @AfterEach
    void tearDown() {
        // clear the database after each test
        userRepository.deleteAll();
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    String exchangeJWT() throws Exception {
        // clear the database after each test
        URI uri = new URI(baseUrl + port + "/authentication");

        User input = generateTestUser();
        User u2 = jwtUserDetailsService.save(new UserDTO(input.getUsername(), input.getEmail(), input.getPassword(),
                input.getPassword()));
        authenticate(input.getUsername(), input.getPassword());
        final UserDetails userDetails =
                userDetailsService.loadUserByUsername(new JwtRequest(input.getUsername(), input.getPassword()).getUsername());
        return jwtTokenUtil.generateToken(userDetails);

    }

//    @Test
//    public void listUser_Success() throws Exception {
//        String jwt = exchangeJWT().trim();
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.add("Authorization", "Bearer " + jwt);
//        HttpEntity<Object> header = new HttpEntity<Object>(map);
//        URI uri = new URI(baseUrl + port + "/users");
//        // Need to use array with a ResponseEntity here
//
//        ResponseEntity<User[]> result = restTemplate.exchange(uri, HttpMethod.GET, header, User[].class);
//        User[] newsList = result.getBody();
//        System.out.println(newsList);
//        assertEquals(200, result.getStatusCode().value());
//        assertEquals(1, newsList.length);
//    }



    private User generateTestUser() {
        return new User("name", "abc@gmail.com", "abc");
    }
}
