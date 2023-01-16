package com.training.security.controller;

import com.training.security.dto.LoginDTO;
import com.training.security.dto.TokenDTO;
import com.training.security.auth.AuthenticationService;
import com.training.security.dto.RegisterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public void register(@RequestBody RegisterDTO request) {
        authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
