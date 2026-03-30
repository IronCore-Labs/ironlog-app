package dev.ironcorelabs.ironcore.training.controller;

import dev.ironcorelabs.ironlog.core.security.SecurityUtils;
import dev.ironcorelabs.ironlog.core.service.TrainerService;
import dev.ironcorelabs.ironlog.restapi.openapi.api.TrainersApi;
import dev.ironcorelabs.ironlog.restapi.openapi.model.RegisterTrainerRequest;
import dev.ironcorelabs.ironlog.restapi.openapi.model.TrainerDto;
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
    public ResponseEntity<TrainerDto> updateTrainer(UUID trainerId, UpdateMyTrainerProfileRequest updateMyTrainerProfileRequest) {
        return TrainersApi.super.updateTrainer(trainerId, updateMyTrainerProfileRequest);
    }

    @Override
    public ResponseEntity<TrainerDto> updateMyTrainerProfile(UpdateMyTrainerProfileRequest updateMyTrainerProfileRequest) {
        return TrainersApi.super.updateMyTrainerProfile(updateMyTrainerProfileRequest);
    }

    @Override
    public ResponseEntity<Void> selfAssing(UUID clientId) {
        return TrainersApi.super.selfAssing(clientId);
    }

    @Override
    public ResponseEntity<TrainerDto> registerTrainer(RegisterTrainerRequest registerTrainerRequest) {
        return TrainersApi.super.registerTrainer(registerTrainerRequest);
    }

    @Override
    public ResponseEntity<Void> promoteTrainer(UUID trainerId) {
        return TrainersApi.super.promoteTrainer(trainerId);
    }

    @Override
    public ResponseEntity<TrainerDto> getTrainerById(UUID trainerId) {
        return TrainersApi.super.getTrainerById(trainerId);
    }

    @Override
    public ResponseEntity<TrainerDto> getMyTrainerProfile() {
        return TrainersApi.super.getMyTrainerProfile();
    }

    @Override
    public ResponseEntity<TrainerList> getAllTrainers(Integer page, Integer size, String sort) {
        return TrainersApi.super.getAllTrainers(page, size, sort);
    }

    @Override
    public ResponseEntity<Void> deactivate() {
        return TrainersApi.super.deactivate();
    }
}
