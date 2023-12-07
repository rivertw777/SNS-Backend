package backend.spring.repository;

import backend.spring.model.user.dto.UserUpdateDto;
import backend.spring.model.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    void save(User user);

    List<User> findAll();

    Optional<User> findById(Long userId);

    Optional<User> findByUserName(String username);

    void update(Long userId, UserUpdateDto updateParam);

    void delete(Long userId);

}


