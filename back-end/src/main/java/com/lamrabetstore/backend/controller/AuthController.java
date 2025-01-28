package com.lamrabetstore.backend.controller;

import com.lamrabetstore.backend.dto.*;
import com.lamrabetstore.backend.mapper.UserRegistrationMapper;
import com.lamrabetstore.backend.service.AuthService;
import com.lamrabetstore.backend.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication endpoints")
public class AuthController {

    private final UserRegistrationMapper userRegistrationMapper;
    private final AuthService authService;

    @Operation(summary = "Login user", description = "Authenticates a user and returns JWT token")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody final AuthRequest authenticationRequestDto
    ) {
        return ResponseEntity.ok(
                authService.authenticate(authenticationRequestDto));
    }

    @Operation(summary = "Register user", description = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<RegistrationResponseDto> registerUser(
            @Valid @RequestBody final RegistrationRequestDto registrationDTO) {

        final var registeredUser = authService
                .registerUser(userRegistrationMapper.toUser(registrationDTO));

        return ResponseEntity.ok(
                userRegistrationMapper.toRegistrationResponseDto(registeredUser)
        );
    }
}