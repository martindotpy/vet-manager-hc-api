package com.vet.hc.api.patient.core.adapter.out.persistence.entity;

import java.time.LocalDate;
import java.util.List;

import com.vet.hc.api.client.core.adapter.out.persistence.entity.ClientEntity;
import com.vet.hc.api.patient.core.domain.enums.Genre;
import com.vet.hc.api.patient.medicalhistory.adapter.out.persistence.entity.MedicalHistoryEntity;
import com.vet.hc.api.patient.race.adapter.out.persistence.entity.RaceEntity;
import com.vet.hc.api.patient.vaccine.adapter.out.persistence.entity.VaccineEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "patient")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Genre genre;
    @Column(nullable = false)
    private String characteristics;
    @Column(name = "is_deceased", columnDefinition = "bit(1) default 0", nullable = false)
    private boolean deceased;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private RaceEntity race;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private ClientEntity owner;
    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    private List<VaccineEntity> vaccines;
    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    private List<MedicalHistoryEntity> medicalHistories;
}
