package backend.spring.member.service;

import backend.spring.member.model.dto.request.MemberSignupRequest;
import backend.spring.member.model.entity.Member;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {

    void registerUser(MemberSignupRequest signupParam);

    List<Member> getSuggestions(Member member);

    void followMember(Member member, String suggestionName);

    void unfollowMember(Member member, String suggestionName);

    List<Member> getAllUsers();

}