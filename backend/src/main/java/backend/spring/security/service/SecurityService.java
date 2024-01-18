package backend.spring.security.service;

import static backend.spring.exception.member.constants.MemberExceptionMessages.MEMBER_NAME_NOT_FOUND;

import backend.spring.member.model.entity.Member;
import backend.spring.security.model.CustomUserDetails;
import backend.spring.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;

    // 이름으로 회원 조회
    @Override
    public CustomUserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Member member = memberRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException(MEMBER_NAME_NOT_FOUND.getMessage()));

        return new CustomUserDetails(member);
    }

}