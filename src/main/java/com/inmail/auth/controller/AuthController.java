package com.inmail.auth.controller;

import com.inmail.api.ApiResponse;
import com.inmail.auth.dto.SignupRequest;
import com.inmail.auth.dto.UserSummary;
import com.inmail.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserSummary>> signup(@Valid @RequestBody SignupRequest req) {
        UserSummary result = authService.signup(req);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }
}
