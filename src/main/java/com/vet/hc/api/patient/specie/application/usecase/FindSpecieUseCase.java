package com.vet.hc.api.patient.specie.application.usecase;

import java.util.List;

import com.vet.hc.api.patient.specie.application.port.in.FindSpeciePort;
import com.vet.hc.api.patient.specie.domain.dto.SpecieDto;
import com.vet.hc.api.patient.specie.domain.failure.SpecieFailure;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class FindSpecieUseCase implements FindSpeciePort {
    @Override
    public Result<List<SpecieDto>, SpecieFailure> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Result<SpecieDto, SpecieFailure> findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }
}
