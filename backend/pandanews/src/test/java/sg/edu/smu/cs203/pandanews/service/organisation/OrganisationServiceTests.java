package sg.edu.smu.cs203.pandanews.service.organisation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sg.edu.smu.cs203.pandanews.dto.OrganisationDTO;
import sg.edu.smu.cs203.pandanews.model.Organisation;
import sg.edu.smu.cs203.pandanews.model.user.User;
import sg.edu.smu.cs203.pandanews.repository.OrganisationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrganisationServiceTests {
    @Mock
    private OrganisationRepository organisationRepo;

    @InjectMocks
    private OrganisationServiceImpl organisationService;



    @Test
    void addOrganisation_ReturnSavedOrg() {
        Organisation o = generateTestOrganisation("title");
        o.setAddress("address");
        o.setContact("contact");
        when(organisationRepo.save(any(Organisation.class))).thenReturn(o);

        OrganisationDTO organisationDTO = new OrganisationDTO("title", "address", "contact");
        Organisation org = organisationService.addOrganisation(organisationDTO, null);

        assertNotNull(org);
        assertEquals("title", org.getTitle());
        assertEquals("address", org.getAddress());
        assertEquals("contact", org.getContact());

    }

    @Test
    void getOrganisation_Success() {
        Organisation o = generateTestOrganisation("title");
        when(organisationRepo.findById(any(Long.class))).thenReturn(Optional.of(o));

        Organisation org = organisationService.getOrganisation(10L);

        assertNotNull(org);
        assertEquals("title", org.getTitle());
        verify(organisationRepo).findById(10L);
    }

    @Test
    void getOrganisation_Failure() {
        when(organisationRepo.findById(any(Long.class))).thenReturn(Optional.empty());

        Organisation org = organisationService.getOrganisation(10L);

        assertNull(org);
        verify(organisationRepo).findById(10L);
    }

    @Test
    void getOrganisationByCode_Success() {
        Organisation o = generateTestOrganisation("title");
        o.setCode("TEST");
        when(organisationRepo.findByCode(any(String.class))).thenReturn(Optional.of(o));

        Organisation org = organisationService.getOrganisationByCode("TEST");

        assertNotNull(org);
        assertEquals("TEST", org.getCode());
        verify(organisationRepo).findByCode("TEST");
    }

    @Test
    void getOrganisationByCode_Failure() {
        when(organisationRepo.findByCode(any(String.class))).thenReturn(Optional.empty());

        Organisation org = organisationService.getOrganisationByCode("TEST");

        assertNull(org);
        verify(organisationRepo).findByCode("TEST");
    }

    @Test
    void listOrganisations_Success() {
        Organisation o = generateTestOrganisation("title");
        List<Organisation> oList = new ArrayList<>();
        oList.add(o);
        when(organisationRepo.findAll()).thenReturn(oList);

        List<Organisation> result = organisationService.listOrganisations();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void updateOrg_ReturnUpdatedOrg() {
        Organisation o = generateTestOrganisation("title");
        when(organisationRepo.findById(any(Long.class))).thenReturn(Optional.of(o));
        Organisation o2 = generateTestOrganisation("title2");
        when(organisationRepo.save(any(Organisation.class))).thenReturn(o2);

        long id = 10L;
        Organisation newUser = organisationService.updateOrganisation(id, o);

        assertNotNull(newUser);
        assertEquals("title2", newUser.getTitle());
        verify(organisationRepo).findById(id);
        verify(organisationRepo).save(o);
    }

    @Test
    void updateOrg_ReturnNull() {
        Organisation o = generateTestOrganisation("title");
        Long id = 10L;
        when(organisationRepo.findById(id)).thenReturn(Optional.empty());
        Organisation newUser = organisationService.updateOrganisation(id, o);

        assertNull(newUser);
        verify(organisationRepo).findById(id);
    }


    @Test
    void addEmployee_ReturnUpdatedOrg() {
        User u1 = generateTestUser("u1");
        List<User> uList = new ArrayList<>();
        uList.add(u1);

        Organisation o = generateTestOrganisation("title");
        when(organisationRepo.findByCode(any(String.class))).thenReturn(Optional.of(o));
        o.setEmployee(new ArrayList<>());
        Organisation o2 = generateTestOrganisation("title");
        o2.setEmployee(uList);

        when(organisationRepo.save(any(Organisation.class))).thenReturn(o2);

        String id = "TEST";
        Organisation newOrg = organisationService.addEmployee(id, u1);

        assertNotNull(newOrg);
        assertEquals(1, newOrg.getEmployee().size());
        verify(organisationRepo).findByCode(id);
        verify(organisationRepo).save(o);
    }

    @Test
    void addEmployee_ReturnNull() {
        Organisation o = generateTestOrganisation("title");
        o.setEmployee(new ArrayList<>());
        User u1 = generateTestUser("u1");

        String id = "TEST";
        when(organisationRepo.findByCode(any(String.class))).thenReturn(Optional.empty());
        Organisation newOrg = organisationService.addEmployee(id, u1);

        assertNull(newOrg);
        verify(organisationRepo).findByCode(id);
    }

    @Test
    void deleteOrg_Success() {
        OrganisationServiceImpl mock = mock(OrganisationServiceImpl.class);
        doNothing().when(mock).deleteOrganisation(isA(Long.class));
        mock.deleteOrganisation(10L);
        verify(mock, times(1)).deleteOrganisation(10L);
    }

    @Test
    void getOrganisationByOwner_Success() {
        Organisation o = generateTestOrganisation("title");
        User u = generateTestUser("name");
        o.setOwner(u);
        when(organisationRepo.findByOwnerId(any(Long.class))).thenReturn(Optional.of(o));

        Organisation org = organisationService.getOrganisationByOwner(10L);

        assertNotNull(org);
        assertEquals("name", org.getOwner().getUsername());
        verify(organisationRepo).findByOwnerId(10L);
    }

    @Test
    void getOrganisationByOwner_Failure() {
        when(organisationRepo.findByOwnerId(any(Long.class))).thenReturn(Optional.empty());

        Organisation org = organisationService.getOrganisationByOwner(10L);

        assertNull(org);
        verify(organisationRepo).findByOwnerId(10L);
    }


    @Test
    void approveOrganisation_ReturnUpdatedOrg() {
        Organisation o = generateTestOrganisation("title");
        when(organisationRepo.findById(any(Long.class))).thenReturn(Optional.of(o));
        Organisation o2 = generateTestOrganisation("title");
        o2.setStatus((byte) 1);
        when(organisationRepo.save(any(Organisation.class))).thenReturn(o2);

        long id = 10L;
        Organisation newOrg = organisationService.approveOrganisation(id);

        assertNotNull(newOrg);
        assertEquals((byte) 1, newOrg.getStatus());
        verify(organisationRepo).findById(id);
        verify(organisationRepo).save(o);
    }

    @Test
    void approveOrganisation_ReturnNull() {
        Organisation o = generateTestOrganisation("title");
        Long id = 10L;
        when(organisationRepo.findById(id)).thenReturn(Optional.empty());
        Organisation newOrg = organisationService.approveOrganisation(id);

        assertNull(newOrg);
        verify(organisationRepo).findById(id);
    }


    private Organisation generateTestOrganisation(String name) {
        return new Organisation(name);
    }

    private User generateTestUser(String name) {
        return new User(name, "abc@gmail.com", "abc");
    }

}
