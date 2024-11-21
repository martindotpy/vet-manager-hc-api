package com.vet.hc.api.patient.specie.application.port.in;

import com.vet.hc.api.patient.specie.domain.dto.SpecieDto;
import com.vet.hc.api.patient.specie.domain.failure.SpecieFailure;
import com.vet.hc.api.patient.specie.domain.payload.UpdateSpeciePayload;
import com.vet.hc.api.shared.domain.query.Result;

public interface UpdateSpeciePort {
    Result<SpecieDto, SpecieFailure> update(UpdateSpeciePayload payload);
}
