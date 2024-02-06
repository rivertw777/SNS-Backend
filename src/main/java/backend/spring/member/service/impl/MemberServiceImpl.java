package backend.spring.member.service.impl;

import static backend.spring.member.exception.constants.MemberExceptionMessages.DUPLICATE_NAME;
import static backend.spring.member.exception.constants.MemberExceptionMessages.MEMBER_ID_NOT_FOUND;
import static backend.spring.member.exception.constants.MemberExceptionMessages.MEMBER_NAME_NOT_FOUND;

import backend.spring.member.exception.DuplicateNameException;
import backend.spring.member.exception.MemberNotFoundException;
import backend.spring.member.dto.request.MemberSignupRequest;
import backend.spring.member.dto.response.SuggestionResponse;
import backend.spring.member.dto.response.mapper.SuggestionResponseMapper;
import backend.spring.member.model.entity.Member;
import backend.spring.member.model.Role;
import backend.spring.member.repository.MemberRepository;
import backend.spring.member.service.MemberService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Autowired
    private final SuggestionResponseMapper suggestionResponseMapper;

    @Value("${avatar.access.url}")
    private String accessUrl;

    // 회원 가입
    @Override
    public void registerUser(MemberSignupRequest signupParam) {
        // 이름 중복 검증
        validateDuplicateName(signupParam.name());

        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(signupParam.password());

        // 회원 저장
        Member member = Member.builder()
                .name(signupParam.name())
                .password(encodedPassword)
                .roles(Collections.singletonList(Role.USER))
                .build();
        memberRepository.save(member);

        // 아바타 이미지 경로 저장
        String avatarUrl = generateAvatarUrl(member.getMemberId());
        member.setAvatarUrl(avatarUrl);
    }

    // 이름의 중복 검증
    private void validateDuplicateName(String username){
        Optional<Member> findMember = memberRepository.findByName(username);
        if (findMember.isPresent()) {
            throw new DuplicateNameException(DUPLICATE_NAME.getMessage());
        }
    }

    // 아바타 url 생성 (임시)
    private String generateAvatarUrl(Long userId) {
        String avatarUrl = accessUrl + userId + ".png";
        return avatarUrl;
    }

    // 추천 회원 리스트 반환
    @Override
    public List<SuggestionResponse> getSuggestions(Long memberId) {
        // 로그인 중인 회원 조회
        Member currentMember = findMember(memberId);

        // 모든 회원 반환
        List<Member> members = memberRepository.findAll();

        // 추천 회원 응답 DTO 변환
        List<SuggestionResponse> SuggestionResponses = suggestionResponseMapper.toSuggestionResponses(members, currentMember);
        return SuggestionResponses;
    }

    // 회원 팔로우
    @Override
    public void followMember(Long memberId, String suggestionMemberName) {
        // 로그인 중인 회원 조회
        Member currentMember = findMember(memberId);

        //  추천 회원 조회
        Member suggestionMember = findSuggestionMember(suggestionMemberName);

        // 팔로잉 목록에 추가
        currentMember.getFollowingSet().add(suggestionMember);
        memberRepository.save(currentMember);
    }

    // 회원 언팔로우
    @Override
    public void unfollowMember(Long memberId, String suggestionMemberName) {
        // 로그인 중인 회원 조회
        Member currentMember = findMember(memberId);

        //  추천 회원 조회
        Member suggestionMember = findSuggestionMember(suggestionMemberName);

        // 팔로잉 목록에서 제거
        currentMember.getFollowingSet().remove(suggestionMember);
        memberRepository.save(currentMember);
    }

    // 이름으로 찾아서 반환
    private Member findSuggestionMember(String suggestionMemberName){
        Member suggestionMember = memberRepository.findByName(suggestionMemberName)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NAME_NOT_FOUND.getMessage()));
        return suggestionMember;
    }

    // id로 찿아서 반환
    private Member findMember(Long memberId){
        Member findMember = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_ID_NOT_FOUND.getMessage()));
        return findMember;
    }

}