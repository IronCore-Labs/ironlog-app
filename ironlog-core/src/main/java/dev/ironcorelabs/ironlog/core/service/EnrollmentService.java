package dev.ironcorelabs.ironlog.core.service;

import java.util.UUID;

public interface EnrollmentService {

    void assignClientToTrainer(UUID clientId, UUID trainerId);
    void deleteClientToTrainer(UUID clientId, UUID trainerId);
    void disableClientToTrainer(UUID clientId, UUID trainerId);

    boolean existsByTrainerAndClient(UUID trainerId, UUID clientId);
}
