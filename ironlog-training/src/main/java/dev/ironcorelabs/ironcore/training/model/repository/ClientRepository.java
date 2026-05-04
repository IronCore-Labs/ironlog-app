package dev.ironcorelabs.ironcore.training.model.repository;

import dev.ironcorelabs.ironcore.training.model.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByExternalId(UUID externalId);

    @EntityGraph(attributePaths = {"enrollments"})
    @Query("SELECT DISTINCT c FROM Client c" +
            " JOIN c.enrollments e" +
            " WHERE e.trainer.externalId = :trainerId" +
            " AND e.enabled = true")
    Page<Client> findByTrainerExternalId(@Param("trainerId") UUID trainerId, Pageable pageable);

    @EntityGraph(attributePaths = {"enrollments"})
    @Query("SELECT DISTINCT c FROM Client c" +
            " JOIN c.enrollments e" +
            " WHERE e.trainer.id = :trainerId" +
            " AND e.enabled = true")
    Page<Client> findByTrainerId(@Param("trainerId") Long trainerId, Pageable pageable);

    Page<Client> findByEnabledTrue(Pageable pageable);
}
