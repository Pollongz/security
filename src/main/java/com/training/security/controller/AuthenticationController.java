package com.training.security.controller;

import com.training.security.dto.LoginDTO;
import com.training.security.dto.TokenDTO;
import com.training.security.auth.AuthenticationService;
import com.training.security.dto.RegisterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/token")
    public ResponseEntity<TokenDTO> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }
}
