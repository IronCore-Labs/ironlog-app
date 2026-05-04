package dev.ironcorelabs.ironlog.core.service;

import java.util.UUID;

public interface InternalEnrollmentService {
    void assignClientToTrainer(UUID clientId, Long trainerId);
    void deleteClientToTrainer(UUID clientId, Long trainerId);
    void disableClientToTrainer(UUID clientId, Long trainerId);
}
