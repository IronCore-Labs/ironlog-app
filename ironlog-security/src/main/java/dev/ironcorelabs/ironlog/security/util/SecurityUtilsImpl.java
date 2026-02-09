package dev.ironcorelabs.ironlog.security.util;

import dev.ironcorelabs.ironlog.core.exception.UnauthorizedException;
import dev.ironcorelabs.ironlog.core.security.AuthenticateUser;
import dev.ironcorelabs.ironlog.core.security.SecurityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SecurityUtilsImpl implements SecurityUtils {

    @Override
    public Long getCurrentUserId() {
        return getCurrentUserDetailAuthenticateUser().getId();
    }

    @Override
    public AuthenticateUser getCurrentUserDetailAuthenticateUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
            || authentication.getPrincipal().equals(ANONYMOUS_USER))
        {
            throw new UnauthorizedException("session.not.active");
        }

        if (authentication.getPrincipal() instanceof AuthenticateUser userDetails)
        {
            return userDetails;
        }

        throw new UnauthorizedException("session.not.active");
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
                .anyMatch(auth -> auth.getAuthority().equals(authority));
    }

    private static final String ANONYMOUS_USER = "anonymousUser";
}
