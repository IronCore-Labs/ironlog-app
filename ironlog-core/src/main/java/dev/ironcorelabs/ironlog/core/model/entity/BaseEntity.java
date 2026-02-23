package dev.ironcorelabs.ironlog.core.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "external_id", nullable = false, unique = true)
    private UUID externalId;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private Long createdBy; // Vinculado a tu AuditorAware<Long>

    @LastModifiedBy
    @Column(name = "updated_by")
    private Long updatedBy;

    @PrePersist
    public void onCreate() {
        if (externalId == null)
        {
            externalId = UUID.randomUUID();
        }
    }
}
