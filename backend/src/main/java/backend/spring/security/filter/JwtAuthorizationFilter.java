package backend.spring.security.filter;

import backend.spring.security.utils.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

// 인가 필터
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final TokenProvider tokenProvider;

    private final static String HEADER_STRING = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        super(authenticationManager);
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader(HEADER_STRING);
        if (header == null || !header.startsWith(TOKEN_PREFIX)){
            chain.doFilter(request, response);
            return;
        }

        String token = request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,"");
        Authentication authentication = tokenProvider.extractAuthentication(token);

        // 강제로 시큐리티의 세션에 접근하여 값 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }

}
