package sg.edu.smu.cs203.pandanews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import javax.validation.Valid;
import org.springframework.web.server.ResponseStatusException;

import sg.edu.smu.cs203.pandanews.util.JwtTokenUtil;
import sg.edu.smu.cs203.pandanews.model.user.JwtRequest;
import sg.edu.smu.cs203.pandanews.model.user.JwtResponse;
import sg.edu.smu.cs203.pandanews.service.user.JwtUserDetailsService;
import sg.edu.smu.cs203.pandanews.dto.AdminDTO;
import sg.edu.smu.cs203.pandanews.dto.UserDTO;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDTO userDTO) throws Exception {
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Password and confirmPassword provided do not match!");
        }
        return ResponseEntity.ok(userDetailsService.save(userDTO));
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admin/authenticate")
    public ResponseEntity<?> createAuthenticationTokenAdmin(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadAdminByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admin/register")
    public ResponseEntity<?> addAdmin(@Valid @RequestBody AdminDTO adminDTO) throws Exception {
        return ResponseEntity.ok(userDetailsService.save(adminDTO));
    }
}
