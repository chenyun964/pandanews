package sg.edu.smu.cs203.pandanews.service.workgroup;

import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.smu.cs203.pandanews.model.WorkGroup;
import sg.edu.smu.cs203.pandanews.repository.WorkGroupRepository;

@Service
public class WorkGroupServiceImpl implements WorkGroupService {

    private WorkGroupRepository workGroupRepo;

    public WorkGroupServiceImpl(WorkGroupRepository workGroupRepo) {
        this.workGroupRepo = workGroupRepo;
    }

    @Override
    public WorkGroup getWorkGroup(Long id) {
        return workGroupRepo.findById(id).orElse(null);
    }

    @Override
    public List<WorkGroup> listWorkGroups(Long organisationId) {
        return workGroupRepo.findByOrganisationId(organisationId);
    }

    @Override
    public WorkGroup addWorkGroup(WorkGroup workgroup) {
        return workGroupRepo.save(workgroup);
    }

    @Override
    public WorkGroup updateWorkGroup(Long id, WorkGroup newWorkGroup) {
        return workGroupRepo.findById(id).map(workgroup -> {
            workgroup.setWorkGroupName(newWorkGroup.getWorkGroupName());
            workgroup.setDatesInOffice(newWorkGroup.getDatesInOffice());
            return workGroupRepo.save(workgroup);
        }).orElse(null);
    }

    @Override
    public void deleteWorkGroup(Long id) {
        workGroupRepo.deleteById(id);
    }

}
