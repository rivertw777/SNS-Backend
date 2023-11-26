package backend.spring.controller;

import backend.spring.model.User;
import backend.spring.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userid}")
    public User getUserByUserId(@PathVariable  String userid) {
        return userService.getUserByUserId(userid);
    }

    @PostMapping("")
    public User registerUser(@RequestBody User user) {

        return userService.registerUser(user);
    }

    @PutMapping("/{userid}")
    public void modifyUser(@PathVariable String userid, @RequestBody User user) {

        userService.modifyUser(userid, user);
    }

    @DeleteMapping("/{userid}")
    public void removeUser(@PathVariable String userid) {
        userService.removeUser(userid);
    }
}
