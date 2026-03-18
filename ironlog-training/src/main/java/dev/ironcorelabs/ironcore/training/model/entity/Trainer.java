package dev.ironcorelabs.ironcore.training.model.entity;

import dev.ironcorelabs.ironlog.core.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "trainer", schema = "training", indexes = {})
@EqualsAndHashCode(callSuper = true)
public class Trainer extends BaseEntity {

    @Id
    private Long id;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "location")
    private String location;

    @Column(name = "need_registration", nullable = false)
    private Boolean needRegistration;

    @Column(name = "speciality")
    private String speciality;

    @Column(name = "bio")
    private String bio;

    @Column(name = "experience_years")
    private Integer experienceYears;
}
