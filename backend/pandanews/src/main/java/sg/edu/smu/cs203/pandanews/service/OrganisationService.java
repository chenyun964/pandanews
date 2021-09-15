package sg.edu.smu.cs203.pandanews.service;

import java.util.List;
import org.springframework.stereotype.Service;

import sg.edu.smu.cs203.pandanews.model.Organisation;

@Service
public interface OrganisationService {
    List<Organisation> listOrganisations();
    Organisation getOrganisation(Long id);
    Organisation addOrganisation(Organisation organisation);
    Organisation updateOrganisation(Long id, Organisation organisation);

    /**
     * Change method's signature: do not return a value for delete operation
     * @param id
     */
    void deleteOrganisation(Long id);
}
