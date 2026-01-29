package dev.ironcorelabs.ironlog.security.controller;

import dev.ironcorelabs.ironlog.restapi.openapi.api.AuthApi;
import dev.ironcorelabs.ironlog.restapi.openapi.model.*;
import org.springframework.http.ResponseEntity;

public class AuthController implements AuthApi {

    @Override
    public ResponseEntity<User> registerUser(RegisterUserRequest registerUserRequest) {
        return AuthApi.super.registerUser(registerUserRequest);
    }

    @Override
    public ResponseEntity<LoginUser200Response> refreshUser(RefreshUserRequest refreshUserRequest) {
        return AuthApi.super.refreshUser(refreshUserRequest);
    }

    @Override
    public ResponseEntity<Void> logout(RefreshUserRequest refreshUserRequest) {
        return AuthApi.super.logout(refreshUserRequest);
    }

    @Override
    public ResponseEntity<LoginUser200Response> loginUser(LoginUserRequest loginUserRequest) {
        return AuthApi.super.loginUser(loginUserRequest);
    }
}
