package backend.spring.repository;

import backend.spring.model.dto.UserUpdateDto;
import backend.spring.model.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    void save(User user);

    List<User> findAll();

    Optional<User> findById(Long userId);

    Optional<User> findByUserName(String userName);

    void update(Long userId, UserUpdateDto updateParam);

    void delete(Long userId);

}


