package dev.ironcorelabs.ironcore.training.model.entity;

import dev.ironcorelabs.ironlog.core.model.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "client", schema = "training")
public class Client extends BaseEntity {

    @Id
    private Long id;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "location")
    private String location;

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
}
