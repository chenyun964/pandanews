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

import sg.edu.smu.cs203.pandanews.service.UserService;
import sg.edu.smu.cs203.pandanews.model.User;

@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService us){
        this.userService = us;
    }

    /**
     * List all books in the system
     * @return list of all books
     */
    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.listUsers();
    }

    /**
     * Search for book with the given id
     * If there is no book with the given "id", throw a BookNotFoundException
     * @param id
     * @return book with the given id
     */
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id){
        User user = userService.getUser(id);

        // Need to handle "book not found" error using proper HTTP status code
        // In this case it should be HTTP 404
        if(user == null) return null;
        return userService.getUser(id);

    }
    /**
     * Add a new book with POST request to "/books"
     * Note the use of @RequestBody
     * @param user
     * @return list of all books
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users")
    public User addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    /**
     * If there is no book with the given "id", throw a BookNotFoundException
     * @param id
     * @param newUserInfo
     * @return the updated, or newly added book
     */
    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User newUserInfo){
        User user = userService.updateUser(id, newUserInfo);
        if(user == null) return null;
        
        return user;
    }

    /**
     * Remove a book with the DELETE request to "/books/{id}"
     * If there is no book with the given "id", throw a BookNotFoundException
     * @param id
     */
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id){
        try{
            userService.deleteUser(id);
         }catch(EmptyResultDataAccessException e) {
            // throw new BookNotFoundException(id);
         }
    }
}
