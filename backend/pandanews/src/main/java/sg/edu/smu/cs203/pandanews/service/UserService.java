package sg.edu.smu.cs203.pandanews.service;

import java.util.List;
import org.springframework.stereotype.Service;

import sg.edu.smu.cs203.pandanews.model.User;
import sg.edu.smu.cs203.pandanews.model.Organisation;

@Service
public interface UserService {
    List<User> listUsers();
    User getUser(Long id);
    User addUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    User getUserByUsername(String username);
    User updateUserCompany(Long id, Organisation organisation);
}
