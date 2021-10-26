package sg.edu.smu.cs203.pandanews.service.policy;

import java.util.List;
import org.springframework.stereotype.Service;

import sg.edu.smu.cs203.pandanews.model.Policy;

@Service
public interface PolicyService {
    List<Policy> listPolicies(Long organisationId);
    Policy getPolicy(Long id);
    Policy addPolicy(Policy policy);
    Policy updatePolicy(Long id, Policy policy);
    void deletePolicy(Long id);
}
