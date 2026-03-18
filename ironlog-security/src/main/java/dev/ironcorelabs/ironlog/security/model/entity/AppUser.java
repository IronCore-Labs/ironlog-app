package dev.ironcorelabs.ironlog.security.model.entity;

import dev.ironcorelabs.ironlog.core.model.entity.BaseEntity;
import dev.ironcorelabs.ironlog.security.model.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(schema = "sec", name = "app_user", indexes = {@Index(columnList = "enabled")})
public class AppUser extends BaseEntity {

    @Id
    @GeneratedValue(generator = "user_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_gen", schema = "sec", sequenceName = "user_seq", allocationSize = 1)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(
            schema = "sec",
            name = "app_user_role",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false)
    private List<UserRole> roles;

    @Column(name = "password_change_required", nullable = false)
    private Boolean passwordChangeRequired;

    @Column(name = "need_registration", nullable = false)
    private Boolean needRegistration;

    @OneToMany(mappedBy = "user")
    private List<RefreshToken> refreshTokens;

    public void addRole(UserRole role) {
        roles.add(role);
    }
}
