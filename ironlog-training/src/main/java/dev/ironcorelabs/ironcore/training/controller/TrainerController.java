package dev.ironcorelabs.ironcore.training.controller;

import dev.ironcorelabs.ironlog.core.security.SecurityUtils;
import dev.ironcorelabs.ironlog.core.service.TrainerService;
import dev.ironcorelabs.ironlog.restapi.openapi.api.TrainersApi;
import dev.ironcorelabs.ironlog.restapi.openapi.model.RegisterTrainerRequest;
import dev.ironcorelabs.ironlog.restapi.openapi.model.Trainer;
import dev.ironcorelabs.ironlog.restapi.openapi.model.TrainerList;
import dev.ironcorelabs.ironlog.restapi.openapi.model.UpdateMyTrainerProfileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TrainerController implements TrainersApi {

    private final TrainerService trainerService;
    private final SecurityUtils securityUtils;

    @Override
    public ResponseEntity<Trainer> updateTrainer(UUID trainerId, UpdateMyTrainerProfileRequest request) {
        return ResponseEntity.ok(trainerService.updateByExternalId(trainerId, request));
    }

    @Override
    public ResponseEntity<Trainer> updateMyTrainerProfile(UpdateMyTrainerProfileRequest request) {
        return ResponseEntity.ok(trainerService.update(securityUtils.getCurrentUserId(), request));
    }

    @Override
    public ResponseEntity<Trainer> registerTrainer(RegisterTrainerRequest request) {
        return ResponseEntity.ok(trainerService.create(request));
    }

    @Override
    public ResponseEntity<Trainer> getTrainerById(UUID trainerId) {
        return ResponseEntity.ok(trainerService.findByExternalId(trainerId));
    }

    @Override
    public ResponseEntity<Trainer> getMyTrainerProfile() {
        return ResponseEntity.ok(trainerService.findById(securityUtils.getCurrentUserId()));
    }

    @Override
    public ResponseEntity<TrainerList> getAllTrainers(Integer page, Integer size, String sort) {
        return ResponseEntity.ok(trainerService.findAll(page, size));
    }
}
