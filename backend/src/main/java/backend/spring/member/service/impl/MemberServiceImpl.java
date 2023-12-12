package backend.spring.member.service.impl;

import backend.spring.member.model.dto.MemberSignupDto;
import backend.spring.member.model.entity.Member;
import backend.spring.member.repository.MemberRepository;
import backend.spring.member.service.MemberService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    // 회원 가입
    @Override
    public void registerUser(MemberSignupDto signupParam) {
        // 이미 존재하는 사용자 이름이라면 예외 발생
        validateDuplicateUser(signupParam.username());

        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(signupParam.password());
        Member user = Member.create(signupParam.username(), encodedPassword);
        user.generateAvatarUrl();
        memberRepository.save(user);
        user.generateAvatarUrl();
    }

    private void validateDuplicateUser(String username){
        Optional<Member> findUser = memberRepository.findByUsername(username);
        if (findUser.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자 이름입니다: " + username);
        }
    }

    @Override
    public List<Member> getAllUsers() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> getUserById(Long userId) {
        return memberRepository.findById(userId);
    }

    //@Override
    //public void modifyUser(Long userId, UserUpdateDto updateParam) {
    //    userRepository.update(userId, updateParam);
    //}

    @Override
    public void removeUser(Long userId) {
        memberRepository.deleteById(userId);
    }

}