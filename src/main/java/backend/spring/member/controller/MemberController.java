package backend.spring.member.controller;

import backend.spring.member.dto.request.MemberSignupRequest;
import backend.spring.member.dto.request.SuggestionRequest;
import backend.spring.member.dto.response.SuggestionResponse;
import backend.spring.member.service.MemberService;
import backend.spring.security.utils.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    // 회원 가입
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 가입 성공"),
            @ApiResponse(responseCode = "409", description = "중복된 이름")})
    @Operation(summary = "회원 가입")
    @PostMapping("")
    public ResponseEntity<Void> signUp(@Valid @RequestBody MemberSignupRequest signupParam) {
        // 회원 가입
        memberService.registerUser(signupParam);
        return ResponseEntity.ok().build();
    }

    // 추천 회원 리스트 반환
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "추천 회원 조회 성공"),
            @ApiResponse(responseCode = "404", description = "리소스 찾을 수 없음.")})
    @Operation(summary = "추천 회원 조회")
    @GetMapping("/suggestions")
    public ResponseEntity<List<SuggestionResponse>> getSuggestions() {
        // 로그인 중인 회원 id
        Long memberId = securityUtil.getCurrentMemberId();

        // 추천 회원 응답 DTO 반환
        List<SuggestionResponse> SuggestionResponses = memberService.getSuggestions(memberId);
        return ResponseEntity.ok(SuggestionResponses);
    }

    // 회원 팔로우
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 팔로우 성공"),
            @ApiResponse(responseCode = "404", description = "리소스 찾을 수 없음.")})
    @Operation(summary = "회원 팔로우")
    @PostMapping("/follow")
    public ResponseEntity<Void> followMember(@Valid @RequestBody SuggestionRequest memberParam) {
        // 로그인 중인 회원 id
        Long memberId = securityUtil.getCurrentMemberId();

        // 유저 팔로우
        memberService.followMember(memberId, memberParam.name());
        return ResponseEntity.ok().build();
    }

    // 회원 언팔로우
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 팔로우 취소 성공"),
            @ApiResponse(responseCode = "404", description = "리소스 찾을 수 없음.")})
    @Operation(summary = "회원 언팔로우")
    @DeleteMapping("/follow")
    public ResponseEntity<Void> unfollowMember(@Valid @RequestBody SuggestionRequest memberParam) {
        // 로그인 중인 회원 id
        Long memberId = securityUtil.getCurrentMemberId();

        // 유저 언팔로우
        memberService.unfollowMember(memberId, memberParam.name());
        return ResponseEntity.ok().build();
    }

}

