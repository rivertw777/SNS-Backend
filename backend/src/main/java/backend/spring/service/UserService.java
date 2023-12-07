package backend.spring.service;

import backend.spring.model.user.dto.UserSignupDto;
import backend.spring.model.user.dto.UserUpdateDto;
import backend.spring.model.user.entity.User;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {

    void registerUser(UserSignupDto signupParam);

    List<User> getAllUsers();

    Optional<User> getUserById(Long userId);

    void modifyUser(Long userId, UserUpdateDto updateParam);

    void removeUser(Long userId);

    Authentication authenticate(String username, String password);

}