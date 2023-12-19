package backend.spring.member.service;

import backend.spring.member.model.dto.request.MemberSignupRequest;
import backend.spring.member.model.entity.Member;
import java.util.Optional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {

    void registerUser(MemberSignupRequest signupParam);

    List<Member> getSuggestions(String username);

    List<Member> getAllUsers();

    Optional<Member> getUserById(Long userId);

    void removeUser(Long userId);

    void followMember(String username, String username1);
}