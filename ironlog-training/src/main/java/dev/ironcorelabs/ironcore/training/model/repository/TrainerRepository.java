package dev.ironcorelabs.ironcore.training.model.repository;

import dev.ironcorelabs.ironcore.training.model.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    Optional<Trainer> findByExternalId(UUID externalId);
}
