package dev.ironcorelabs.ironlog.security.config;

import dev.ironcorelabs.ironlog.security.exception.AbsentTokenException;
import dev.ironcorelabs.ironlog.security.exception.InvalidToken;
import dev.ironcorelabs.ironlog.security.service.RefreshTokenService;
import dev.ironcorelabs.ironlog.security.util.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserDetailsService userService;
    private final RefreshTokenService refreshTokenService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    public JWTFilter(JWTUtil jwtUtil, UserDetailsService userService
            , RefreshTokenService refreshTokenService
            , @Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    protected UsernamePasswordAuthenticationToken authenticate(HttpServletRequest request) {

        final String token = jwtUtil.getToken(request);

        if (!StringUtils.hasText(token))
        {
            throw new AbsentTokenException("absent.token");
        }

        final Claims claims = jwtUtil.getClaims(token);

        if (claims == null)
        {
            throw new InvalidToken("invalid.token");
        }

        final UUID jti = jwtUtil.getJti(claims);

        if (jti == null)
        {
            throw new InvalidToken("invalid.token");
        }

        if (refreshTokenService.isRevoked(jti))
        {
            throw new InvalidToken("invalid.token");
        }

        String userName = jwtUtil.getUserName(claims);

        if (!StringUtils.hasText(userName))
        {
            throw new InvalidToken("invalid.token");
        }

        final UserDetails user = userService.loadUserByUsername(userName);

        if (!jwtUtil.isValid(user, claims))
        {
            throw new InvalidToken("invalid.token");
        }

        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return authentication;
    }

    private void filter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final UsernamePasswordAuthenticationToken authentication = authenticate(request);

        if (authentication != null)
        {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filter(request, response, filterChain);
        } catch(Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}
