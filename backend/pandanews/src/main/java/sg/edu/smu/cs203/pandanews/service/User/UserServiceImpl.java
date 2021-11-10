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

    private UserRepository userRepo;

    private PasswordEncoder bcryptEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepo, PasswordEncoder bcryptEncoder) {
        this.userRepo = userRepo;
        this.bcryptEncoder = bcryptEncoder;
    }

    @Override
    public User getUser(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public List<User> listUsers() {
        return userRepo.findAll();
    }



    @Override
    public User updateUser(Long id, User newUser) {
        return userRepo.findById(id).map(user -> {
            user.setName(newUser.getName());
            user.setContact(newUser.getContact());
            return userRepo.save(user);
        }).orElse(null);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username).orElse(null);
    }

    public User updateUserCompany(Long id, Organisation organisation) {
        return userRepo.findById(id).map(user -> {
            user.setOrganisation(organisation);
            return userRepo.save(user);
        }).orElse(null);
    }

    @Override
    public User updateUserRole(User user, String role) {
        user.setAuthorities(role);
        return userRepo.save(user);
    }


    //repeated
    @Override
    public User joinOrganisation(User user, Organisation organisation) {
        user.setOrganisation(organisation);
        userRepo.save(user);
        return user;
    }


    @Override
    public User quitOrganisation(User user) {
        user.setOrganisation(null);
        return userRepo.save(user);
    }


    public User joinWorkGroup(User user, WorkGroup workGroup){
        user.setWorkGroup(workGroup);
        userRepo.save(user);
        return user;
    }

    @Override
    public User quitWorkGroup(User user){
        //return the user who get quited the workgroup
        user.setWorkGroup(null);
<<<<<<< Updated upstream
        return users.save(user);
=======
        userRepo.save(user);
>>>>>>> Stashed changes
    }

    @Override
    public User updateVaccine(User user){
        user.setVaccinated(true);
        userRepo.save(user);
        return user;
    }
}
