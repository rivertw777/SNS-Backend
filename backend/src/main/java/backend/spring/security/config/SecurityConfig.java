package backend.spring.security.config;

import backend.spring.security.filter.JwtAuthenticationFilter;
import backend.spring.security.filter.JwtAuthorizationFilter;
import backend.spring.security.service.SecurityService;
import backend.spring.security.utils.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    private SecurityService securityService;

    private final TokenProvider tokenProvider;

    // 보안 필터 체인을 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // jwt 토큰 사용
                .csrf().disable()
                .cors()
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 커스텀 필터 적용
                .apply(new MyCustomDsl())
                // 요청 처리
                .and()
                .authorizeRequests()
                // 회원가입, 이미지 경로 허용
                .requestMatchers("/api/users", "/users/avatars/**","/instagram/photos/**").permitAll()
                .anyRequest().authenticated();

        return http.build();
    }

    // 빈 생성 시 스프링의 내부 동작으로 UserSecurityService와 PasswordEncoder가 자동으로 설정
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 커스텀 필터 설정
    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                    .addFilter(new JwtAuthenticationFilter(authenticationManager, tokenProvider))
                    .addFilter(new JwtAuthorizationFilter(authenticationManager, tokenProvider));

        }
    }

}
