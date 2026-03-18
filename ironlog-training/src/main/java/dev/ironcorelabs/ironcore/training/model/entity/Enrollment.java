package dev.ironcorelabs.ironcore.training.model.entity;

import dev.ironcorelabs.ironlog.core.model.entity.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "enrollment", schema = "training")
public class Enrollment extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "enrollment_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "enrollment_gen", schema = "training", sequenceName = "enrollment_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "assignment_date", nullable = false)
    private LocalDateTime assignmentDate;
}
