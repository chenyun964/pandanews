package sg.edu.smu.cs203.pandanews.service.workgroup;

import org.hibernate.boot.registry.classloading.spi.ClassLoaderService.Work;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sg.edu.smu.cs203.pandanews.model.Organisation;
import sg.edu.smu.cs203.pandanews.model.WorkGroup;
import sg.edu.smu.cs203.pandanews.model.news.News;
import sg.edu.smu.cs203.pandanews.model.user.User;
import sg.edu.smu.cs203.pandanews.repository.CategoryRepository;
import sg.edu.smu.cs203.pandanews.repository.NewsRepository;
import sg.edu.smu.cs203.pandanews.repository.OrganisationRepository;
import sg.edu.smu.cs203.pandanews.repository.WorkGroupRepository;
import sg.edu.smu.cs203.pandanews.repository.UserRepository;
import sg.edu.smu.cs203.pandanews.service.news.NewsServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class WorkGroupServiceTests {

    @InjectMocks
    private WorkGroupServiceImpl workGroupService;

    @Mock
    private WorkGroupRepository workGroupRepository;

    @Mock
    private PasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();


    @Test
    void getWorkGroup_Success() {
        Organisation o = new Organisation("org");
        WorkGroup n = generateTestWorkGroup("Test", o);
        when(workGroupRepository.findById(any(Long.class))).thenReturn(Optional.of(n));

        WorkGroup wg = workGroupService.getWorkGroup(10L);

        assertNotNull(wg);
        verify(workGroupRepository).findById(10L);
    }

    @Test
    void getWorkGroup_Failure() {
        when(workGroupRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        WorkGroup wg = workGroupService.getWorkGroup(10L);

        assertNull(wg);
        verify(workGroupRepository).findById(10L);
    }

    @Test
    void listWorkGroups_Success() {
        when(workGroupRepository.findAll()).thenReturn(new ArrayList<WorkGroup>());
        List<WorkGroup> list = workGroupService.listWorkGroups();

        assertNotNull(list);
        verify(workGroupRepository).findAll();
    }

    @Test
    void updateNews_ReturnUpdatedBook() {
        User u = generateTestUser("test");
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(u));
        User updated = generateTestUser("updated");
        when(userRepository.save(any(User.class))).thenReturn(updated);

        long id = 10L;
        User newUser = userService.updateUser(id, updated);

        assertNotNull(newUser);
        assertEquals("updated", newUser.getUsername());
        verify(userRepository).findById(id);
        verify(userRepository).save(u);
    }

    @Test
    void deleteNews_Success() {
        UserServiceImpl mock = mock(UserServiceImpl.class);
        doNothing().when(mock).deleteUser(isA(Long.class));
        mock.deleteUser(10L);
        verify(mock, times(1)).deleteUser(10L);
    }

    @Test
    void getUserByUsername_Success() {
        User u = generateTestUser("test");
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(u));

        User user = userService.getUserByUsername("test");

        assertNotNull(user);
        assertEquals("test", user.getUsername());
        verify(userRepository).findByUsername(u.getUsername());
    }

    @Test
    void updateUserCompany_Success() {
        User u = generateTestUser("test");
        User u2 = u;
        Organisation o = new Organisation("org");
        u2.setOrganisation(o);

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(u));
        when(userRepository.save(any(User.class))).thenReturn(u2);

        User user = userService.updateUserCompany(10L, o);

        assertNotNull(user);
        assertEquals("org", user.getOrganisation().getTitle());
        verify(userRepository).findById(10L);
        verify(userRepository).save(u);
    }

    @Test
    void updateUserRole_Success() {
        User u = generateTestUser("test");
        User u2 = u;

        u2.setAuthorities("ROLE");

        when(userRepository.save(any(User.class))).thenReturn(u2);
        User user = userService.updateUserRole(u, "ROLE");

        assertNotNull(user);
        assertEquals(1, user.getAuthorities().size());
        verify(userRepository).save(u);
    }

    @Test
    void joinOrganisation_Success() {
        User u = generateTestUser("test");
        User u2 = u;
        Organisation o = new Organisation("org");
        u2.setOrganisation(o);

        when(userRepository.save(any(User.class))).thenReturn(u2);
        User user = userService.joinOrganisation(u, o);

        assertNotNull(user);
        assertEquals("org", user.getOrganisation().getTitle());
        verify(userRepository).save(u);
    }

    @Test
    void quitOrganisation_Success() {
        User u = generateTestUser("test");
        Organisation o = new Organisation("org");
        u.setOrganisation(o);
        User u2 = u;
        u2.setOrganisation(null);

        when(userRepository.save(any(User.class))).thenReturn(u2);
        User user = userService.quitOrganisation(u);

        assertNotNull(user);
        assertEquals(null, user.getOrganisation());
        verify(userRepository).save(u);
    }

    @Test
    void joinWorkGroup_Success() {
        User u = generateTestUser("test");
        User u2 = u;
        WorkGroup w = new WorkGroup("wg");
        u2.setWorkGroup(w);

        when(userRepository.save(any(User.class))).thenReturn(u2);
        User user = userService.joinWorkGroup(u, w);

        assertNotNull(user);
        assertEquals("wg", user.getWorkGroup().getTitle());
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
    }

    private WorkGroup generateTestWorkGroup(String name, Organisation o) {
        return new WorkGroup(name, o);
    }
}
