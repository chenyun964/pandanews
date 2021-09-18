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

import sg.edu.smu.cs203.pandanews.service.OrganisationService;
import sg.edu.smu.cs203.pandanews.model.Organisation;

@RestController
public class OrganisationController {
    private OrganisationService organisationService;

    public OrganisationController(OrganisationService os){
        this.organisationService = os;
    }

    /**
     * List all books in the system
     * @return list of all books
     */
    @GetMapping("/organisations")
    public List<Organisation> getOrganisations(){
        return organisationService.listOrganisations();
    }

    /**
     * Search for book with the given id
     * If there is no book with the given "id", throw a BookNotFoundException
     * @param id
     * @return book with the given id
     */
    @GetMapping("/organisations/{id}")
    public Organisation getOrganisation(@PathVariable Long id){
        Organisation organisation = organisationService.getOrganisation(id);

        // Need to handle "book not found" error using proper HTTP status code
        // In this case it should be HTTP 404
        if(organisation == null) return null;
        return organisationService.getOrganisation(id);

    }
    /**
     * Add a new book with POST request to "/books"
     * Note the use of @RequestBody
     * @param organisation
     * @return list of all books
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/organisations")
    public Organisation addOrganisation(@RequestBody Organisation organisation){
        return organisationService.addOrganisation(organisation);
    }

    /**
     * If there is no book with the given "id", throw a BookNotFoundException
     * @param id
     * @param newOrganisationInfo
     * @return the updated, or newly added book
     */
    @PutMapping("/organisations/{id}")
    public Organisation updateOrganisation(@PathVariable Long id, @RequestBody Organisation newOrganisationInfo){
        Organisation organisation = organisationService.updateOrganisation(id, newOrganisationInfo);
        if(organisation == null) return null;
        
        return organisation;
    }

    /**
     * Remove a book with the DELETE request to "/books/{id}"
     * If there is no book with the given "id", throw a BookNotFoundException
     * @param id
     */
    @DeleteMapping("/organisations/{id}")
    public void deleteOrganisation(@PathVariable Long id){
        try{
            organisationService.deleteOrganisation(id);
         }catch(EmptyResultDataAccessException e) {
            // throw new BookNotFoundException(id);
         }
    }
}
