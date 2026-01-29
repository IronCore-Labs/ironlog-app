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
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserDetailsService userService;
    private final RefreshTokenService refreshTokenService;

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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final UsernamePasswordAuthenticationToken authentication = authenticate(request);

        if (authentication != null)
        {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
