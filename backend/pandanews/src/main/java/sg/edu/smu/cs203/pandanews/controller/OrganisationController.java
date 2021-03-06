package sg.edu.smu.cs203.pandanews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import sg.edu.smu.cs203.pandanews.dto.OrganisationDTO;
import sg.edu.smu.cs203.pandanews.model.Organisation;
import sg.edu.smu.cs203.pandanews.model.Policy;
import sg.edu.smu.cs203.pandanews.model.user.User;
import sg.edu.smu.cs203.pandanews.service.organisation.OrganisationService;
import sg.edu.smu.cs203.pandanews.service.user.UserService;

import java.util.List;

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
     * List all books in the system
     * 
     * @return list of all books
     */
    @GetMapping("/organisation")
    public List<Organisation> getOrganisations() {
        return organisationService.listOrganisations();
    }

    /**
     * Search for book with the given id If there is no book with the given "id",
     * throw a BookNotFoundException
     * 
     * @param id
     * @return book with the given id
     */
    // @GetMapping("/organisation/{id}")
    // public Organisation getOrganisation(@PathVariable Long id){
    // Organisation organisation = orgService.getOrganisation(id);

    // // Need to handle "book not found" error using proper HTTP status code
    // // In this case it should be HTTP 404
    // if(organisation == null) return null;
    // return orgService.getOrganisation(id);
    // }

    /**
     * Add a new book with POST request to "/books" Note the use of @RequestBody
     * 
     * @param organisation
     * @return list of all books
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
     * If there is no book with the given "id", throw a BookNotFoundException
     * 
     * @param id
     * @param newOrganisationInfo
     * @return the updated, or newly added book
     */
    @PutMapping("/organisation/{id}")
    public Organisation updateOrganisation(@PathVariable Long id, @RequestBody Organisation newOrganisationInfo) {
        Organisation organisation = organisationService.updateOrganisation(id, newOrganisationInfo);
        if (organisation == null)
            return null;

        return organisation;
    }

    @PutMapping("/organisation/approval/{id}")
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
     * Remove a book with the DELETE request to "/books/{id}" If there is no book
     * with the given "id", throw a BookNotFoundException
     * 
     * @param id
     */
    @DeleteMapping("/organisation/{id}")
    public void deleteOrganisation(@PathVariable Long id) {
        try {
            organisationService.deleteOrganisation(id);
        } catch (EmptyResultDataAccessException e) {
            // throw new BookNotFoundException(id);
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

    @PutMapping("/organisation/promotion/{id}")
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

    @PutMapping("/organisation/demotion/{id}")
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
