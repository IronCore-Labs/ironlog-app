package dev.ironcorelabs.ironlog.core.service;

import dev.ironcorelabs.ironlog.core.dto.ClientDTO;
import dev.ironcorelabs.ironlog.restapi.openapi.model.*;

import java.util.UUID;

public interface TrainerService {
    TrainerDto update(Long id, UpdateMyTrainerProfileRequest request);

    TrainerDto updateByExternalId(UUID id, UpdateMyTrainerProfileRequest request);

    TrainerDto create(RegisterTrainerRequest request);

    TrainerDto findById(Long id);

    TrainerDto findByExternalId(UUID id);

    void delete(Long id);

    void deleteByExternalId(UUID id);

    TrainerList findAll(int page, int size);

    void promoteToTrainer(UUID id);

    void disableTrainer(UUID id);

    ClientList getMyAthletes(Long trainerId, int page, int size);

    ClientDTO registerAndEnroll(Long trainerId, RegisterClientRequest request);

    void assignClientToTrainer(Long trainerId, UUID clientExtId);

    void disableClientToTrainer(Long trainerId, UUID clientExtId);
}
