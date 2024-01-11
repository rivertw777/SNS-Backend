package backend.spring.security.config;

import backend.spring.security.config.filter.JwtAuthenticationFilter;
import backend.spring.security.config.filter.JwtAuthorizationFilter;
import backend.spring.security.utils.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
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
    private final TokenProvider tokenProvider;

    // 보안 필터 체인 구성
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
                // 회원가입 경로, 이미지 경로
                .requestMatchers("/api/users", "/users/avatars/**", "/sns/photos/**").permitAll()
                .anyRequest().authenticated();

        return http.build();
    }

    // 특정 주소 무시
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 정적 리소스, Swagger 경로
        return (web) -> web.ignoring().requestMatchers(
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/v3/api-docs/**");
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
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, tokenProvider);
            JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(authenticationManager, tokenProvider);
            // 로그인 경로 수정
            jwtAuthenticationFilter.setFilterProcessesUrl("/api/users/login");
            http
                    .addFilter(jwtAuthenticationFilter)
                    .addFilter(jwtAuthorizationFilter);
        }
    }

}
