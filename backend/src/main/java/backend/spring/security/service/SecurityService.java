package backend.spring.security.service;

import backend.spring.security.utils.TokenProvider;
import backend.spring.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Transactional
@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 이름을 가진 회원이 없습니다."));
    }

    // 인증 생성
    public Authentication createAuthentication(String username, String password){
        // 해당 이름을 가진 유저가 있는지 확인
        UserDetails user = loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    // jwt 토큰 생성
    public String generateToken(Authentication authentication) {
        return tokenProvider.generateToken(authentication);
    }

    // 요청으로 부터 토큰 데이터 가져오기
    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }

    // 토큰으로부터 이름 가져오기
    public String getUsernameFromToken(String token) {
        return tokenProvider.getUsernameFromToken(token);
    }

}