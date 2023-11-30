package backend.spring.dao;

import backend.spring.dao.dto.UserUpdateDto;
import backend.spring.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    public void save(User user);

    public void update(Long userId, UserUpdateDto userUpdateDto);

    public Optional<User> findById(Long userId);

    public List<User> findAll();

    public void delete(Long userId);
}


