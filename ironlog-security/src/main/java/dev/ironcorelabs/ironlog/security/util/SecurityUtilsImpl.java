package dev.ironcorelabs.ironlog.security.util;

import dev.ironcorelabs.ironlog.core.exception.UnauthorizedException;
import dev.ironcorelabs.ironlog.core.security.SecurityUtils;
import dev.ironcorelabs.ironlog.security.dto.UserDetailsCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;

@Component("sec")
@RequiredArgsConstructor
public class SecurityUtilsImpl implements SecurityUtils {

    public Long getCurrentUserId() {
        return getAuthenticatedDetails().getId();
    }

    // Método de control de acceso al Principal: La "Aduana Central"
    private UserDetailsCustom getAuthenticatedDetails() {
        return Optional.ofNullable(getCurrentUserDetailAuthenticateUser())
                .orElseThrow(() -> new UnauthorizedException("session.invalid"));
    }

    public UserDetailsCustom getCurrentUserDetailAuthenticateUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
            || (authentication.getPrincipal() != null && authentication.getPrincipal().equals(ANONYMOUS_USER)))
        {
            return null;
        }

        if (authentication.getPrincipal() instanceof UserDetailsCustom userDetails)
        {
            return userDetails;
        }

        return null;
    }

    @Override
    public UUID getExternalId() {
        return getAuthenticatedDetails().getExternalId();
    }

    @Override
    public boolean hasRole(String role) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || !StringUtils.hasText(role))
        {
            return false;
        }

        String roleToCompare = role.startsWith("ROLE_") ? role : "ROLE_" + role;

        return authentication.getAuthorities()
                .stream()
                .filter(auth -> auth.getAuthority() != null)
                .anyMatch(auth -> auth.getAuthority().equals(roleToCompare));
    }

    private static final String ANONYMOUS_USER = "anonymousUser";
}
