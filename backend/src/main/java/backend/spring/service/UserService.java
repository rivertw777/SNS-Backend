package backend.spring.service;

import backend.spring.dao.dto.UserUpdateDto;
import backend.spring.model.User;
import java.util.Optional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    void registerUser(User user);

    List<User> getAllUsers();

    Optional<User> getUserById(Long userId);

    void modifyUser(Long userId, UserUpdateDto updateParam);

    void removeUser(Long userId);

}