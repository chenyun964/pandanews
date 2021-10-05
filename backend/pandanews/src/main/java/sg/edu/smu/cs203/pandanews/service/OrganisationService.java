package sg.edu.smu.cs203.pandanews.service;

import java.util.List;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs203.pandanews.model.User;
import sg.edu.smu.cs203.pandanews.model.Organisation;
import sg.edu.smu.cs203.pandanews.dto.OrganisationDTO;
import sg.edu.smu.cs203.pandanews.model.User;

@Service
public interface OrganisationService {
    List<Organisation> listOrganisations();
    Organisation getOrganisation(Long id);
    Organisation addOrganisation(OrganisationDTO organisation, User user);
    Organisation updateOrganisation(Long id, Organisation organisation);

    /**
     * Change method's signature: do not return a value for delete operation
     * @param id
     */
    void deleteOrganisation(Long id);

    Organisation getOrganisationByOwner(Long id);
    Organisation approveOrganisation(Long id);
    Organisation getOrganisationByCode(String code);
    Organisation addEmployee(String code, User employee);
}
