package com.vet.hc.api.patient.specie.application.usecase;

import com.vet.hc.api.patient.specie.application.port.in.UpdateSpeciePort;
import com.vet.hc.api.patient.specie.domain.dto.SpecieDto;
import com.vet.hc.api.patient.specie.domain.failure.SpecieFailure;
import com.vet.hc.api.patient.specie.domain.payload.UpdateSpeciePayload;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class UpdateSpecieUseCase implements UpdateSpeciePort {

    @Override
    public Result<SpecieDto, SpecieFailure> update(UpdateSpeciePayload payload) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}
