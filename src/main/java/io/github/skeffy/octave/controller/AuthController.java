package io.github.skeffy.octave.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import io.github.skeffy.octave.security.GoogleTokenVerifier;
import io.github.skeffy.octave.security.jwt.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;
    private final GoogleTokenVerifier googleTokenVerifier;

    public AuthController(JwtService jwtService, GoogleTokenVerifier googleTokenVerifier) {
        this.jwtService = jwtService;
        this.googleTokenVerifier = googleTokenVerifier;
    }

    @PostMapping("/google")
    public ResponseEntity<String> authenticateWithGoogle(@RequestBody Map<String, String> payload,
                                                         HttpServletResponse response) {
        String googleToken = payload.get("token");
        GoogleIdToken.Payload googlePayload = googleTokenVerifier.verifyToken(googleToken);

        if (googlePayload == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Google token");
        }

        String email = googlePayload.getEmail();
        String accessToken = jwtService.generateAccessToken(email);
        String refreshToken = jwtService.generateRefreshToken(email);

        // Store refresh token as HTTP-only cookie
        Cookie refreshCookie = new Cookie("refresh_token", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(true);
        refreshCookie.setPath("/api/auth/refresh");
        refreshCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
        refreshCookie.setAttribute("SameSite", "Strict");

        response.addCookie(refreshCookie);

        return ResponseEntity.ok(accessToken);
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(@CookieValue(name = "refresh_token", required = false) String refreshToken) {
        if (refreshToken == null || !jwtService.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }

        String newAccessToken = jwtService.generateAccessToken(jwtService.extractUsername(refreshToken));
        return ResponseEntity.ok(newAccessToken);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("refresh_token", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/api/auth/refresh");
        cookie.setMaxAge(0); // Expire immediately
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }
}
