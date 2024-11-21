package com.vet.hc.api.patient.specie.application.port.in;

import com.vet.hc.api.patient.specie.domain.dto.SpecieDto;
import com.vet.hc.api.patient.specie.domain.failure.SpecieFailure;
import com.vet.hc.api.patient.specie.domain.payload.CreateSpeciePayload;
import com.vet.hc.api.shared.domain.query.Result;

public interface CreateSpeciePort {
    Result<SpecieDto, SpecieFailure> create(CreateSpeciePayload payload);
}
