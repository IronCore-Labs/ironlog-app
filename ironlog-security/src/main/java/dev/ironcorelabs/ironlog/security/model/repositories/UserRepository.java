package dev.ironcorelabs.ironlog.security.model.repositories;

import dev.ironcorelabs.ironlog.security.model.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    List<AppUser> findByFullName(String fullName);

    Optional<AppUser> findByEmail(String email);
}
