package sg.edu.smu.cs203.pandanews.service.policy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sg.edu.smu.cs203.pandanews.model.Policy;
import sg.edu.smu.cs203.pandanews.model.StatSummary;
import sg.edu.smu.cs203.pandanews.model.Statistic;
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

    @Test
    void addPolicy_Success() {
        Policy p = new Policy();
        when(policyRepo.save(any(Policy.class))).thenReturn(p);
        Policy result = policyService.addPolicy(p);

        assertNotNull(result);
        verify(policyRepo).save(p);
    }

    @Test
    void addPolicy_Failure() {
        Policy p = new Policy();
        when(policyRepo.save(any(Policy.class))).thenReturn(null);
        Policy result = policyService.addPolicy(p);

        assertNull(result);
        verify(policyRepo).save(p);
    }

    @Test
    void getPolicy_Success() {
        Policy policy = new Policy();
        when(policyRepo.findById(any(Long.class))).thenReturn(Optional.of(policy));

        Policy result = policyService.getPolicy(10L);
        assertNotNull(result);
        verify(policyRepo).findById(10L);
    }

    @Test
    void getPolicy_Failure() {
        when(policyRepo.findById(any(Long.class))).thenReturn(Optional.empty());

        Policy result = policyService.getPolicy(10L);
        assertNull(result);
        verify(policyRepo).findById(10L);
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
        updatePolicy.setValidity(true);

        when(policyRepo.findById(any(Long.class))).thenReturn(Optional.of(policy));
        when(policyRepo.save(any(Policy.class))).thenReturn(updatePolicy);

        Policy result = policyService.updatePolicy(10L, updatePolicy);

        assertNotNull(result);
        assertEquals("updated", result.getMessage());
        assertTrue(result.getValidity());
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

        when(policyRepo.findByOrganisationId(any(Long.class))).thenReturn(policyList);

        List<Policy> result = policyService.listPolicies(10L);

        assertNotNull(result);
        verify(policyRepo).findByOrganisationId(10L);
    }

}
