package dev.ironcorelabs.ironcore.training.service;

import dev.ironcorelabs.ironcore.training.TrainerMapper;
import dev.ironcorelabs.ironcore.training.model.entity.Trainer;
import dev.ironcorelabs.ironcore.training.model.repository.TrainerRepository;
import dev.ironcorelabs.ironlog.core.exception.RecordNotFoundException;
import dev.ironcorelabs.ironlog.core.service.TrainerService;
import dev.ironcorelabs.ironlog.restapi.openapi.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository repository;
    private final TrainerMapper mapper;

    @Override
    public TrainerDto update(Long id, UpdateMyTrainerProfileRequest request) {
        return null;
    }

    @Override
    public TrainerDto updateUnsafe(Long id, UpdateMyTrainerProfileRequest request) {
        return null;
    }

    @Override
    public TrainerDto updateByExternalId(UUID id, UpdateMyTrainerProfileRequest request) {


        return null;
    }

    @Override
    public TrainerDto updateByExternalIdUnsafe(UUID id, UpdateMyTrainerProfileRequest request) {

        final Trainer trainer = repository.findByExternalId(id)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        return updateTrainer(trainer, request);
    }

    private TrainerDto updateTrainer(Trainer trainer, UpdateMyTrainerProfileRequest request) {
        mapper.updateEntity(request, trainer);
        return mapper.toDto(repository.save(trainer));
    }

    @Override
    public TrainerDto create(RegisterTrainerRequest request) {
        return null;
    }

    @Override
    public TrainerDto createUnsafe(RegisterTrainerRequest request) {
        return null;
    }

    @Override
    public TrainerDto findById(Long id) {
        return null;
    }

    @Override
    public TrainerDto findByExternalId(UUID id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void deleteByExternalId(UUID id) {

    }

    @Override
    public TrainerList findAll(int page, int size) {
        return null;
    }

    @Override
    public void promoteToTrainerDto(UUID id) {

    }

    @Override
    public ClientList getMyAthletes(int page, int size) {
        return null;
    }

    @Override
    public Client registerAndEnroll(RegisterClientRequest request) {
        return null;
    }

    @Override
    public void enroll(UUID clientId) {

    }
}
