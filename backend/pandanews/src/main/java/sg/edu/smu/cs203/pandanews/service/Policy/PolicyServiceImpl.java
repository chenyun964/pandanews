package sg.edu.smu.cs203.pandanews.service.policy;

import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.smu.cs203.pandanews.model.Policy;
import sg.edu.smu.cs203.pandanews.repository.PolicyRepository;

@Service
public class PolicyServiceImpl implements PolicyService {

    private PolicyRepository policies;

    public PolicyServiceImpl(PolicyRepository policies) {
        this.policies = policies;
    }

    @Override
    public Policy getPolicy(Long id) {
        return policies.findById(id).orElse(null);
    }

    @Override
    public List<Policy> listPolicies(Long organisationId) {
        return policies.findByOrganisationId(organisationId);
    }

    @Override
    public Policy addPolicy(Policy policy) {
        return policies.save(policy);
    }

    @Override
    public Policy updatePolicy(Long id, Policy newPolicy) {
        return policies.findById(id).map(policy -> {
            policy.setMessage(newPolicy.getMessage());
            policy.setValidity(newPolicy.getValidity());
            return policies.save(policy);
        }).orElse(null);
    }

    @Override
    public void deletePolicy(Long id) {
        policies.deleteById(id);
    }

}
