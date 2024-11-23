package com.vet.hc.api.patient.vaccine.domain.dto;

import java.time.LocalDateTime;

import com.vet.hc.api.user.core.domain.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class VaccineDto {
    private Long id;

    private String name;
    private Integer dose;
    private LocalDateTime providedAt;

    private UserDto vaccinator;
    // private SaleDto sale;
}