package dev.ironcorelabs.ironlog.security.util;

import dev.ironcorelabs.ironlog.core.exception.UnauthorizedException;
import dev.ironcorelabs.ironlog.core.security.SecurityUtils;
import dev.ironcorelabs.ironlog.security.dto.UserDetailsCustom;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.UUID;

@Component
public class SecurityUtilsImpl implements SecurityUtils {

    public Long getCurrentUserId() {
        return getCurrentUserDetailAuthenticateUser().getId();
    }

    public UserDetailsCustom getCurrentUserDetailAuthenticateUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
            || (authentication.getPrincipal() != null && authentication.getPrincipal().equals(ANONYMOUS_USER)))
        {
            throw new UnauthorizedException("session.not.active");
        }

        if (authentication.getPrincipal() instanceof UserDetailsCustom userDetails)
        {
            return userDetails;
        }

        throw new UnauthorizedException("session.not.active");
    }

    @Override
    public UUID getExternalId() {
        return getCurrentUserDetailAuthenticateUser().getExternalId();
    }

    @Override
    public boolean hasAuthority(String authority) {

        if (!StringUtils.hasText(authority))
        {
            return false;
        }

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated())
        {
            throw new UnauthorizedException("session.not.active");
        }

        return authentication.getAuthorities()
                .stream()
                .filter(auth -> auth.getAuthority() != null)
                .anyMatch(auth -> auth.getAuthority().equals(authority));
    }

    private static final String ANONYMOUS_USER = "anonymousUser";
}
