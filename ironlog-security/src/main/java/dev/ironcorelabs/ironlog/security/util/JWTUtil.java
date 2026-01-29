package dev.ironcorelabs.ironlog.security.util;

import dev.ironcorelabs.ironlog.security.config.TokenProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
public class JWTUtil {

    public JWTUtil(TokenProperties tokenProperties) {
        this.expiration = tokenProperties.getExpiration();
        this.expirationRefresh = tokenProperties.getRefresh();
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(tokenProperties.getSecret()));
    }

    public String getToken(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);

        if (!StringUtils.hasText(token))
            return null;

        if (token.contains(BEARER))
            token = token.replaceAll(BEARER, "");

        return token;
    }

    protected String generateToken(UserDetails user, UUID jti, long expiration) {
        return Jwts.builder()
                .signWith(key)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .claim(JTI, jti)
                .compact();
    }

    public String generateAccessToken(UserDetails user, UUID jti) {
        return generateToken(user, jti, expiration * 60 * 60 * 1000);
    }

    public String generateRefreshToken(UserDetails user, UUID jti) {
        return generateToken(user, jti, expirationRefresh * 60 * 60 * 1000);
    }

    public Claims getClaims(String token) {
        if (!StringUtils.hasText(token))
        {
            return null;
        }

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Claims getClaimsIgnoredExpired(String token) {
        try {
            return getClaims(token);
        } catch(ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String getUserName(Claims claims) {
        return Optional.ofNullable(claims)
                .map(Claims::getSubject)
                .orElse("");
    }

    public Date getExpirationDate(Claims claims) {
        return Optional.ofNullable(claims)
                .map(Claims::getExpiration)
                .orElse(null);
    }

    public Date getIssuedAt(Claims claims) {
        return Optional.ofNullable(claims)
                .map(Claims::getIssuedAt)
                .orElse(null);
    }

    public boolean isValid(UserDetails user, Claims claims) {
        final Date expirationDate = getExpirationDate(claims);
        final String userName = getUserName(claims);

        return expirationDate != null && expirationDate.after(new Date(System.currentTimeMillis()))
                && user != null
                && StringUtils.hasText(user.getUsername())
                && userName.equals(user.getUsername());
    }

    public boolean isValidUserName(UserDetails user, Claims claims) {
        final String userName = getUserName(claims);

        return user != null
                && userName.equals(user.getUsername());
    }

    public UUID getJti(String token) {
        final Claims claims = getClaims(token);
        return getJti(claims);
    }

    public UUID getJti(Claims claims) {
        return Optional.ofNullable(claims)
                .map(cls -> cls.get(JTI, UUID.class))
                .orElse(null);
    }

    public LocalDateTime getExpirationDateDT(Claims claims) {
        return getExpirationDate(claims)
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public LocalDateTime getIssuedAtDT(Claims claims) {
        return getIssuedAt(claims)
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private final Long expiration;
    private final Long expirationRefresh;
    private final SecretKey key;

    private static final String AUTHORIZATION = "authorization";
    private static final String BEARER = "Bearer";

    private static final String JTI = "jti";
}
