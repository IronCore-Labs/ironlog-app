package dev.ironcorelabs.ironcore.training.event;

import dev.ironcorelabs.ironcore.training.model.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainerEventListener {
    private final TrainerRepository repository;
}
