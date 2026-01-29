package dev.ironcorelabs.ironlog.security.model.repositories;

import dev.ironcorelabs.ironlog.security.model.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByJti(UUID jti);

    List<RefreshToken> findByUser_Id(Long userId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE RefreshToken rt SET rt.isRevoked = :isRevoked" +
            " WHERE rt.user.id = :userId AND rt.isRevoked <> :isRevoked")
    int updateRevokedByUserId(@Param("userId") Long userId, @Param("isRevoked") Boolean isRevoked);
}
