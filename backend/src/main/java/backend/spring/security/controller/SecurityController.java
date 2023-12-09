package backend.spring.security.controller;

import backend.spring.config.jwt.dto.TokenResponse;
import backend.spring.member.model.dto.MemberLoginDto;

import backend.spring.security.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/security")
public class SecurityController {

    @Autowired
    private final SecurityService securityService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/tokens")
    public ResponseEntity<TokenResponse> login(@RequestBody MemberLoginDto loginRequest) {
        try {
            // 인증 권한 받기
            Authentication authentication = securityService.createAuthentication(loginRequest.username(),
                    loginRequest.password());
            authenticationManager.authenticate(authentication);

            // 토큰 전송
            String token = securityService.generateToken(authentication);
            TokenResponse tokenResponse = new TokenResponse(token);
            return ResponseEntity.ok(tokenResponse);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
