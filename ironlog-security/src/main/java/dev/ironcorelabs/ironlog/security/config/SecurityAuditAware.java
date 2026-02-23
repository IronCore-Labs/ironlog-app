package dev.ironcorelabs.ironlog.security.config;

import dev.ironcorelabs.ironlog.core.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;

import java.util.Optional;

@RequiredArgsConstructor
public class SecurityAuditAware implements AuditorAware<Long> {

    private final SecurityUtils securityUtils;

    @Override
    @NonNull
    public Optional<Long> getCurrentAuditor() {
        return Optional.ofNullable(securityUtils.getCurrentUserId());
    }
}
