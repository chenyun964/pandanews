package sg.edu.smu.cs203.pandanews.service;

import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.smu.cs203.pandanews.model.Organisation;
import sg.edu.smu.cs203.pandanews.repository.OrganisationRepository;

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
    public Organisation addOrganisation(Organisation organisation){
        return organisations.save(organisation);
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
        organisations.deleteById(id);
    }
}
