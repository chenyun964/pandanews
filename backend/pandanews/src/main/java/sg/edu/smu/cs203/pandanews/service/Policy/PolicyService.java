package sg.edu.smu.cs203.pandanews.service.policy;

import org.springframework.stereotype.Service;
import sg.edu.smu.cs203.pandanews.model.Policy;

import java.util.List;

@Service
public interface PolicyService {
    List<Policy> listPolicies(Long organisationId);

    Policy getPolicy(Long id, Long organisationId);

    Policy addPolicy(Policy policy, Long organisationId);

    Policy updatePolicy(Long id, Policy policy);

    void deletePolicy(Long id);
}
