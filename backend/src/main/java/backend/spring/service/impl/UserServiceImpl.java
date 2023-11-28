package backend.spring.service.impl;

import backend.spring.dao.UserDao;
import backend.spring.model.User;
import backend.spring.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserDao userDao;

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUserByUserId(String userId) {
        return userDao.getUserByUserId(userId);
    }

    @Override
    public User registerUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public void modifyUser(String userId, User user) {
        userDao.updateUser(userId, user);
    }

    @Override
    public void removeUser(String userId) {
        userDao.deleteUser(userId);
    }
}