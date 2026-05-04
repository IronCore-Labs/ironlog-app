package dev.ironcorelabs.ironcore.training.model.entity;

import dev.ironcorelabs.ironlog.core.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "client", schema = "training")
public class Client extends BaseEntity {

    @Id
    private Long id;

    @Column(name = "need_registration", nullable = false)
    private Boolean needRegistration;

    @Column(name = "weight")
    private BigDecimal weight;

    @Column(name = "height")
    private BigDecimal height;

    @Column(name = "has_surgeries", nullable = false)
    private Boolean hasSurgeries;

    @Column(name = "surgery_details")
    private String surgeryDetails;

    @Column(name = "heart_conditions")
    private String heartConditions;

    @Column(name = "allergies")
    private String allergies;

    @Column(name = "medications")
    private String medications;

    @Column(name = "observations")
    private String observations;

    @OneToMany(mappedBy = "client")
    private List<Enrollment> enrollments;
}
