package sg.edu.smu.cs203.pandanews.service.policy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs203.pandanews.model.Organisation;
import sg.edu.smu.cs203.pandanews.model.Policy;
import sg.edu.smu.cs203.pandanews.repository.OrganisationRepository;
import sg.edu.smu.cs203.pandanews.repository.PolicyRepository;

import java.util.List;

@Service
public class PolicyServiceImpl implements PolicyService {

    @Autowired
    private PolicyRepository policyRepo;

    @Autowired
    private OrganisationRepository organisationRepo;

    @Override
    public Policy getPolicy(Long id, Long organisationId) {
        return findOrganisation(organisationId) == null ? null : policyRepo.findByIdAndOrganisationId(id, organisationId).orElse(null);
    }

    @Override
    public List<Policy> listPolicies(Long organisationId) {
        return findOrganisation(organisationId) == null ? null : policyRepo.findByOrganisationId(organisationId);
    }

    @Override
    public Policy addPolicy(Policy policy, Long organisationId) {
        Organisation o = findOrganisation(organisationId);
        if (o == null) return null;
        policy.setOrganisation(o);
        return policyRepo.save(policy);
    }

    @Override
    public Policy updatePolicy(Long id, Policy newPolicy) {
        return policyRepo.findById(id).map(policy -> {
            policy.setTitle(newPolicy.getTitle());
            policy.setMessage(newPolicy.getMessage());
            policy.setValidity(newPolicy.getValidity());
            return policyRepo.save(policy);
        }).orElse(null);
    }

    @Override
    public void deletePolicy(Long id) {
        policyRepo.deleteById(id);
    }

    private Organisation findOrganisation(Long oid) {
        return organisationRepo.findById(oid).orElse(null);
    }

}
