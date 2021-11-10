package sg.edu.smu.cs203.pandanews.service.user;

<<<<<<< Updated upstream
import org.hibernate.boot.registry.classloading.spi.ClassLoaderService.Work;
import org.junit.jupiter.api.BeforeEach;
=======
>>>>>>> Stashed changes
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sg.edu.smu.cs203.pandanews.model.Organisation;
<<<<<<< Updated upstream
import sg.edu.smu.cs203.pandanews.model.WorkGroup;
import sg.edu.smu.cs203.pandanews.model.news.News;
import sg.edu.smu.cs203.pandanews.model.user.User;
import sg.edu.smu.cs203.pandanews.repository.CategoryRepository;
import sg.edu.smu.cs203.pandanews.repository.NewsRepository;
import sg.edu.smu.cs203.pandanews.repository.OrganisationRepository;
import sg.edu.smu.cs203.pandanews.repository.WorkGroupRepository;
=======
import sg.edu.smu.cs203.pandanews.model.user.User;
>>>>>>> Stashed changes
import sg.edu.smu.cs203.pandanews.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepo;

    @Mock
    private PasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();


    @Test
    void getUser_Success() {
        User n = generateTestUser("Test");
        when(userRepo.findById(any(Long.class))).thenReturn(Optional.of(n));

        User user = userService.getUser(10L);

        assertNotNull(user);
        verify(userRepo).findById(10L);
    }

    @Test
    void getUser_Failure() {
        when(userRepo.findById(any(Long.class))).thenReturn(Optional.empty());
        User u = userService.getUser(10L);

        assertNull(u);
        verify(userRepo).findById(10L);
    }

    @Test
    void listUsers_Success() {
        when(userRepo.findAll()).thenReturn(new ArrayList<User>());
        List<User> list = userService.listUsers();

        assertNotNull(list);
        verify(userRepo).findAll();
    }

    @Test
    void updateUser_returnUpdatedUser() {
        User u = generateTestUser("test");
        when(userRepo.findById(any(Long.class))).thenReturn(Optional.of(u));
        User updated = generateTestUser("updated");
        when(userRepo.save(any(User.class))).thenReturn(updated);

        long id = 10L;
        User newUser = userService.updateUser(id, updated);

        assertNotNull(newUser);
        assertEquals("updated", newUser.getUsername());
        verify(userRepo).findById(id);
        verify(userRepo).save(u);
    }

    @Test
    void deleteUser_Success() {
        UserServiceImpl mock = mock(UserServiceImpl.class);
        doNothing().when(mock).deleteUser(isA(Long.class));
        mock.deleteUser(10L);
        verify(mock, times(1)).deleteUser(10L);
    }

    @Test
    void getUserByUsername_Success() {
        User u = generateTestUser("test");
        when(userRepo.findByUsername(any(String.class))).thenReturn(Optional.of(u));

        User user = userService.getUserByUsername("test");

        assertNotNull(user);
        assertEquals("test", user.getUsername());
        verify(userRepo).findByUsername(u.getUsername());
    }

    @Test
    void updateUserCompany_Success() {
        User u = generateTestUser("test");
        User u2 = u;
        Organisation o = new Organisation("org");
        u2.setOrganisation(o);

        when(userRepo.findById(any(Long.class))).thenReturn(Optional.of(u));
        when(userRepo.save(any(User.class))).thenReturn(u2);

        User user = userService.updateUserCompany(10L, o);

        assertNotNull(user);
        assertEquals("org", user.getOrganisation().getTitle());
        verify(userRepo).findById(10L);
        verify(userRepo).save(u);
    }

    @Test
    void updateUserRole_Success() {
        User u = generateTestUser("test");
        User u2 = u;

        u2.setAuthorities("ROLE");

        when(userRepo.save(any(User.class))).thenReturn(u2);
        User user = userService.updateUserRole(u, "ROLE");

        assertNotNull(user);
        assertEquals(1, user.getAuthorities().size());
        verify(userRepo).save(u);
    }

    @Test
    void joinOrganisation_Success() {
        User u = generateTestUser("test");
        User u2 = u;
        Organisation o = new Organisation("org");
        u2.setOrganisation(o);

        when(userRepo.save(any(User.class))).thenReturn(u2);
        User user = userService.joinOrganisation(u, o);

        assertNotNull(user);
        assertEquals("org", user.getOrganisation().getTitle());
        verify(userRepo).save(u);
    }

    @Test
    void quitOrganisation_Success() {
        User u = generateTestUser("test");
        Organisation o = new Organisation("org");
        u.setOrganisation(o);
        User u2 = u;
        u2.setOrganisation(null);

<<<<<<< Updated upstream
        when(userRepository.save(any(User.class))).thenReturn(u2);
        User user = userService.quitOrganisation(u);

        assertNotNull(user);
        assertEquals(null, user.getOrganisation());
        verify(userRepository).save(u);
=======
        when(userRepo.save(any(User.class))).thenReturn(u2);
        User user = userService.updateVaccine(u);

        assertNotNull(user);
        assertEquals(true, user.getVaccinated());
        verify(userRepo).save(u);
>>>>>>> Stashed changes
    }

    @Test
    void joinWorkGroup_Success() {
        User u = generateTestUser("test");
        User u2 = u;
        WorkGroup w = new WorkGroup("wg");
        u2.setWorkGroup(w);

<<<<<<< Updated upstream
        when(userRepository.save(any(User.class))).thenReturn(u2);
        User user = userService.joinWorkGroup(u, w);

        assertNotNull(user);
        assertEquals("wg", user.getWorkGroup().getWorkGroupName());
        verify(userRepository).save(u);
    }

    @Test
    void quitWorkGroup_Success() {
        User u = generateTestUser("test");
        WorkGroup w = new WorkGroup("wg");
        u.setWorkGroup(w);
        User u2 = u;
        u2.setWorkGroup(w);

        when(userRepository.save(any(User.class))).thenReturn(u2);
        User user = userService.quitWorkGroup(u);

        assertNotNull(user);
        assertEquals(null, user.getWorkGroup());
        verify(userRepository).save(u);
    }

    @Test
    void updateVaccine_Success() {
        User u = generateTestUser("test");
        User u2 = u;
        u2.setVaccinated(true);

        when(userRepository.save(any(User.class))).thenReturn(u2);
        User user = userService.updateVaccine(u);

        assertNotNull(user);
        assertEquals(true, user.getVaccinated());
        verify(userRepository).save(u);
=======
        when(userRepo.save(any(User.class))).thenReturn(u2);
        User user = userService.quitOrganisation(u);

        assertNotNull(user);
        assertEquals(null, user.getOrganisation());
        verify(userRepo).save(u);
>>>>>>> Stashed changes
    }

    private User generateTestUser(String name) {
        return new User(name, "abc@gmail.com", "abc");
    }
}
