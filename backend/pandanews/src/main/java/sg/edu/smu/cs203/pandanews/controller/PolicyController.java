package sg.edu.smu.cs203.pandanews.controller;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.smu.cs203.pandanews.exception.UnauthenticatedException;
import sg.edu.smu.cs203.pandanews.exception.UnauthorizedUserException;
import sg.edu.smu.cs203.pandanews.service.Policy.PolicyService;
import sg.edu.smu.cs203.pandanews.model.Policy;
import sg.edu.smu.cs203.pandanews.service.User.UserService;
import sg.edu.smu.cs203.pandanews.repository.UserRepository;
import sg.edu.smu.cs203.pandanews.model.User.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
public class PolicyController {
    private PolicyService policyService;
    private UserService userService;
    private UserRepository users;

    public PolicyController(PolicyService policyService){
        this.policyService = policyService;
    }

    @GetMapping("/organisations/{oid}/policies")
    public List<Policy> getPolicies(@PathVariable Long oid) throws UnauthenticatedException, UnauthorizedUserException {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.getUserByUsername(userDetails.getUsername());
        if(user == null) throw new UnauthenticatedException();

        if(!users.findByOrganisationId(oid).contains(user)) throw new UnauthorizedUserException();

        return policyService.listPolicies(oid);
    }

    @GetMapping("/organisations/{oid}/policies/{id}")
    public Policy getPolicy(@PathVariable Long oid, @PathVariable Long id){
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.getUserByUsername(userDetails.getUsername());
        if(user == null) throw new UnauthenticatedException();

        if(!users.findByOrganisationId(oid).contains(user)) throw new UnauthorizedUserException();

        Policy policy = policyService.getPolicy(id);

        if(policy == null) return null;
        return policyService.getPolicy(id);

    }
    
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/organisations/{oid}/policies")
    public Policy addPolicy(@PathVariable Long oid, @RequestBody Policy policy){
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.getUserByUsername(userDetails.getUsername());
        if(user == null) throw new UnauthenticatedException();

        if(!users.findByOrganisationId(oid).contains(user)) throw new UnauthorizedUserException();

        return policyService.addPolicy(policy);
    }

    @PutMapping("/organisations/{oid}/policies/{id}")
    public Policy updatePolicy(@PathVariable Long oid, @PathVariable Long id, @RequestBody Policy newPolicyInfo){
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.getUserByUsername(userDetails.getUsername());
        if(user == null) throw new UnauthenticatedException();

        if(!users.findByOrganisationId(oid).contains(user)) throw new UnauthorizedUserException();

        Policy policy = policyService.updatePolicy(id, newPolicyInfo);
        if(policy == null) return null;
        
        return policy;
    }

    @DeleteMapping("/organisations/{oid}/policies/{id}")
    public void deletePolicy(@PathVariable Long oid, @PathVariable Long id){
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.getUserByUsername(userDetails.getUsername());
        if(user == null) throw new UnauthenticatedException();

        if(!users.findByOrganisationId(oid).contains(user)) throw new UnauthorizedUserException();

        try {
            policyService.deletePolicy(id);
        } catch(EmptyResultDataAccessException e) {
            // throw new PolicyNotFoundException(id);
        }
    }
}
