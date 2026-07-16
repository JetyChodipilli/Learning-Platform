package com.learning.platform.service;

import com.learning.platform.domain.AppUser;
import com.learning.platform.dto.AuthResponse;
import com.learning.platform.dto.LoginRequest;
import com.learning.platform.dto.RegisterRequest;
import com.learning.platform.repository.AppUserRepository;
import com.learning.platform.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager,
                       UserDetailsService userDetailsService,
                       JwtUtil jwtUtil,
                       AppUserRepository appUserRepository,
                       PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtUtil.generateToken(userDetails);
        
        AppUser user = appUserRepository.findByEmail(request.getEmail()).orElseThrow();
        
        return new AuthResponse(token, user.getId(), user.getRole(), user.getEmail());
    }

    @Transactional
    public AuthResponse registerParent(RegisterRequest request) {
        if (appUserRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already registered");
        }

        AppUser newUser = new AppUser();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole("PARENT"); // Force PARENT role
        newUser.setTimezone(request.getTimezone() != null ? request.getTimezone() : "UTC");

        AppUser savedUser = appUserRepository.save(newUser);

        UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getEmail());
        String token = jwtUtil.generateToken(userDetails);

        return new AuthResponse(token, savedUser.getId(), savedUser.getRole(), savedUser.getEmail());
    }
}
