package backend.spring.service.impl;

import backend.spring.repository.UserDao;
import backend.spring.model.dto.UserUpdateDto;
import backend.spring.model.entity.User;
import backend.spring.service.UserService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserDao userDao;

    @Override
    public void registerUser(User user) {
        userDao.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userDao.findById(userId);
    }

    @Override
    public void modifyUser(Long userId, UserUpdateDto updateParam) {
        userDao.update(userId, updateParam);
    }

    @Override
    public void removeUser(Long userId) {
        userDao.delete(userId);
    }

    // 이름으로 사용자 불러오기
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userDao.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userName));
    }

    // 비밀번호 일치 여부 확인
    public Authentication authenticate(String username, String password) {
        User user = (User) loadUserByUsername(username);
        if (!user.isCredentialsValid(username, password)) {
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

}