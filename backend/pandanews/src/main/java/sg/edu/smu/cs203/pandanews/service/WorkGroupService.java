package sg.edu.smu.cs203.pandanews.service;

import java.util.List;
import org.springframework.stereotype.Service;

import sg.edu.smu.cs203.pandanews.model.WorkGroup;

@Service
public interface WorkGroupService {
    List<WorkGroup> listWorkGroups();
    WorkGroup getWorkGroup(Long id);
    WorkGroup addWorkGroup(WorkGroup workgroup);
    WorkGroup updateWorkGroup(Long id, WorkGroup workgroup);
    void deleteWorkGroup(Long id);
    WorkGroup getWorkGroupByUsername(String username);
}
