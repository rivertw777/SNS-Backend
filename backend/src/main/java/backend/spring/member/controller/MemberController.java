package backend.spring.member.controller;

import backend.spring.member.model.dto.request.MemberSignupRequest;
import backend.spring.member.model.dto.request.MemberUpdateRequest;
import backend.spring.member.model.dto.response.SuggestionResponse;
import backend.spring.member.model.dto.response.mapper.SuggestionResponseMapper;
import backend.spring.member.model.entity.Member;
import backend.spring.member.service.MemberService;
import backend.spring.security.service.SecurityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class MemberController {

    @Autowired
    private final MemberService memberService;
    @Autowired
    private final SecurityService securityService;
    @Autowired
    private final SuggestionResponseMapper suggestionResponseMapper;

    // 회원 가입
    @PostMapping("")
    public ResponseEntity<?> signUp(@Valid @RequestBody MemberSignupRequest signupParam, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            memberService.registerUser(signupParam);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 추천 이용자 리스트 반환
    @GetMapping("/suggestions")
    public ResponseEntity<?> getSuggestions(HttpServletRequest request) {
        String token = securityService.resolveToken(request);
        String username = securityService.getUsernameFromToken(token);

        // 추천 이용자 반환
        List<Member> suggestions = memberService.getSuggestions(username);

        List<SuggestionResponse> SuggestionResponses = getSuggestionResponses(suggestions);
        return ResponseEntity.ok(SuggestionResponses);
    }

    private List<SuggestionResponse> getSuggestionResponses(List<Member> members) {
        return members.stream()
                .map(member -> SuggestionResponseMapper.toMemberResponse(member, 100L))
                .collect(Collectors.toList());
    }


    // 유저 팔로우
    @PostMapping("/follow")
    public ResponseEntity<Void> followUser(HttpServletRequest request) {
        String token = securityService.resolveToken(request);
        String username = securityService.getUsernameFromToken(token);

        // 유저 팔로우
        //userService.followUser(username, followRequest.getUsername());

        return ResponseEntity.ok().build();
    }

    // 회원 전체 조회
    @GetMapping("")
    public ResponseEntity<List<Member>> getAllUsers() {
        List<Member> users = memberService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // 회원 단일 조회
    @GetMapping("/{userId}")
    public ResponseEntity<Member> getUserById(@PathVariable Long userId) {
        Optional<Member> user = memberService.getUserById(userId);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    // 회원 정보 수정
    @PutMapping("/{userId}")
    public ResponseEntity<?> modifyUser(@PathVariable Long userId, @Valid @RequestBody MemberUpdateRequest updateParam, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        //userService.modifyUser(userId, updateParam);
        return ResponseEntity.ok().build();
    }

    // 회원 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> removeUser(@PathVariable Long userId) {
        memberService.removeUser(userId);
        return ResponseEntity.ok().build();
    }

}

