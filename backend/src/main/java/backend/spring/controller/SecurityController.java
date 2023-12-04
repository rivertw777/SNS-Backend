package backend.spring.controller;

import backend.spring.model.dto.UserLoginDto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/security")
public class SecurityController {

    @PostMapping("/tokens")
    public ResponseEntity<Void> createToken(@RequestBody UserLoginDto loginRequest) {
        System.out.println(loginRequest);
        return null;
    }
}
