package com.vet.hc.api.patient.specie.application.usecase;

import com.vet.hc.api.patient.specie.application.port.in.DeleteSpeciePort;
import com.vet.hc.api.patient.specie.domain.failure.SpecieFailure;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class DeleteSpecieUseCase implements DeleteSpeciePort {
    @Override
    public Result<Void, SpecieFailure> deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }
}
