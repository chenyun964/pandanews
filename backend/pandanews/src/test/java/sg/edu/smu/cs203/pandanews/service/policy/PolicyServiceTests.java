package sg.edu.smu.cs203.pandanews.service.policy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sg.edu.smu.cs203.pandanews.model.Organisation;
import sg.edu.smu.cs203.pandanews.model.Policy;
import sg.edu.smu.cs203.pandanews.repository.OrganisationRepository;
import sg.edu.smu.cs203.pandanews.repository.PolicyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PolicyServiceTests {
    @InjectMocks
    private PolicyServiceImpl policyService;
    @Mock
    private PolicyRepository policyRepo;
    @Mock
    private OrganisationRepository organisationRepo;

    @Test
    void addPolicy_Success() {
        Policy p = new Policy();
        when(organisationRepo.findById(any(Long.class))).thenReturn(Optional.of(new Organisation()));
        when(policyRepo.save(any(Policy.class))).thenReturn(p);
        Policy result = policyService.addPolicy(p, 10L);
        assertNotNull(result);
        verify(policyRepo).save(p);
        verify(organisationRepo).findById(10L);
    }

    @Test
    void addPolicy_Failure() {
        Policy p = new Policy();
        when(organisationRepo.findById(any(Long.class))).thenReturn((Optional.empty()));
        Policy result = policyService.addPolicy(p, 10L);

        assertNull(result);
        verify(organisationRepo).findById(10L);
    }

    @Test
    void getPolicy_Success() {

        Policy policy = new Policy();
        when(policyRepo.findByIdAndOrganisationId(any(Long.class), any(Long.class))).thenReturn(Optional.of(policy));
        when(organisationRepo.findById(any(Long.class))).thenReturn(Optional.of(new Organisation()));

        Policy result = policyService.getPolicy(10L, 10L);
        assertNotNull(result);
        verify(policyRepo).findByIdAndOrganisationId(10L, 10L);
        verify(organisationRepo).findById(10L);
    }

    @Test
    void getPolicy_Failure() {

        when(organisationRepo.findById(any(Long.class))).thenReturn(Optional.of(new Organisation()));
        Policy result = policyService.getPolicy(10L, 10L);
        assertNull(result);
        verify(organisationRepo).findById(10L);

    }

    @Test
    void deletePolicy_Success() {
        PolicyServiceImpl mock = mock(PolicyServiceImpl.class);
        doNothing().when(mock).deletePolicy(isA(Long.class));
        mock.deletePolicy(10L);
        verify(mock, times(1)).deletePolicy(10L);
    }

    @Test
    void updatePolicy_ReturnUpdatedResult() {
        Policy policy = new Policy();
        Policy updatePolicy = new Policy();

        updatePolicy.setMessage("updated");

        when(policyRepo.findById(any(Long.class))).thenReturn(Optional.of(policy));
        when(policyRepo.save(any(Policy.class))).thenReturn(updatePolicy);

        Policy result = policyService.updatePolicy(10L, updatePolicy);

        assertNotNull(result);
        assertEquals("updated", result.getMessage());
        verify(policyRepo).findById(10L);
        verify(policyRepo).save(policy);
    }

    @Test
    void updatePolicy_ReturnNull() {
        when(policyRepo.findById(any(Long.class))).thenReturn(Optional.empty());
        Policy result = policyService.updatePolicy(10L, new Policy());

        assertNull(result);
        verify(policyRepo).findById(10L);
    }

    @Test
    void listPolicies_Success() {
        List<Policy> policyList = new ArrayList<>();
        Policy s = new Policy();
        policyList.add(s);
        when(organisationRepo.findById(any(Long.class))).thenReturn(Optional.of(new Organisation()));
        when(policyRepo.findByOrganisationId(any(Long.class))).thenReturn(policyList);
        List<Policy> result = policyService.listPolicies(10L);
        assertNotNull(result);
        verify(policyRepo).findByOrganisationId(10L);
        verify(organisationRepo).findById(10L);

    }

}
