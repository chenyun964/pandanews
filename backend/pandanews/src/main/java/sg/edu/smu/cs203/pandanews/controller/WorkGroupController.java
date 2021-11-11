package sg.edu.smu.cs203.pandanews.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import sg.edu.smu.cs203.pandanews.exception.UnauthenticatedException;
import sg.edu.smu.cs203.pandanews.exception.UnauthorizedUserException;
import sg.edu.smu.cs203.pandanews.service.user.UserService;
import sg.edu.smu.cs203.pandanews.service.workgroup.WorkGroupService;
import sg.edu.smu.cs203.pandanews.model.WorkGroup;
import sg.edu.smu.cs203.pandanews.repository.UserRepository;
import sg.edu.smu.cs203.pandanews.model.user.User;
import sg.edu.smu.cs203.pandanews.exception.WorkGroupNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
public class WorkGroupController {
    private WorkGroupService workGroupService;
    private UserService userService;
    private UserRepository userRepo;

    @Autowired
    public WorkGroupController(WorkGroupService workGroupService, UserService userService, UserRepository userRepo) {
        this.workGroupService = workGroupService;
        this.userService = userService;
        this.userRepo = userRepo;
    }

    /**
     * Lists all work groups under the given organisation
     * Throws UnauthenticatedException, UnauthorizedUserException
     * 
     * @param oid
     * @return List<WorkGroup>
     */
    @GetMapping("/organisation/{oid}/workgroup")
    public List<WorkGroup> getWorkGroups(@PathVariable Long oid)
            throws UnauthenticatedException, UnauthorizedUserException {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userService.getUserByUsername(userDetails.getUsername());
        if (user == null)
            throw new UnauthenticatedException();

        if (!userRepo.findByOrganisationId(oid).contains(user))
            throw new UnauthorizedUserException();

        return workGroupService.listWorkGroups(oid);
    }

    /**
     * Gets the work group with the specified id under the given organisation
     * Throws UnauthenticatedException, UnauthorizedUserException
     * 
     * @param oid, @param id
     * @return WorkGroup
     */
    @GetMapping("/organisation/{oid}/workgroup/{id}")
    public WorkGroup getWorkGroup(@PathVariable Long oid, @PathVariable Long id)
            throws UnauthenticatedException, UnauthorizedUserException {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userService.getUserByUsername(userDetails.getUsername());
        if (user == null)
            throw new UnauthenticatedException();

        if (!userRepo.findByOrganisationId(oid).contains(user))
            throw new UnauthorizedUserException();

        WorkGroup workGroup = workGroupService.getWorkGroup(id);
        if (workGroup == null)
            return null;
        return workGroupService.getWorkGroup(id);

    }

    /**
     * Adds a work group under the given organisation
     * Throws UnauthenticatedException, UnauthorizedUserException
     * 
     * @param oid, @param workGroup
     * @return WorkGroup
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/organisation/{oid}/workgroup")
    public WorkGroup addWorkGroup(@PathVariable Long oid, @RequestBody WorkGroup workGroup)
            throws UnauthenticatedException, UnauthorizedUserException {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userService.getUserByUsername(userDetails.getUsername());
        if (user == null)
            throw new UnauthenticatedException();

        if (!userRepo.findByOrganisationId(oid).contains(user))
            throw new UnauthorizedUserException();

        return workGroupService.addWorkGroup(workGroup);
    }

    /**
     * Updates a work group under the given organisation
     * Throws UnauthenticatedException, UnauthorizedUserException
     * 
     * @return WorkGroup
     */
    @PutMapping("/organisation/{oid}/workgroup/{id}")
    public WorkGroup updateWorkGroup(@PathVariable Long oid, @PathVariable Long id,
            @RequestBody WorkGroup newWorkGroupInfo) throws UnauthenticatedException, UnauthorizedUserException {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userService.getUserByUsername(userDetails.getUsername());
        if (user == null)
            throw new UnauthenticatedException();

        if (!userRepo.findByOrganisationId(oid).contains(user))
            throw new UnauthorizedUserException();

        WorkGroup workGroup = workGroupService.updateWorkGroup(id, newWorkGroupInfo);
        if (workGroup == null)
            return null;

        return workGroup;
    }

    /**
     * Remove a book with the DELETE request to "/books/{id}" If there is no book
     * with the given "id", throw a BookNotFoundException
     * 
     * @param id
     */
    @DeleteMapping("/organisation/{oid}/workgroup/{id}")
    public void deleteWorkGroup(@PathVariable Long oid, @PathVariable Long id)
            throws UnauthenticatedException, UnauthorizedUserException {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userService.getUserByUsername(userDetails.getUsername());
        if (user == null)
            throw new UnauthenticatedException();

        if (!userRepo.findByOrganisationId(oid).contains(user))
            throw new UnauthorizedUserException();

        try {
            workGroupService.deleteWorkGroup(id);
        } catch (EmptyResultDataAccessException e) {
            throw new WorkGroupNotFoundException();
        }
    }

    @DeleteMapping("/organisation/{oid}/workgroup/{wgid}/employee/{id}")
    public void removeWorkGroupEmployee(@PathVariable Long oid, @PathVariable Long wgid, @PathVariable Long id) {
        User employee = userService.getUser(id);
        userService.quitWorkGroup(employee);
    }
}
