package dev.ironcorelabs.ironlog.security.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "sec", name = "refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(generator = "refresh_token_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "refresh_token_gen", sequenceName = "refresh_token_seq"
            , schema = "sec", allocationSize = 1)
    private Long id;

    @Column(name = "token", nullable = false, columnDefinition = "TEXT")
    private String token;

    @Column(name = "jti", nullable = false, unique = true)
    private UUID jti;

    @Column(name = "attempts", nullable = false)
    private Long attempts;

    @Column(name = "is_revoked", nullable = false)
    private Boolean isRevoked;

    @Column(name = "generation_date", nullable = false)
    private LocalDateTime generationDate;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;
}
