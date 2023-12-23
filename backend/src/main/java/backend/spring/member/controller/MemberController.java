package backend.spring.member.controller;

import backend.spring.member.model.dto.request.MemberSignupRequest;
import backend.spring.member.model.dto.request.SuggestionRequest;
import backend.spring.member.model.dto.response.SuggestionResponse;
import backend.spring.member.model.dto.response.mapper.SuggestionResponseMapper;
import backend.spring.member.model.entity.Member;
import backend.spring.member.service.MemberService;
import backend.spring.security.utils.SecurityUtil;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class MemberController {

    @Autowired
    private final SecurityUtil securityUtil;
    @Autowired
    private final MemberService memberService;
    @Autowired
    private final SuggestionResponseMapper suggestionResponseMapper;

    // 회원 가입
    @PostMapping("")
    public ResponseEntity<?> signUp(@Valid @RequestBody MemberSignupRequest signupParam) {
        try {
            // 회원 등록
            memberService.registerUser(signupParam);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 추천 이용자 리스트 반환
    @GetMapping("/suggestions")
    public ResponseEntity<?> getSuggestions() {
        // 로그인중인 회원 조회
        Member member = securityUtil.getCurrentMember();

        // 추천 회원 반환
        List<Member> suggestions = memberService.getSuggestions(member);

        // 추천 회원 응답 변환
        List<SuggestionResponse> SuggestionResponses = suggestionResponseMapper.toSuggestionResponses(suggestions);
        return ResponseEntity.ok(SuggestionResponses);
    }


    // 유저 팔로우
    @PostMapping("/follow")
    public ResponseEntity<?> followMember(@Valid @RequestBody SuggestionRequest memberParam) {
        // 로그인중인 회원 조회
        Member member = securityUtil.getCurrentMember();

        // 유저 팔로우
        memberService.followMember(member, memberParam.name());
        return ResponseEntity.ok().build();
    }

    // 유저 언팔로우
    @DeleteMapping("/follow")
    public ResponseEntity<?> unfollowMember(@Valid @RequestBody SuggestionRequest memberParam) {
        // 로그인중인 회원 조회
        Member member = securityUtil.getCurrentMember();

        // 유저 언팔로우
        memberService.unfollowMember(member, memberParam.name());
        return ResponseEntity.ok().build();
    }

    // 회원 전체 조회
    @GetMapping("")
    public ResponseEntity<List<Member>> getAllUsers() {
        List<Member> members = memberService.getAllUsers();
        return ResponseEntity.ok(members);
    }

}

