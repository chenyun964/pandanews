package sg.edu.smu.cs203.pandanews.service;

import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.smu.cs203.pandanews.model.WorkGroup;
import sg.edu.smu.cs203.pandanews.repository.WorkGroupRepository;

@Service
public class WorkGroupServiceImpl implements WorkGroupService {

    private WorkGroupRepository workgroups;

    public WorkGroupServiceImpl(WorkGroupRepository workgroups) {
        this.workgroups = workgroups;
    }

    @Override
    public WorkGroup getWorkGroup(Long id) {
        return workgroups.findById(id).orElse(null);
    }

    @Override
    public List<WorkGroup> listWorkGroups() {
        return workgroups.findAll();
    }

    @Override
    public WorkGroup addWorkGroup(WorkGroup workgroup) {
        return workgroups.save(workgroup);
    }

    @Override
    public WorkGroup updateWorkGroup(Long id, WorkGroup newWorkGroup) {
        return workgroups.findById(id).map(workgroup -> {
            workgroups.setTitle(newWorkGroup.getTitle());
            workgroups.setAddress(newWorkGroup.getAddress());
            workgroups.setContact(newWorkGroup.getContact());
            workgroups.setCode(newWorkGroup.getCode());
            return workgroups.save(workgroup);
        }).orElse(null);
    }

    @Override
    public void deleteWorkGroup(Long id) {
        workgroups.deleteById(id);
    }

}
