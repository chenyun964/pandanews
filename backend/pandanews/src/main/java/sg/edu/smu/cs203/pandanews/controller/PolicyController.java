package sg.edu.smu.cs203.pandanews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sg.edu.smu.cs203.pandanews.exception.OrganisationNotFoundException;
import sg.edu.smu.cs203.pandanews.model.Policy;
import sg.edu.smu.cs203.pandanews.service.policy.PolicyService;

import java.util.List;

@RestController
@CrossOrigin
public class PolicyController {
    @Autowired
    private PolicyService policyService;


    @GetMapping("/organisation/{oid}/policies")
    public List<Policy> getPolicies(@PathVariable Long oid) {
        List<Policy> result = policyService.listPolicies(oid);
        if (result == null) throw new OrganisationNotFoundException();
        return result;
    }

    @GetMapping("/organisation/{oid}/policies/{id}")
    public Policy getPolicy(@PathVariable Long oid, @PathVariable Long id) {
        Policy p = policyService.getPolicy(id, oid);
        if (p == null) throw new OrganisationNotFoundException();
        return p;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/organisation/{oid}/policies")
    public Policy addPolicy(@PathVariable Long oid, @RequestBody Policy policy) {
        Policy result = policyService.addPolicy(policy, oid);
        if (result == null) {
            throw new OrganisationNotFoundException();
        }
        return result;
    }

    @PutMapping("/organisation/policies/{id}")
    public Policy updatePolicy(@PathVariable Long id, @RequestBody Policy newPolicyInfo) {
        return policyService.updatePolicy(id, newPolicyInfo);
    }

    @DeleteMapping("/organisation/policies/{id}")
    public void deletePolicy(@PathVariable Long id) {
        policyService.deletePolicy(id);
    }
}
