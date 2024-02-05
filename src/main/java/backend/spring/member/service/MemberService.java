package backend.spring.member.service;

import backend.spring.member.dto.request.MemberSignupRequest;
import backend.spring.member.dto.response.SuggestionResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {

    void registerUser(MemberSignupRequest signupParam);

    List<SuggestionResponse> getSuggestions(Long memberId);

    void followMember(Long memberId, String suggestionName);

    void unfollowMember(Long memberId, String suggestionName);

}