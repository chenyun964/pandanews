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

import sg.edu.smu.cs203.pandanews.service.OrganisationService;
import sg.edu.smu.cs203.pandanews.service.UserService;
import sg.edu.smu.cs203.pandanews.service.UserServiceImpl;
import sg.edu.smu.cs203.pandanews.dto.OrganisationDTO;
import sg.edu.smu.cs203.pandanews.model.User;
import sg.edu.smu.cs203.pandanews.model.Organisation;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@CrossOrigin
public class OrganisationController {

    private OrganisationService orgService;

    private UserService users;

    public OrganisationController(OrganisationService os, UserService users){
        this.orgService = os;
        this.users = users;
    }

    /**
     * List all books in the system
     * @return list of all books
     */
    @GetMapping("/organisation")
    public List<Organisation> getOrganisations(){
        return orgService.listOrganisations();
    }

    /**
     * Search for book with the given id
     * If there is no book with the given "id", throw a BookNotFoundException
     * @param id
     * @return book with the given id
     */
    // @GetMapping("/organisation/{id}")
    // public Organisation getOrganisation(@PathVariable Long id){
    //     Organisation organisation = orgService.getOrganisation(id);

    //     // Need to handle "book not found" error using proper HTTP status code
    //     // In this case it should be HTTP 404
    //     if(organisation == null) return null;
    //     return orgService.getOrganisation(id);
    // }

    /**
     * Add a new book with POST request to "/books"
     * Note the use of @RequestBody
     * @param organisation
     * @return list of all books
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/organisation")
    public Organisation addOrganisation(@RequestBody OrganisationDTO organisation){
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
        
        User user = users.getUserByUsername(userDetails.getUsername());
        if(user == null) return null;

        Organisation newOrg = orgService.addOrganisation(organisation, user);
        if(newOrg == null) return null;

        users.updateUserCompany(user.getId(), newOrg);
        return newOrg;
    }

    /**
     * If there is no book with the given "id", throw a BookNotFoundException
     * @param id
     * @param newOrganisationInfo
     * @return the updated, or newly added book
     */
    @PutMapping("/organisation/{id}")
    public Organisation updateOrganisation(@PathVariable Long id, @RequestBody Organisation newOrganisationInfo){
        Organisation organisation = orgService.updateOrganisation(id, newOrganisationInfo);
        if(organisation == null) return null;
        
        return organisation;
    }

    @PutMapping("/organisation/approve/{id}")
    public Organisation approveOrganisation(@PathVariable Long id){
        Organisation organisation = orgService.approveOrganisation(id);
        if(organisation == null) return null;

        User user = organisation.getOwner();
        if(user == null) return null;

        users.updateUserRole(user, "ROLE_OWNER");
        return organisation;
    }


    /**
     * Remove a book with the DELETE request to "/books/{id}"
     * If there is no book with the given "id", throw a BookNotFoundException
     * @param id
     */
    @DeleteMapping("/organisation/{id}")
    public void deleteOrganisation(@PathVariable Long id){
        try{
            orgService.deleteOrganisation(id);
         }catch(EmptyResultDataAccessException e) {
            // throw new BookNotFoundException(id);
         }
    }

    @GetMapping("/organisations/employee")
    public List<User> getOrganisationEmployees(){
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
        
        User user = users.getUserByUsername(userDetails.getUsername());
        if(user == null) return null;

        Organisation organisation = user.getOrganisation();
        if(organisation == null) return null;

        return organisation.getEmployee();
    }

    @PostMapping("/organisation/employee")
    public Organisation addOgranisationEmployee(@RequestBody Organisation org){
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();

        User employee = users.getUserByUsername(userDetails.getUsername());
        if(org.getCode() == null) return null;
        return orgService.addEmployee(org.getCode(), employee);
    }

    @GetMapping("/organisation/{code}")
    public Organisation addOgranisationEmployee(@PathVariable String code){
        return orgService.getOrganisationByCode(code);
    }
}