package sg.edu.smu.cs203.pandanews.service.user;

import java.util.List;
import org.springframework.stereotype.Service;

import sg.edu.smu.cs203.pandanews.model.user.User;
import sg.edu.smu.cs203.pandanews.model.Organisation;
import sg.edu.smu.cs203.pandanews.model.WorkGroup;

@Service
public interface UserService {
    List<User> listUsers();

    User getUser(Long id);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    User getUserByUsername(String username);

    User updateUserCompany(Long id, Organisation organisation);

    User updateUserRole(User user, String role);

    User joinOrganisation(User user, Organisation organisation);

    User joinWorkGroup(User user, WorkGroup workGroup);

    User updateVaccine(User user);

    User quitOrganisation(User user);

    User quitWorkGroup(User user);
}
