package dev.ironcorelabs.ironlog.security.service;

import dev.ironcorelabs.ironlog.security.model.entities.AppUser;
import io.jsonwebtoken.Claims;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenService {
    String createRefreshToken(String token, Long userId);
    boolean isRevoked(UUID jti);
    void logout(UUID jti);
    void logoutByUser(UUID jti, Long userId);
    void revokeAllSessions(Long userId);
}
