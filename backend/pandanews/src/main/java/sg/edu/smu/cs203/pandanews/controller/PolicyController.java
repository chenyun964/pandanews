package sg.edu.smu.cs203.pandanews.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import sg.edu.smu.cs203.pandanews.exception.PolicyNotFoundException;
import sg.edu.smu.cs203.pandanews.service.policy.PolicyService;
import sg.edu.smu.cs203.pandanews.service.user.UserService;

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

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    /**
     * List the policies under the given organisation
     * Throws UnauthenticatedException, UnauthorizedUserException
     * 
     * @param oid
     * @return List<Policy>
     */
    @GetMapping("/organisation/{oid}/policies")
    public List<Policy> getPolicies(@PathVariable Long oid) throws UnauthenticatedException, UnauthorizedUserException {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userService.getUserByUsername(userDetails.getUsername());
        if (user == null)
            throw new UnauthenticatedException();

        if (!userRepo.findByOrganisationId(oid).contains(user))
            throw new UnauthorizedUserException();

        return policyService.listPolicies(oid);
    }

    /**
     * Retrieves the policy with the given id under the given organisation
     * 
     * @param oid, @param id
     * @return Policy
     */
    @GetMapping("/organisation/{oid}/policies/{id}")
    public Policy getPolicy(@PathVariable Long oid, @PathVariable Long id) {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userService.getUserByUsername(userDetails.getUsername());
        if (user == null)
            throw new UnauthenticatedException();

        if (!userRepo.findByOrganisationId(oid).contains(user))
            throw new UnauthorizedUserException();

        Policy policy = policyService.getPolicy(id);

        if (policy == null)
            return null;
        return policyService.getPolicy(id);

    }

    /**
     * Adds a new policy to the given organisation
     * 
     * @param oid, @param policy
     * @return Policy
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/organisation/{oid}/policies")
    public Policy addPolicy(@PathVariable Long oid, @RequestBody Policy policy) {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userService.getUserByUsername(userDetails.getUsername());
        if (user == null)
            throw new UnauthenticatedException();

        if (!userRepo.findByOrganisationId(oid).contains(user))
            throw new UnauthorizedUserException();

        return policyService.addPolicy(policy);
    }

    /**
     * Updates the policy details of the given policy id of the given organisation
     * 
     * @param oid, @param id, @param newPolicyInfo
     * @return Policy
     */
    @PutMapping("/organisation/{oid}/policies/{id}")
    public Policy updatePolicy(@PathVariable Long oid, @PathVariable Long id, @RequestBody Policy newPolicyInfo) {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userService.getUserByUsername(userDetails.getUsername());
        if (user == null)
            throw new UnauthenticatedException();

        if (!userRepo.findByOrganisationId(oid).contains(user))
            throw new UnauthorizedUserException();

        Policy policy = policyService.updatePolicy(id, newPolicyInfo);
        if (policy == null)
            return null;

        return policy;
    }

    /**
     * Removes the policy with the given policy id of the given organisation
     * Throws UnauthenticatedException, UnauthorizedUserException, PolicyNotFoundException
     * 
     * @param oid, @param id
     * @return void
     */
    @DeleteMapping("/organisation/{oid}/policies/{id}")
    public void deletePolicy(@PathVariable Long oid, @PathVariable Long id) throws UnauthenticatedException, UnauthorizedUserException, PolicyNotFoundException {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userService.getUserByUsername(userDetails.getUsername());
        if (user == null)
            throw new UnauthenticatedException();

        if (!userRepo.findByOrganisationId(oid).contains(user))
            throw new UnauthorizedUserException();

        try {
            policyService.deletePolicy(id);
        } catch (EmptyResultDataAccessException e) {
            throw new PolicyNotFoundException();
        }
    }
}
