package backend.spring.dao;

import backend.spring.dao.dto.UserUpdateDto;
import backend.spring.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    void save(User user);

    List<User> findAll();

    Optional<User> findById(Long userId);

    void update(Long userId, UserUpdateDto updateParam);

    void delete(Long userId);

}


