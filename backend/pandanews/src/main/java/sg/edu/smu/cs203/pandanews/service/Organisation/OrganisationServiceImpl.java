package sg.edu.smu.cs203.pandanews.service.Organisation;

import java.util.List;

import org.springframework.stereotype.Service;
import sg.edu.smu.cs203.pandanews.model.User.User;
import sg.edu.smu.cs203.pandanews.model.Organisation;
import sg.edu.smu.cs203.pandanews.dto.OrganisationDTO;
import sg.edu.smu.cs203.pandanews.repository.OrganisationRepository;

import java.util.UUID;

@Service
public class OrganisationServiceImpl implements OrganisationService {

    private OrganisationRepository organisations;

    public OrganisationServiceImpl (OrganisationRepository organisations) {
        this.organisations = organisations;
    }

    @Override
    public Organisation getOrganisation(Long id) {
        return organisations.findById(id).orElse(null);
    }

    @Override
    public List<Organisation> listOrganisations(){
        return organisations.findAll();
    }

    @Override
    public Organisation addOrganisation(OrganisationDTO organisation, User user){
        UUID uuid = UUID.randomUUID();
        String code = uuid.toString();
        Organisation newOrg = new Organisation();
        newOrg.setTitle(organisation.getTitle());
        newOrg.setAddress(organisation.getAddress());
        newOrg.setContact(organisation.getContact());
        newOrg.setCode(code);
        newOrg.setOwner(user);
        return organisations.save(newOrg);
    }

    @Override
    public Organisation updateOrganisation(Long id, Organisation newOrganisation){
        return organisations.findById(id).map(organisation -> {
            organisation.setTitle(newOrganisation.getTitle());
            organisation.setAddress(newOrganisation.getAddress());
            organisation.setContact(newOrganisation.getContact());
            organisation.setCode(newOrganisation.getCode());
            return organisations.save(organisation);
        }).orElse(null);
    }

    @Override
    public void deleteOrganisation(Long id){
        organisations.setOrganisationToNull(id);
        organisations.deleteById(id);
    }

    @Override
    public Organisation getOrganisationByOwner(Long id){
        return organisations.findByOwnerId(id).orElse(null);
    }

    @Override
    public Organisation approveOrganisation(Long id){
        return organisations.findById(id).map(organisation -> {
            organisation.setStatus((byte)1);
            return organisations.save(organisation);
        }).orElse(null);
    }

    @Override
    public Organisation getOrganisationByCode(String code){
        return organisations.findByCode(code).orElse(null);
    }

    @Override
    public Organisation addEmployee(String code, User newEmployee){
        System.out.println(newEmployee.getUsername());
        return organisations.findByCode(code).map(organisation -> {
            List<User> employee = organisation.getEmployee();
            employee.add(newEmployee);
            organisation.setEmployee(employee);
            return organisations.save(organisation);
        }).orElse(null);
    }
}
