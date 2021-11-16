package sg.edu.smu.cs203.pandanews.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import sg.edu.smu.cs203.pandanews.dto.UserDTO;
import sg.edu.smu.cs203.pandanews.model.user.JwtRequest;
import sg.edu.smu.cs203.pandanews.model.user.User;
import sg.edu.smu.cs203.pandanews.service.user.JwtUserDetailsService;
import sg.edu.smu.cs203.pandanews.util.JwtTokenUtil;

@Component
public class TestUtils {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    public String exchangeJWT() throws Exception {
        // clear the database after each test
        User input = generateTestUser();
        jwtUserDetailsService.save(new UserDTO(input.getUsername(), input.getEmail(), input.getPassword(),
                input.getPassword()));
        authenticate(input.getUsername(), input.getPassword());
        final UserDetails userDetails =
                userDetailsService.loadUserByUsername(new JwtRequest(input.getUsername(), input.getPassword()).getUsername());
        return jwtTokenUtil.generateToken(userDetails);

    }

    public HttpHeaders exchangeHeader() throws Exception {
        String jwt = exchangeJWT();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("Authorization", "Bearer " + jwt);
        return new HttpHeaders(map);
    }

    private User generateTestUser() {
        User u = new User("name", "abc@gmail.com", "abc");
        u.setAuthorities("ROLE_ADMIN");
        return u;
    }

}
