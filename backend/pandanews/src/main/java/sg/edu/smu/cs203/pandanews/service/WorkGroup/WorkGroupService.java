package sg.edu.smu.cs203.pandanews.service.workGroup;

import java.util.List;
import org.springframework.stereotype.Service;

import sg.edu.smu.cs203.pandanews.model.WorkGroup;

@Service
public interface WorkGroupService {

    WorkGroup getWorkGroup(Long id);

    WorkGroup addWorkGroup(WorkGroup workgroup);

    WorkGroup updateWorkGroup(Long id, WorkGroup workgroup);

    List<WorkGroup> listWorkGroups(Long organisationId);

    void deleteWorkGroup(Long id);
}
