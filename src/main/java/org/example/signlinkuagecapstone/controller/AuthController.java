package org.example.signlinkuagecapstone.controller;

import jakarta.validation.Valid;
import org.example.signlinkuagecapstone.dto.LoginRequest;
import org.example.signlinkuagecapstone.dto.LoginResponse;
import org.example.signlinkuagecapstone.dto.RegisterRequest;
import org.example.signlinkuagecapstone.entity.User;
import org.example.signlinkuagecapstone.repository.UserRepository;
import org.example.signlinkuagecapstone.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody RegisterRequest request) {

        String normalizedEmail = request.getEmail().trim().toLowerCase();
        if (userRepository.findByEmail(normalizedEmail).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT) // ✅ 409
                    .body(Map.of(
                            "message", "Email is already registered"
                    ));
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(normalizedEmail);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(normalizeRole(request.getRole()));

        try {
            User saved = userRepository.save(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "id", saved.getId(),
                    "email", saved.getEmail(),
                    "role", saved.getRole()
            ));
        } catch (Exception e) {
            // Return clear error to frontend instead of generic 500 with no body
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Failed to register user",
                    "error", e.getMessage()
            ));
        }
    }

    // ✅ JWT LOGIN: username/password -> token
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Extract username (email) and role from Authentication
        String username = authentication.getName();
        String role = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("USER")
                .replaceAll("^ROLE_", ""); // Remove ROLE_ prefix if present

        // Load user entity to get display name (first + last)
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found after authentication: " + username));

        String displayName = (user.getFirstName() != null ? user.getFirstName() : "")
                + (user.getLastName() != null ? " " + user.getLastName() : "");

        String token = jwtTokenProvider.generateToken(username, role);
        return new LoginResponse(token, displayName.trim());
    }

    // ✅ Optional: keep /me (works after JWT filter sets Authentication)
    @GetMapping("/me")
    public Map<String, Object> me(Authentication authentication) {
        String email = authentication.getName();
        String displayName = email;
        try {
            User user = userRepository.findByEmail(email).orElse(null);
            if (user != null) {
                displayName = ((user.getFirstName() != null ? user.getFirstName() : "") +
                        (user.getLastName() != null ? " " + user.getLastName() : "")).trim();
            }
        } catch (Exception ignored) {}

        return Map.of(
                "username", displayName,
                "email", authentication.getName(),
                "roles", authentication.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
        );
    }

    private String normalizeRole(String role) {
        if (role == null || role.isBlank()) {
            return "ROLE_USER";
        }

        String cleaned = role.trim().toUpperCase();
        if (!cleaned.startsWith("ROLE_")) {
            cleaned = "ROLE_" + cleaned;
        }

        if (!"ROLE_USER".equals(cleaned) && !"ROLE_ADMIN".equals(cleaned)) {
            throw new RuntimeException("Role must be USER or ADMIN");
        }

        return cleaned;
    }
}