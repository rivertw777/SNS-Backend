package backend.spring.dao;

import backend.spring.model.User;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    public List<User> getAllUsers();

    public User getUserByUserId(String userId);

    public User insertUser(User user);

    public void updateUser(String userId,User user);

    public void deleteUser(String userId);
}


