package backend.spring.service.impl;

import backend.spring.model.dto.UserSignupDto;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserDao userDao;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    // 회원 가입
    @Override
    public void registerUser(UserSignupDto signupParam) {
        // 이미 존재하는 사용자 이름이라면 예외 발생
        Optional<User> findUser = userDao.findByUserName(signupParam.userName());
        if (findUser.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자 이름입니다: " + signupParam.userName());
        }

        String encodedPassword = passwordEncoder.encode(signupParam.userPassword());
        User user = User.create(signupParam.userName(), encodedPassword);
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

        // 해당 이름을 가진 유저가 있는지 확인
        User user = (User) loadUserByUsername(username);

        // 비밀번호가 일치하는지 확인
        if (!user.isPasswordMatch(username, password)) {
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

}