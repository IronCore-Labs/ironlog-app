package dev.ironcorelabs.ironlog.security.service;

import dev.ironcorelabs.ironlog.restapi.openapi.api.AuthApi;
import dev.ironcorelabs.ironlog.restapi.openapi.model.LoginUser200Response;
import dev.ironcorelabs.ironlog.restapi.openapi.model.LoginUserRequest;
import dev.ironcorelabs.ironlog.restapi.openapi.model.RefreshUserRequest;

public interface AuthService {
    LoginUser200Response login(LoginUserRequest login);
    void logout(String refreshTokens);
    LoginUser200Response refresh(RefreshUserRequest refresh);
}
