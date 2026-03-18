package dev.ironcorelabs.ironlog.security.service;

import java.util.UUID;

public interface RefreshTokenService {
    String createRefreshToken(String token, Long userId);
    boolean isRevoked(UUID jti);
    void logout(UUID jti);
    void logoutByUser(UUID jti, Long userId);
    void revokeAllSessions(Long userId);
}
