package backend.spring.member.service.impl.dev;

import static backend.spring.exception.member.constants.MemberExceptionMessages.DUPLICATE_NAME;

import backend.spring.exception.member.DuplicateNameException;
import backend.spring.member.dto.request.MemberSignupRequest;
import backend.spring.member.model.entity.Member;
import backend.spring.member.model.entity.Role;
import backend.spring.member.repository.MemberRepository;
import backend.spring.member.service.filter.MemberFilter;
import backend.spring.member.service.impl.MemberServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Profile("dev")
@Service
public class MemberServiceDev extends MemberServiceImpl {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberServiceDev(MemberRepository memberRepository,
                            PasswordEncoder passwordEncoder,
                            MemberFilter memberFilter) {
        super(memberRepository, passwordEncoder, memberFilter);
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Value("${avatar.access.url}")
    private String accessUrl;

    // 회원 가입
    @Override
    public void registerUser(MemberSignupRequest signupParam) {
        // 이미 존재하는 사용자 이름이라면 예외 발생
        validateDuplicateName(signupParam.name());

        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(signupParam.password());

        // 일반 역할 부여
        List<Role> roles = new ArrayList<>();
        roles.add(Role.USER);

        // 회원 저장
        Member member = Member.builder()
                .name(signupParam.name())
                .password(encodedPassword)
                .roles(roles)
                .build();
        memberRepository.save(member);

        // 아바타 이미지 경로 저장
        String avatarUrl = generateAvatarUrl(member.getMemberId());
        member.setAvatarUrl(avatarUrl);
    }

    // 이름의 중복 검증
    private void validateDuplicateName(String username){
        Optional<Member> findUser = memberRepository.findByName(username);
        if (findUser.isPresent()) {
            throw new DuplicateNameException(DUPLICATE_NAME.getMessage());
        }
    }

    // 아바타 url 생성
    private String generateAvatarUrl(Long userId) {
        String avatarUrl = accessUrl + userId + ".png";
        return avatarUrl;
    }

}
