package sg.edu.smu.cs203.pandanews.service.policy;

import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.smu.cs203.pandanews.model.Policy;
import sg.edu.smu.cs203.pandanews.repository.PolicyRepository;

@Service
public class PolicyServiceImpl implements PolicyService {

    private PolicyRepository policyRepo;

    public PolicyServiceImpl(PolicyRepository policies) {
        this.policyRepo = policies;
    }

    @Override
    public Policy getPolicy(Long id) {
        return policyRepo.findById(id).orElse(null);
    }

    @Override
    public List<Policy> listPolicies(Long organisationId) {
        return policyRepo.findByOrganisationId(organisationId);
    }

    @Override
    public Policy addPolicy(Policy policy) {
        return policyRepo.save(policy);
    }

    @Override
    public Policy updatePolicy(Long id, Policy newPolicy) {
        return policyRepo.findById(id).map(policy -> {
            policy.setMessage(newPolicy.getMessage());
            policy.setValidity(newPolicy.getValidity());
            return policyRepo.save(policy);
        }).orElse(null);
    }

    @Override
    public void deletePolicy(Long id) {
        policyRepo.deleteById(id);
    }

}
