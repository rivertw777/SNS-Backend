package backend.spring.service;

import backend.spring.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public List<User> getAllUsers();

    public User getUserByUserId(String userId);

    public User registerUser(User user);

    public void modifyUser(String userId, User user);

    public void removeUser(String userId);
}