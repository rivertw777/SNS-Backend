package backend.spring.security.utils;

import backend.spring.security.model.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    public static Long getCurrentMemberId() {
        // 인증 정보 조회
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 정보가 없을 시
        if (authentication == null || authentication.getName() == null) {
            throw new IllegalArgumentException("인증 정보가 없습니다.");
        }

        // 인증 정보에서 회원 정보 가져오기
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return customUserDetails.member().getMemberId();
    }

}