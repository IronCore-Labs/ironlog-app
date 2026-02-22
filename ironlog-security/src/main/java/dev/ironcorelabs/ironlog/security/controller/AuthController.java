package dev.ironcorelabs.ironlog.security.controller;

import dev.ironcorelabs.ironlog.restapi.openapi.api.AuthApi;
import dev.ironcorelabs.ironlog.restapi.openapi.model.*;
import dev.ironcorelabs.ironlog.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;
    private final UserService userService;

    @Override
    public ResponseEntity<User> registerUser(RegisterUserRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @Override
    public ResponseEntity<LoginUser200Response> refreshUser(RefreshUserRequest refreshUserRequest) {
        return ResponseEntity.ok(authService.refresh(refreshUserRequest));
    }

    @Override
    public ResponseEntity<Void> logout(RefreshUserRequest refreshUserRequest) {
        authService.logout(refreshUserRequest.getRefreshToken());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<LoginUser200Response> loginUser(LoginUserRequest loginUserRequest) {
        return ResponseEntity.ok(authService.login(loginUserRequest));
    }
}
