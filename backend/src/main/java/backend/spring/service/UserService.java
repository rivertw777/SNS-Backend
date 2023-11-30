package backend.spring.service;

import backend.spring.dao.dto.UserUpdateDto;
import backend.spring.model.User;
import java.util.Optional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public List<User> getAllUsers();

    public Optional<User> getUserById(Long userId);

    public void registerUser(User user);

    public void modifyUser(Long userId, UserUpdateDto updateParam);

    public void removeUser(Long userId);

}