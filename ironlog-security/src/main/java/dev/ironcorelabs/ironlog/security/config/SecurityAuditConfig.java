package dev.ironcorelabs.ironlog.security.config;

import dev.ironcorelabs.ironlog.core.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class SecurityAuditConfig {

    private final SecurityUtils securityUtils;

    @Bean
    public AuditorAware<Long> auditorProvider() {
        return new SecurityAuditAware(securityUtils);
    }
}
