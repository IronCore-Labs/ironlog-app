package dev.ironcorelabs.ironlog.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "token")
public class TokenProperties {
    private Long expiration;
    private Long refresh;
    private String secret;
}
