package sg.edu.smu.cs203.pandanews.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs203.pandanews.dto.AdminDTO;
import sg.edu.smu.cs203.pandanews.dto.UserDTO;
import sg.edu.smu.cs203.pandanews.model.user.User;
import sg.edu.smu.cs203.pandanews.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));

    }

    public UserDetails loadAdminByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findAdminByUsername(username, "ROLE_ADMIN")
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));
    }

    public User save(UserDTO user) {
        User newUser = new User();
        User u = userRepo.findByUsername(user.getUsername()).orElse(null);
        if (u != null) return null;
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setEmail(user.getEmail());
        return userRepo.save(newUser);
    }

    public User save(AdminDTO admin) {
        User newAdmin = new User("ROLE_ADMIN");
        User u = userRepo.findByUsername(newAdmin.getUsername()).orElse(null);
        if (u != null) return null;
        newAdmin.setUsername(admin.getUsername());
        newAdmin.setPassword(bcryptEncoder.encode(admin.getPassword()));
        return userRepo.save(newAdmin);
    }
}
