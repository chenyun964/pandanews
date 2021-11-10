package sg.edu.smu.cs203.pandanews.service.workgroup;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sg.edu.smu.cs203.pandanews.model.Organisation;
import sg.edu.smu.cs203.pandanews.model.WorkGroup;
import sg.edu.smu.cs203.pandanews.repository.WorkGroupRepository;

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
    private WorkGroupRepository workGroupRepo;
    


    @Test
    void getWorkGroup_Success() {
        Organisation o = new Organisation("org");
        WorkGroup n = generateTestWorkGroup("Test", o);
        when(workGroupRepo.findById(any(Long.class))).thenReturn(Optional.of(n));

        WorkGroup wg = workGroupService.getWorkGroup(10L);

        assertNotNull(wg);
        verify(workGroupRepo).findById(10L);
    }

    @Test
    void getWorkGroup_Failure() {
        when(workGroupRepo.findById(any(Long.class))).thenReturn(Optional.empty());
        WorkGroup wg = workGroupService.getWorkGroup(10L);

        assertNull(wg);
        verify(workGroupRepo).findById(10L);
    }

    @Test
    void listWorkGroupsByOrganisation_Success() {
        List<WorkGroup> list = new ArrayList<>();
        WorkGroup wg = new WorkGroup("name", new Organisation("org"));
        list.add(wg);
        when(workGroupRepo.findByOrganisationId(any(Long.class))).thenReturn(list);
        List<WorkGroup> wgList = workGroupService.listWorkGroups(10L);

        assertNotNull(list);
        assertEquals(1, wgList.size());
        assertEquals("name", wgList.get(0).getWorkGroupName());
        assertEquals("org", wgList.get(0).getOrganisation().getTitle());
        verify(workGroupRepo).findByOrganisationId(10L);
    }

    @Test
    void updateWorkGroups_Success() {
        Organisation o = new Organisation("org");
        WorkGroup wg = generateTestWorkGroup("test", o);
        when(workGroupRepo.findById(any(Long.class))).thenReturn(Optional.of(wg));
        WorkGroup updated = generateTestWorkGroup("updated", o);
        when(workGroupRepo.save(any(WorkGroup.class))).thenReturn(updated);

        long id = 10L;
        WorkGroup newWorkGroup = workGroupService.updateWorkGroup(id, updated);

        assertNotNull(newWorkGroup);
        assertEquals("updated", newWorkGroup.getWorkGroupName());
        verify(workGroupRepo).findById(id);
        verify(workGroupRepo).save(wg);
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
