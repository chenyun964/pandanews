package sg.edu.smu.cs203.pandanews.service;

import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.smu.cs203.pandanews.model.User;
import sg.edu.smu.cs203.pandanews.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository users;

    public UserServiceImpl (UserRepository users) {
        this.users = users;
    }

    @Override
    public User getUser(Long id) {
        return users.findById(id).map(user -> user).orElse(null);
    }

    @Override
    public List<User> listUsers(){
        return null;
    }

    @Override
    public User addUser(User user){
        return null;
    }

    @Override
    public User updateUser(Long id, User user){
        return null;
    }

    @Override
    public void deleteUser(Long id){
        System.out.println(id);
    }
}
