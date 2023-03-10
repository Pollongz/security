package com.training.security.auth;

import com.training.security.dto.LoginDTO;
import com.training.security.dto.RegisterDTO;
import com.training.security.dto.TokenDTO;
import com.training.security.entity.User;
import com.training.security.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public void register(RegisterDTO request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(request.getRoles())
                .build();

        userRepository.save(user);
    }

    public TokenDTO login(LoginDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var jwtRefreshToken = jwtService.generateRefreshToken(user);
        return TokenDTO.builder()
                .token(jwtToken)
                .refreshToken(jwtRefreshToken)
                .build();
    }

    public TokenDTO refreshToken(String refreshToken) {
        String newToken = refreshToken.substring(7);
        String userEmail = jwtService.extractClaim(newToken, Claims::getSubject);

        var user = userRepository.findByEmail(userEmail)
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var jwtRefreshToken = jwtService.generateRefreshToken(user);
        return TokenDTO.builder()
                .token(jwtToken)
                .refreshToken(jwtRefreshToken)
                .build();
    }
}
