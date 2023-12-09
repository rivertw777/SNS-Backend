package backend.spring.member.service;

import backend.spring.member.model.dto.MemberSignupDto;
import backend.spring.member.model.entity.Member;
import java.util.Optional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {

    void registerUser(MemberSignupDto signupParam);

    List<Member> getAllUsers();

    Optional<Member> getUserById(Long userId);

    //void modifyUser(Long userId, UserUpdateDto updateParam);

    void removeUser(Long userId);

}