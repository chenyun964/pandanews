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
import org.springframework.web.bind.annotation.CrossOrigin;

import sg.edu.smu.cs203.pandanews.exception.OrganisationNotFoundException;
import sg.edu.smu.cs203.pandanews.service.organisation.OrganisationService;
import sg.edu.smu.cs203.pandanews.service.user.UserService;
import sg.edu.smu.cs203.pandanews.dto.OrganisationDTO;
import sg.edu.smu.cs203.pandanews.model.user.User;
import sg.edu.smu.cs203.pandanews.model.Organisation;
import sg.edu.smu.cs203.pandanews.model.Policy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@CrossOrigin
public class OrganisationController {

    private OrganisationService organisationService;

    private UserService userService;

    @Autowired
    public OrganisationController(OrganisationService os, UserService userService) {
        this.organisationService = os;
        this.userService = userService;
    }

    /**
     * Lists all the organisations
     * 
     * @return List<Organisation>
     */
    @GetMapping("/organisation")
    public List<Organisation> getOrganisations() {
        return organisationService.listOrganisations();
    }

    /**
     * Creates a new organisation
     * 
     * @param organisation
     * @return Organisation
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/organisation")
    public Organisation addOrganisation(@RequestBody OrganisationDTO organisation) {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userService.getUserByUsername(userDetails.getUsername());
        if (user == null)
            return null;

        Organisation newOrg = organisationService.addOrganisation(organisation, user);
        if (newOrg == null)
            return null;

        userService.updateUserCompany(user.getId(), newOrg);
        return newOrg;
    }

    /**
     * Updates a specified organisation with new organisation details
     * 
     * @param id, @param newOrganisationInfo
     * @return Organisation
     */
    @PutMapping("/organisation/{id}")
    public Organisation updateOrganisation(@PathVariable Long id, @RequestBody Organisation newOrganisationInfo) {
        Organisation organisation = organisationService.updateOrganisation(id, newOrganisationInfo);
        if (organisation == null)
            return null;

        return organisation;
    }

    /**
     * Approves a new organisation
     * 
     * @param id
     * @return Organisation
     */
    @PutMapping("/organisation/approve/{id}")
    public Organisation approveOrganisation(@PathVariable Long id) {
        Organisation organisation = organisationService.approveOrganisation(id);
        if (organisation == null)
            return null;

        User user = organisation.getOwner();
        if (user == null)
            return null;

        userService.updateUserRole(user, "ROLE_OWNER");
        return organisation;
    }

    /**
     * Deletes the organisation with the specified id
     * Throws OrganisationNotFoundException
     * 
     * @param id
     * @return void
     */
    @DeleteMapping("/organisation/{id}")
    public void deleteOrganisation(@PathVariable Long id) throws OrganisationNotFoundException {
        try {
            organisationService.deleteOrganisation(id);
        } catch (EmptyResultDataAccessException e) {
            throw new OrganisationNotFoundException();
        }
    }

    @GetMapping("/organisation/employee")
    public List<User> getOrganisationEmployees() {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userService.getUserByUsername(userDetails.getUsername());
        if (user == null)
            return null;

        Organisation organisation = user.getOrganisation();
        if (organisation == null)
            return null;

        return organisation.getEmployee();
    }

    @PostMapping("/organisation/employee")
    public Organisation addOrganisationEmployee(@RequestBody Organisation org) {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User employee = userService.getUserByUsername(userDetails.getUsername());
        if (org.getCode() == null)
            return null;
        return organisationService.addEmployee(org.getCode(), employee);
    }

    @DeleteMapping("/organisation/employee/{id}")
    public User removeOgranisationEmployee(@PathVariable Long id) {

        User employee = userService.getUser(id);
        userService.updateUserRole(employee, "ROLE_USER");

        return userService.quitOrganisation(employee);
    }

    @GetMapping("/organisation/{code}")
    public Organisation addOrganisationEmployee(@PathVariable String code) {
        return organisationService.getOrganisationByCode(code);
    }

    @GetMapping("/organisation/policy")
    public List<Policy> getOrganisationPolicies() {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userService.getUserByUsername(userDetails.getUsername());
        if (user == null)
            return null;

        Organisation organisation = user.getOrganisation();
        if (organisation == null)
            return null;

        return organisation.getPolicy();
    }

    // @GetMapping("/organisation/workgroup")
    // public List<WorkGroup> getOrganisationWorkGroups() {
    // final UserDetails userDetails = (UserDetails)
    // SecurityContextHolder.getContext().getAuthentication()
    // .getPrincipal();
    //
    // User user = users.getUserByUsername(userDetails.getUsername());
    // if (user == null)
    // return null;
    //
    // Organisation organisation = user.getOrganisation();
    // if (organisation == null)
    // return null;
    //
    // return organisation.getWorkGroup();
    // }

    @PutMapping("/organisation/promote/{id}")
    public Organisation promoteEmployee(@PathVariable Long id) {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User manager = userService.getUserByUsername(userDetails.getUsername());
        if (manager == null)
            return null;

        User employee = userService.getUser(id);
        if (employee == null)
            return null;

        if (manager.getOrganisation() != employee.getOrganisation())
            return null;

        userService.updateUserRole(employee, "ROLE_MANAGER");
        return null;
    }

    @PutMapping("/organisation/demote/{id}")
    public Organisation demoteEmployee(@PathVariable Long id) {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User manager = userService.getUserByUsername(userDetails.getUsername());
        if (manager == null)
            return null;

        User employee = userService.getUser(id);
        if (employee == null)
            return null;

        if (manager.getOrganisation() != employee.getOrganisation())
            return null;

        userService.updateUserRole(employee, "ROLE_USER");
        return null;
    }
}
