package backend.spring.controller;

import backend.spring.service.SecurityService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/security")
public class SecurityController {
    @Autowired
    private final SecurityService securityService;

    @GetMapping("/tokens")
    public Map<String, Object> generateToken(@RequestParam("subject") String subject) {
        String token = securityService.createToken(subject, (2 * 1000 * 60));
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        return response;
    }

    @GetMapping("/subjects")
    public Map<String, Object> getSubjectFromToken(@RequestParam("token") String token) {
        String subject = securityService.getSubject(token);
        Map<String, Object> response = new HashMap<>();
        response.put("subject", subject);
        return response;
    }

}