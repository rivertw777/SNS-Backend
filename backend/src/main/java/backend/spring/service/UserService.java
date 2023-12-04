package backend.spring.service;

import backend.spring.model.dto.UserUpdateDto;
import backend.spring.model.entity.User;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {

    void registerUser(User user);

    List<User> getAllUsers();

    Optional<User> getUserById(Long userId);

    void modifyUser(Long userId, UserUpdateDto updateParam);

    void removeUser(Long userId);

}