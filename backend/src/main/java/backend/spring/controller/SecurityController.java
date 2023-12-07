package backend.spring.controller;

import backend.spring.config.jwt.TokenProvider;
import backend.spring.config.jwt.dto.TokenResponse;
import backend.spring.model.user.dto.UserLoginDto;

import backend.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserService userService;
    @Autowired
    private final TokenProvider tokenProvider;
    @Autowired
    private final AuthenticationManager authenticationManager;

    @PostMapping("/tokens")
    public ResponseEntity<TokenResponse> createToken(@RequestBody UserLoginDto loginRequest) {
        try {
            // 인증 권한 받기
            Authentication authentication = userService.authenticate(loginRequest.username(),
                    loginRequest.password());
            authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 토큰 발급
            String token = tokenProvider.generateToken(authentication);
            TokenResponse tokenResponse = new TokenResponse(token);

            return ResponseEntity.ok(tokenResponse);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
