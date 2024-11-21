package com.vet.hc.api.patient.specie.application.usecase;

import com.vet.hc.api.patient.specie.application.port.in.CreateSpeciePort;
import com.vet.hc.api.patient.specie.domain.dto.SpecieDto;
import com.vet.hc.api.patient.specie.domain.failure.SpecieFailure;
import com.vet.hc.api.patient.specie.domain.payload.CreateSpeciePayload;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class CreateSpecieUseCase implements CreateSpeciePort {@Override
    public Result<SpecieDto, SpecieFailure> create(CreateSpeciePayload payload) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }
}
