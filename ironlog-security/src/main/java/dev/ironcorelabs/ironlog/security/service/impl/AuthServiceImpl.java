package dev.ironcorelabs.ironlog.security.service.impl;

import dev.ironcorelabs.ironlog.core.security.SecurityUtils;
import dev.ironcorelabs.ironlog.restapi.openapi.model.LoginUser200Response;
import dev.ironcorelabs.ironlog.restapi.openapi.model.LoginUserRequest;
import dev.ironcorelabs.ironlog.restapi.openapi.model.RefreshUserRequest;
import dev.ironcorelabs.ironlog.security.dto.UserDetailsCustom;
import dev.ironcorelabs.ironlog.security.exception.InvalidToken;
import dev.ironcorelabs.ironlog.security.service.AuthService;
import dev.ironcorelabs.ironlog.security.service.RefreshTokenService;
import dev.ironcorelabs.ironlog.security.service.transactional.SecurityTransactionWrapper;
import dev.ironcorelabs.ironlog.security.util.JWTUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RefreshTokenService tokenService;
    private final AuthenticationManager authManager;
    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final SecurityUtils securityUtils;
    private final SecurityTransactionWrapper transactionWrapper;

    @Override
    @Transactional
    public LoginUser200Response login(LoginUserRequest login) {

        final Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail()
                , login.getPassword()));

        final UserDetailsCustom userDetails = (UserDetailsCustom) auth.getPrincipal();

        final UUID jti = UUID.randomUUID();

        final String refreshToken = jwtUtil.generateRefreshToken(userDetails, jti);

        tokenService.revokeAllSessions(userDetails.getId());

        tokenService.createRefreshToken(refreshToken, userDetails.getId());

        return new LoginUser200Response()
                .email(login.getEmail())
                .refreshToken(refreshToken)
                .token(jwtUtil.generateAccessToken(userDetails, jti));
    }

    @Override
    @Transactional
    public void logout(String refreshToken) {
        final Long currentUser = securityUtils.getCurrentUser();

        final Claims claims = jwtUtil.getClaims(refreshToken);

        final UUID jti = jwtUtil.getJti(claims);

        if (jti == null)
        {
            throw new InvalidToken("invalid.token");
        }

        tokenService.logoutByUser(jti, currentUser);
    }

    @Override
    @Transactional
    public LoginUser200Response refresh(RefreshUserRequest refresh) {

        final Claims claims = jwtUtil.getClaimsIgnoredExpired(refresh.getRefreshToken());

        if (claims == null)
        {
            throw new InvalidToken("invalid.token");
        }

        final UUID jti = jwtUtil.getJti(claims);

        if (jti == null)
        {
            throw new InvalidToken("invalid.token");
        }

        if (tokenService.isRevoked(jti))
        {
            throw new InvalidToken("invalid.token");
        }

        final String userName = jwtUtil.getUserName(claims);

        if (!StringUtils.hasText(userName))
        {
            transactionWrapper.revokeForce(jti);
            throw new InvalidToken("invalid.token");
        }

        final UserDetailsCustom userDetails = (UserDetailsCustom) userDetailsService.loadUserByUsername(userName);

        if (!jwtUtil.isValid(userDetails, claims))
        {
            transactionWrapper.revokeForce(jti);
            throw new InvalidToken("invalid.token");
        }

        tokenService.logout(jti);

        final UUID newJTI = UUID.randomUUID();

        final String newRefreshToken = jwtUtil.generateRefreshToken(userDetails, newJTI);

        tokenService.createRefreshToken(newRefreshToken, userDetails.getId());

        return new LoginUser200Response()
                .refreshToken(newRefreshToken)
                .token(jwtUtil.generateAccessToken(userDetails, newJTI))
                .email(userDetails.getUsername());
    }
}
