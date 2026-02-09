package dev.ironcorelabs.ironlog.security.model.entities;

import dev.ironcorelabs.ironlog.security.model.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "sec", name = "app_user", indexes = {@Index(columnList = "enabled")})
public class AppUser {

    @Id
    @GeneratedValue(generator = "user_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_gen", schema = "sec", sequenceName = "user_seq")
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "role", nullable = false)
    private UserRole role;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "external_id", unique = true, nullable = false)
    private UUID externalId = UUID.randomUUID();

    @OneToMany(mappedBy = "user")
    private List<RefreshToken> refreshTokens;
}
