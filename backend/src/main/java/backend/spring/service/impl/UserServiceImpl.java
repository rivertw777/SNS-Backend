package backend.spring.service.impl;

import backend.spring.dao.UserDao;
import backend.spring.dao.dto.UserUpdateDto;
import backend.spring.model.User;
import backend.spring.service.UserService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserDao userDao;

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userDao.findById(userId);
    }

    @Override
    public void registerUser(User user) {
        userDao.save(user);
    }

    @Override
    public void modifyUser(Long userId, UserUpdateDto updateParam) {
        User findUser = userDao.findById(userId).orElseThrow();
        findUser.setUserName(updateParam.userName());
        findUser.setUserPassword(updateParam.userPassword());
    }

    @Override
    public void removeUser(Long userId) {
        userDao.delete(userId);
    }
}