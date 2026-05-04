package dev.ironcorelabs.ironcore.training.service.impl;

import dev.ironcorelabs.ironcore.training.model.entity.Client;
import dev.ironcorelabs.ironcore.training.model.entity.Enrollment;
import dev.ironcorelabs.ironcore.training.model.entity.Trainer;
import dev.ironcorelabs.ironcore.training.model.repository.ClientRepository;
import dev.ironcorelabs.ironcore.training.model.repository.EnrollmentRepository;
import dev.ironcorelabs.ironcore.training.model.repository.TrainerRepository;
import dev.ironcorelabs.ironlog.core.exception.RecordNotFoundException;
import dev.ironcorelabs.ironlog.core.service.EnrollmentService;
import dev.ironcorelabs.ironlog.core.service.InternalEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService, InternalEnrollmentService {

    private final EnrollmentRepository repository;
    private final ClientRepository clientRepository;
    private final TrainerRepository trainerRepository;

    @Override
    @Transactional
    public void assignClientToTrainer(UUID clientId, Long trainerId) {

        boolean exists = repository.findAllByClientExternalIdWithTrainer(clientId)
                .stream().anyMatch(enroll -> !enroll.getTrainer().getId().equals(trainerId));

        if (exists)
        {
            throw new ClientAlreadyAssignedException("client.already.assignment");
        }

        final Client client = clientRepository.findByExternalId(clientId)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        final Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        enableOrCreate(trainer, client);
    }

    @Override
    @Transactional
    public void deleteClientToTrainer(UUID clientId, Long trainerId) {
        final Enrollment enrollment = repository.findByTrainerIdAndClientExternalId(trainerId, clientId)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        repository.delete(enrollment);
    }

    @Override
    @Transactional
    public void disableClientToTrainer(UUID clientId, Long trainerId) {
        final Enrollment enrollment = repository.findByTrainerIdAndClientExternalId(trainerId, clientId)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        enabled(enrollment, false);
    }

    @Override
    @Transactional
    @PreAuthorize("@sec.isAdmin()")
    public void assignClientToTrainer(UUID clientId, UUID trainerId) {
        repository.findAllByClientExternalIdWithTrainer(clientId)
                .ifPresent(enrollment -> enabled(enrollment, false));

        final Client client = clientRepository.findByExternalId(clientId)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        final Trainer trainer = trainerRepository.findByExternalId(trainerId)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        enableOrCreate(trainer, client);
    }

    @Override
    @Transactional
    @PreAuthorize("@sec.isOwnerOrAdmin(#trainerId)")
    public void deleteClientToTrainer(UUID clientId, UUID trainerId) {
        final Enrollment enrollment = repository.findByTrainerExternalIdAndClientExternalId(trainerId, clientId)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        repository.delete(enrollment);
    }

    @Override
    @Transactional
    @PreAuthorize("@sec.isOwnerOrAdmin(#trainerId)")
    public void disableClientToTrainer(UUID clientId, UUID trainerId) {
        final Enrollment enrollment = repository.findByTrainerExternalIdAndClientExternalId(trainerId, clientId)
                .orElseThrow(() -> new RecordNotFoundException("not.found"));

        enabled(enrollment, false);
    }

    @Override
    @PreAuthorize("@sec.isOwnerOrAdmin(#trainerId)")
    public boolean existsByTrainerAndClient(UUID trainerId, UUID clientId) {
        return repository.existsByTrainerExternalIdAndClientExternalId(trainerId, clientId);
    }

    protected void enableOrCreate(Trainer trainer, Client client) {
        final Optional<Enrollment> sEnrollment = repository.findByTrainerAndClient(trainer, client);

        sEnrollment.ifPresentOrElse(enrollment -> enabled(enrollment, true)
                , () -> saveEnrollment(trainer, client));
    }

    protected void enabled(Enrollment enrollment, boolean enabled) {
        enrollment.setEnabled(enabled);
        repository.save(enrollment);
    }

    protected void saveEnrollment(Trainer trainer, Client client) {
        final Enrollment enrollment = new Enrollment();
        enrollment.setClient(client);
        enrollment.setTrainer(trainer);
        enrollment.setAssignmentDate(LocalDateTime.now());
        repository.save(enrollment);
    }
}
