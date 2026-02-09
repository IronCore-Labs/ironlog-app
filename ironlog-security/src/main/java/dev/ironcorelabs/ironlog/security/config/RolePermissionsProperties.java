package dev.ironcorelabs.ironlog.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@Configuration
@ConfigurationProperties(prefix = "security-rbac")
public class RolePermissionsProperties {
    private Map<String, Map<String, List<String>>> modules;

    public List<String> getPermissions(String role) {
        return modules.values()
                .stream()
                .map(map -> map.get(role))
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .distinct()
                .toList();
    }
}
