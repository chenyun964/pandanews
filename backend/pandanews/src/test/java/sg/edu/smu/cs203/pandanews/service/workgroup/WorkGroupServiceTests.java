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

//    @Test
//    void listWorkGroupsByOrganisation_Success() {
//        Organisation o = new Organisation("org");
//        when(workGroupRepository.findByOrganisationId(any(Long.class))).thenReturn(new ArrayList<WorkGroup>());
//        List<WorkGroup> list = workGroupService.listWorkGroups(o.getId());
//
//        assertNotNull(list);
//        verify(workGroupRepository).findAll();
//    }

    @Test
    void listWorkGroupsByOrganisation_Success() {
        //Organisation o = new Organisation("org");
        List<WorkGroup> list = new ArrayList<>();
        WorkGroup wg = new WorkGroup("name", new Organisation("org"));
        list.add(wg);
        when(workGroupRepository.findByOrganisationId(any(Long.class))).thenReturn(list);
        List<WorkGroup> wgList = workGroupService.listWorkGroups(10L);

        assertNotNull(list);
        verify(workGroupRepository).findByOrganisationId(10L);
    }

    @Test
    void updateWorkGroups_Success() {
        Organisation o = new Organisation("org");
        WorkGroup wg = generateTestWorkGroup("test", o);
        when(workGroupRepository.findById(any(Long.class))).thenReturn(Optional.of(wg));
        WorkGroup updated = generateTestWorkGroup("updated", o);
        when(workGroupRepository.save(any(WorkGroup.class))).thenReturn(updated);

        long id = 10L;
        WorkGroup newWorkGroup = workGroupService.updateWorkGroup(id, updated);

        assertNotNull(newWorkGroup);
        assertEquals("updated", newWorkGroup.getWorkGroupName());
        verify(workGroupRepository).findById(id);
        verify(workGroupRepository).save(wg);
    }

    @Test
    void deleteWorkGroup_Success() {
        WorkGroupServiceImpl mock = mock(WorkGroupServiceImpl.class);
        doNothing().when(mock).deleteWorkGroup(isA(Long.class));
        mock.deleteWorkGroup(10L);
        verify(mock, times(1)).deleteWorkGroup(10L);
    }

    private WorkGroup generateTestWorkGroup(String name, Organisation o) {
        return new WorkGroup(name, o);
    }
}
