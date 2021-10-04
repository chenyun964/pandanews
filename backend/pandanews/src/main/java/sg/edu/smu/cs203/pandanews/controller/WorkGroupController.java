package sg.edu.smu.cs203.pandanews.controller;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.smu.cs203.pandanews.service.WorkGroupService;
import sg.edu.smu.cs203.pandanews.model.WorkGroup;

@RestController
public class WorkGroupController {
    private WorkGroupService workGroupService;

    public WorkGroupController(WorkGroupService workGroupService){
        this.workGroupService = workGroupService;
    }

    /**
     * List all books in the system
     * @return list of all books
     */
    @GetMapping("/organisations/{oid}/workgroups")
    public List<WorkGroup> getWorkGroups(@PathVariable Long oid){
        return workGroupService.listWorkGroups(oid);
    }

    /**
     * Search for book with the given id
     * If there is no book with the given "id", throw a BookNotFoundException
     * @param id
     * @return book with the given id
     */
    @GetMapping("/organisations/{oid}/workgroups/{id}")
    public WorkGroup getWorkGroup(@PathVariable Long id){
        WorkGroup workGroup = workGroupService.getWorkGroup(id);

        // Need to handle "book not found" error using proper HTTP status code
        // In this case it should be HTTP 404
        if(workGroup == null) return null;
        return workGroupService.getWorkGroup(id);

    }
    /**
     * Add a new book with POST request to "/books"
     * Note the use of @RequestBody
     * @param workGroup
     * @return list of all books
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/organisations/{oid}/workgroups")
    public WorkGroup addWorkGroup(@PathVariable Long oid, @RequestBody WorkGroup workGroup){
        return workGroupService.addWorkGroup(workGroup);
    }

    /**
     * If there is no book with the given "id", throw a BookNotFoundException
     * @param id
     * @param newOrganisationInfo
     * @return the updated, or newly added book
     */
    @PutMapping("/organisations/{oid}/workgroups/{id}")
    public WorkGroup updateWorkGroup(@PathVariable Long oid, @PathVariable Long id, @RequestBody WorkGroup newWorkGroupInfo){
        WorkGroup workGroup = workGroupService.updateWorkGroup(id, newWorkGroupInfo);
        if(workGroup == null) return null;
        
        return workGroup;
    }

    /**
     * Remove a book with the DELETE request to "/books/{id}"
     * If there is no book with the given "id", throw a BookNotFoundException
     * @param id
     */
    @DeleteMapping("/organisations/{oid}/workgroups/{id}")
    public void deleteWorkGroup(@PathVariable Long oid, @PathVariable Long id){
        try {
            workGroupService.deleteWorkGroup(id);
        } catch(EmptyResultDataAccessException e) {
            // throw new WorkGroupNotFoundException(id);
        }
    }
}
