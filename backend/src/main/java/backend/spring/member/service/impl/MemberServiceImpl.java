package backend.spring.member.service.impl;

import static backend.spring.exception.member.constants.MemberExceptionMessages.DUPLICATE_NAME;
import static backend.spring.exception.member.constants.MemberExceptionMessages.MEMBER_ID_NOT_FOUND;
import static backend.spring.exception.member.constants.MemberExceptionMessages.MEMBER_NAME_NOT_FOUND;

import backend.spring.exception.member.MemberNotFoundException;
import backend.spring.member.dto.request.MemberSignupRequest;
import backend.spring.member.model.entity.Member;
import backend.spring.member.repository.MemberRepository;
import backend.spring.member.service.MemberService;
import backend.spring.member.service.filter.MemberFilter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public abstract class MemberServiceImpl implements MemberService {

    @Value("${avatar.access.url}")
    private String accessUrl;

    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final MemberFilter memberFilter;

    // 회원 가입
    public abstract void registerUser(MemberSignupRequest signupParam);


    // 추천 회원 리스트 반환
    @Override
    public List<Member> getSuggestions(Long memberId) {
        // 로그인 중인 회원 조회
        Member member = findMember(memberId);

        // 모든 회원 반환
        List<Member> members = memberRepository.findAll();

        // 필터링 거쳐서 추천 이용자 리스트 반환
        List<Member> suggestions = memberFilter.toSuggestions(members, member);
        return suggestions;
    }

    // 회원 팔로우
    @Override
    public void followMember(Long memberId, String suggestionMemberName) {
        // 로그인 중인 회원 조회
        Member member = findMember(memberId);

        //  추천 회원 조회
        Member suggestionMember = findSuggestionMember(suggestionMemberName);

        // 팔로잉 목록에 추가
        member.getFollowingSet().add(suggestionMember);
        memberRepository.save(member);
    }

    // 회원 언팔로우
    @Override
    public void unfollowMember(Long memberId, String suggestionMemberName) {
        // 로그인 중인 회원 조회
        Member member = findMember(memberId);

        //  추천 회원 조회
        Member suggestionMember = findSuggestionMember(suggestionMemberName);

        // 팔로잉 목록에서 제거
        member.getFollowingSet().remove(suggestionMember);
        memberRepository.save(member);
    }


    // 모든 회원 조회
    @Override
    public List<Member> getAllUsers() {
        return memberRepository.findAll();
    }

    // 이름으로 찾아서 반환
    private Member findSuggestionMember(String suggestionMemberName){
        Member suggestionMember = memberRepository.findByName(suggestionMemberName)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NAME_NOT_FOUND.getMessage()));
        return suggestionMember;
    }

    // id로 찿아서 반환
    private Member findMember(Long memberId){
        Member member = (Member) memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_ID_NOT_FOUND.getMessage()));
        return member;
    }

}