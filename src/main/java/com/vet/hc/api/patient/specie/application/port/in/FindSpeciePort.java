package com.vet.hc.api.patient.specie.application.port.in;

import java.util.List;

import com.vet.hc.api.patient.specie.domain.dto.SpecieDto;
import com.vet.hc.api.patient.specie.domain.failure.SpecieFailure;
import com.vet.hc.api.shared.domain.query.Result;

public interface FindSpeciePort {
    List<SpecieDto> findAll();

    Result<SpecieDto, SpecieFailure> findById(Long id);
}
