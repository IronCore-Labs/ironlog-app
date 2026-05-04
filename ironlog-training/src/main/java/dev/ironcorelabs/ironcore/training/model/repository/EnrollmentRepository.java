package dev.ironcorelabs.ironcore.training.model.repository;

import dev.ironcorelabs.ironcore.training.model.entity.Client;
import dev.ironcorelabs.ironcore.training.model.entity.Enrollment;
import dev.ironcorelabs.ironcore.training.model.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    /**
     * Navega: Enrollment -> Trainer -> externalId
     * y Enrollment -> Client -> externalId
     */
    boolean existsByTrainerExternalIdAndClientExternalId(UUID trainerExtId, UUID clientExtId);

    /**
     * Navega: Enrollment -> Trainer -> id (PK)
     * y Enrollment -> Client -> externalId
     */
    boolean existsByTrainerIdAndClientExternalId(Long trainerId, UUID clientExtId);

    Optional<Enrollment> findByTrainerExternalIdAndClientExternalId(UUID trainerExtId, UUID clientExtId);

    Optional<Enrollment> findByTrainerIdAndClientExternalId(Long trainerId, UUID clientExtId);

    Optional<Enrollment> findByTrainerAndClient(Trainer trainer, Client client);

    /**
     * Listado optimizado con JOIN FETCH para evitar N+1
     */
    @Query("SELECT e FROM Enrollment e " +
            "JOIN FETCH e.client " +
            "WHERE e.trainer.id = :trainerId")
    List<Enrollment> findAllByTrainerIdWithClient(@Param("trainerId") Long trainerId);

    /**
     * Listado para administración por UUID
     */
    @Query("SELECT e FROM Enrollment e " +
            "JOIN FETCH e.client " +
            "WHERE e.trainer.externalId = :trainerExtId")
    List<Enrollment> findAllByTrainerExternalIdWithClient(@Param("trainerExtId") UUID trainerExtId);

    /**
     * Listado para administración por UUID
     */
    @Query("SELECT e FROM Enrollment e " +
            "JOIN FETCH e.trainer " +
            "WHERE e.client.externalId = :clientExtId " +
            "AND e.enabled = true")
    Optional<Enrollment> findAllByClientExternalIdWithTrainer(@Param("clientExtId") UUID clientExtId);
}
