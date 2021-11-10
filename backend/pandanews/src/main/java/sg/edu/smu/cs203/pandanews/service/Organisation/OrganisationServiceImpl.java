package sg.edu.smu.cs203.pandanews.service.organisation;

import java.util.List;

import org.springframework.stereotype.Service;
import sg.edu.smu.cs203.pandanews.model.user.User;
import sg.edu.smu.cs203.pandanews.model.Organisation;
import sg.edu.smu.cs203.pandanews.dto.OrganisationDTO;
import sg.edu.smu.cs203.pandanews.repository.OrganisationRepository;

import java.util.UUID;

@Service
public class OrganisationServiceImpl implements OrganisationService {

    private OrganisationRepository organisationRepo;

    public OrganisationServiceImpl(OrganisationRepository organisations) {
        this.organisationRepo = organisations;
    }

    @Override
    public Organisation getOrganisation(Long id) {
        return organisationRepo.findById(id).orElse(null);
    }

    @Override
    public List<Organisation> listOrganisations() {
        return organisationRepo.findAll();
    }

    @Override
    public Organisation addOrganisation(OrganisationDTO organisation, User user) {
        UUID uuid = UUID.randomUUID();
        String code = uuid.toString();
        Organisation newOrg = new Organisation();
        newOrg.setTitle(organisation.getTitle());
        newOrg.setAddress(organisation.getAddress());
        newOrg.setContact(organisation.getContact());
        newOrg.setCode(code);
        newOrg.setOwner(user);
        return organisationRepo.save(newOrg);
    }

    @Override
    public Organisation updateOrganisation(Long id, Organisation newOrganisation) {
        return organisationRepo.findById(id).map(organisation -> {
            organisation.setTitle(newOrganisation.getTitle());
            organisation.setAddress(newOrganisation.getAddress());
            organisation.setContact(newOrganisation.getContact());
            organisation.setCode(newOrganisation.getCode());
            return organisationRepo.save(organisation);
        }).orElse(null);
    }

    @Override
    public void deleteOrganisation(Long id) {
        organisationRepo.setOrganisationToNull(id);
        organisationRepo.deleteById(id);
    }

    @Override
    public Organisation getOrganisationByOwner(Long id) {
        return organisationRepo.findByOwnerId(id).orElse(null);
    }

    @Override
    public Organisation approveOrganisation(Long id) {
        return organisationRepo.findById(id).map(organisation -> {
            organisation.setStatus((byte) 1);
            return organisationRepo.save(organisation);
        }).orElse(null);
    }

    @Override
    public Organisation getOrganisationByCode(String code) {
        return organisationRepo.findByCode(code).orElse(null);
    }

    @Override
    public Organisation addEmployee(String code, User newEmployee) {
        System.out.println(newEmployee.getUsername());
        return organisationRepo.findByCode(code).map(organisation -> {
            List<User> employee = organisation.getEmployee();
            employee.add(newEmployee);
            organisation.setEmployee(employee);
            return organisationRepo.save(organisation);
        }).orElse(null);
    }
}
