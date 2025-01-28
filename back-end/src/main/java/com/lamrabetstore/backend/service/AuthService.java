package com.lamrabetstore.backend.service;

import com.lamrabetstore.backend.dto.AuthRequest;
import com.lamrabetstore.backend.dto.AuthResponse;
import com.lamrabetstore.backend.model.User;
import com.lamrabetstore.backend.repository.UserRepository;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponse authenticate(final AuthRequest request) {
        final var authToken = UsernamePasswordAuthenticationToken
                .unauthenticated(request.getEmail(), request.getPassword());

        final var authentication = authenticationManager
                .authenticate(authToken);

        final User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        final List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        System.out.println("Roles: " + roles);

        final var token = jwtService.generateToken(request.getEmail(), roles);

        return new AuthResponse(token);
    }

    public User registerUser(User user) {
        if (userRepository.existsByName(user.getName()) ||
                userRepository.existsByEmail(user.getEmail())) {

            throw new ValidationException(
                    "Username or Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
}