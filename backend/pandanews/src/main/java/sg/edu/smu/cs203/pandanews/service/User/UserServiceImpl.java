package sg.edu.smu.cs203.pandanews.service.user;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import sg.edu.smu.cs203.pandanews.model.Organisation;
import sg.edu.smu.cs203.pandanews.model.WorkGroup;
import sg.edu.smu.cs203.pandanews.model.user.User;
import sg.edu.smu.cs203.pandanews.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository users;

    private PasswordEncoder bcryptEncoder;

    @Autowired
    public UserServiceImpl(UserRepository users, PasswordEncoder bcryptEncoder) {
        this.users = users;
        this.bcryptEncoder = bcryptEncoder;
    }

    @Override
    public User getUser(Long id) {
        return users.findById(id).orElse(null);
    }

    @Override
    public List<User> listUsers() {
        return users.findAll();
    }



    @Override
    public User updateUser(Long id, User newUser) {
        return users.findById(id).map(user -> {
            user.setName(newUser.getName());
            user.setContact(newUser.getContact());
            return users.save(user);
        }).orElse(null);
    }

    @Override
    public void deleteUser(Long id) {
        users.deleteById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return users.findByUsername(username).orElse(null);
    }

    public User updateUserCompany(Long id, Organisation organisation) {
        return users.findById(id).map(user -> {
            user.setOrganisation(organisation);
            return users.save(user);
        }).orElse(null);
    }

    @Override
    public User updateUserRole(User user, String role) {
        user.setAuthorities(role);
        return users.save(user);
    }


    //repeated
    @Override
    public User joinOrganisation(User user, Organisation organisation) {
        user.setOrganisation(organisation);
        users.save(user);
        return user;
    }


    @Override
    public User quitOrganisation(User user) {
        user.setOrganisation(null);
        return users.save(user);
    }

    @Override
    public User updateVaccine(User user) {
        return null;
    }

    public User joinWorkGroup(User user, WorkGroup workGroup){
        user.setWorkGroup(workGroup);
        users.save(user);
        return user;
    }

    @Override
    public void quitWorkGroup(User user){
        user.setWorkGroup(null);
        users.save(user);
    }

    @Override
    public User updateVaccine(User user){
        user.setVaccinated(true);
        users.save(user);
        return user;
    }
}
