package se.amt.webshopauthgroup7.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import se.amt.webshopauthgroup7.dto.AuthResponse;
import se.amt.webshopauthgroup7.dto.LoginRequest;
import se.amt.webshopauthgroup7.dto.RegisterRequest;
import se.amt.webshopauthgroup7.dto.SessionResponse;
import se.amt.webshopauthgroup7.service.AuthService;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest registerRequest) {
        AuthResponse response = authService.register(registerRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse response = authService.login(loginRequest);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/jwks")
    public ResponseEntity<Map<String, Object>> jwks() {
        return ResponseEntity.ok(authService.publicJwkSet());
    }

    //kontrollerar vem som är inloggad och ger info tillbaka om user från befintlig jwt
    //authenticationprincipal är spring security som verifierar jwt
    @GetMapping("/me")
    public ResponseEntity<SessionResponse> me(
            @AuthenticationPrincipal Jwt jwt) {
        List<String> roles = jwt.getClaimAsStringList("roles");

        SessionResponse response = new SessionResponse(
                remainingSeconds(jwt),
                jwt.getSubject(),
                roles == null ? List.of() : roles
        );

        return ResponseEntity.ok(response);
    }

    //Återstående giltighetstid
    private long remainingSeconds(Jwt jwt) {
        Instant expiresAt = jwt.getExpiresAt();

        if (expiresAt == null) {
            return 0;
        }
        return Math.max(0, Duration.between(Instant.now(), expiresAt).getSeconds());
    }
}
