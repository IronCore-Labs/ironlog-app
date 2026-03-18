package dev.ironcorelabs.ironcore.training.model.repository;

import dev.ironcorelabs.ironcore.training.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByExternalId(UUID externalId);
}
