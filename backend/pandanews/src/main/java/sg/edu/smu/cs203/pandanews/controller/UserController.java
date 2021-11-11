package sg.edu.smu.cs203.pandanews.controller;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.smu.cs203.pandanews.service.organisation.OrganisationService;
import sg.edu.smu.cs203.pandanews.service.workgroup.WorkGroupService;
import sg.edu.smu.cs203.pandanews.service.user.UserService;
import sg.edu.smu.cs203.pandanews.model.user.User;
import sg.edu.smu.cs203.pandanews.exception.UserNotFoundException;
import sg.edu.smu.cs203.pandanews.model.Organisation;
import sg.edu.smu.cs203.pandanews.model.WorkGroup;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@CrossOrigin
public class UserController {
    private UserService userService;
    private WorkGroupService workGroupService;
    private OrganisationService organisationService;

    @Autowired
    public UserController(UserService userService, OrganisationService organisationService,
            WorkGroupService workGroupService) {
        this.userService = userService;
        this.organisationService = organisationService;
        this.workGroupService = workGroupService;
    }

    /**
     * Lists all users in the system
     * 
     * @return List<User>
     */
    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.listUsers();
    }

    /**
     * Gets the current logged in user's profile
     * 
     * @return User
     */
    @GetMapping("/users/profile")
    public User getUserProfile() {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.getUserByUsername(userDetails.getUsername());
        if (user == null)
            return null;
        return user;
    }

    /**
     * Updates current user profile with new user info
     * 
     * @param newUserInfo
     * @return User
     */
    @PutMapping("/users/profile")
    public User updateUser(@RequestBody User newUserInfo) {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userService.getUserByUsername(userDetails.getUsername());
        if (user == null)
            return null;
        System.out.println(user.getId());
        user = userService.updateUser(user.getId(), newUserInfo);
        if (user == null)
            return null;

        return user;
    }

    /**
     * Removes a user with the id specified If there is no user with the given id,
     * Throws a UserNotFoundException
     * 
     * @param id
     */
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) throws UserNotFoundException {
        try {
            userService.deleteUser(id);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException();
        }
    }

    /**
     * Retrieves the organisation of the current user
     * 
     * @return Organisation
     */
    @GetMapping("/users/organisation")
    public Organisation getUserOrganisation() {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userService.getUserByUsername(userDetails.getUsername());
        if (user == null)
            return null;

        return user.getOrganisation();
    }

    /**
     * Adds the current logged in user to the organisation specified
     * 
     * @param organisation
     * @return User
     */
    @PostMapping("/users/organisation")
    public User addUserOrganisation(@RequestBody Organisation organisation) {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userService.getUserByUsername(userDetails.getUsername());
        if (user == null)
            return null;

        Organisation org = organisationService.getOrganisationByCode(organisation.getCode());
        if (org == null)
            return null;

        return userService.joinOrganisation(user, org);
    }

    /**
     * Retrieves the work group of the current user
     * 
     * @return WorkGroup
     */
    @GetMapping("/users/workgroup")
    public WorkGroup getWorkGroup() {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userService.getUserByUsername(userDetails.getUsername());
        if (user == null)
            return null;

        return user.getWorkGroup();
    }

    /**
     * Adds a user to the work group specified
     * 
     * @param workGroup, @param user
     * @return User
     */
    @PostMapping("/users/workgroup")
    public User addUserWorkGroup(@RequestBody WorkGroup workGroup, @RequestBody User user) {
        user = userService.getUserByUsername(user.getUsername());
        if (user == null)
            return null;

        WorkGroup wg = workGroupService.getWorkGroup(workGroup.getId());
        if (wg == null)
            return null;

        return userService.joinWorkGroup(user, wg);
    }

    /**
     * Updates the current logged in user's vaccine status
     * 
     * @return User
     */
    @PutMapping("/users/vaccine")
    public User updateUserVaccine() {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userService.getUserByUsername(userDetails.getUsername());
        if (user == null)
            return null;

        return userService.updateVaccine(user);
    }
}
