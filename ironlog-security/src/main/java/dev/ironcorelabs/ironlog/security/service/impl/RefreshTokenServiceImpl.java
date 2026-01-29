package dev.ironcorelabs.ironlog.security.service.impl;

import dev.ironcorelabs.ironlog.core.exception.RecordNotFoundException;
import dev.ironcorelabs.ironlog.core.exception.UnauthorizedException;
import dev.ironcorelabs.ironlog.security.exception.SessionAlreadyRevokedException;
import dev.ironcorelabs.ironlog.security.model.entities.AppUser;
import dev.ironcorelabs.ironlog.security.model.entities.RefreshToken;
import dev.ironcorelabs.ironlog.security.model.repositories.RefreshTokenRepository;
import dev.ironcorelabs.ironlog.security.model.repositories.UserRepository;
import dev.ironcorelabs.ironlog.security.service.RefreshTokenService;
import dev.ironcorelabs.ironlog.security.util.JWTUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository repository;

    private final JWTUtil jwtUtil;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public String createRefreshToken(String token, Long userId) {

        final Claims claims = jwtUtil.getClaims(token);
        final AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        final RefreshToken refreshToken = new RefreshToken();
        refreshToken.setAttempts(0L);
        refreshToken.setJti(jwtUtil.getJti(claims));
        refreshToken.setExpirationDate(jwtUtil.getExpirationDateDT(claims));
        refreshToken.setGenerationDate(jwtUtil.getIssuedAtDT(claims));
        refreshToken.setIsRevoked(Boolean.FALSE);
        refreshToken.setToken(token);
        refreshToken.setUser(user);

        repository.save(refreshToken);

        return token;
    }

    @Override
    public boolean isRevoked(UUID jti) {
        return repository.findByJti(jti)
                .map(RefreshToken::getIsRevoked)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));
    }

    @Override
    @Transactional
    public void logout(UUID jti) {
        final RefreshToken refreshToken = repository.findByJti(jti)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        if (refreshToken.getIsRevoked())
        {
            throw new SessionAlreadyRevokedException("session.revoked");
        }

        refreshToken.setIsRevoked(Boolean.TRUE);
        repository.save(refreshToken);
    }

    @Override
    @Transactional
    public void logoutByUser(UUID jti, Long userId) {
        final RefreshToken refreshToken = repository.findByJti(jti)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        if (refreshToken.getIsRevoked())
        {
            throw new SessionAlreadyRevokedException("session.revoked");
        }

        if (!refreshToken.getUser().getId().equals(userId))
        {
            throw new UnauthorizedException("invalid.session");
        }

        refreshToken.setIsRevoked(Boolean.TRUE);
        repository.save(refreshToken);
    }

    @Override
    @Transactional
    public void revokeAllSessions(Long userId) {
        repository.updateRevokedByUserId(userId, Boolean.TRUE);
    }
}
