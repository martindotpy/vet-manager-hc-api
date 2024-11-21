package com.vet.hc.api.patient.specie.application.port.in;

import com.vet.hc.api.patient.specie.domain.failure.SpecieFailure;
import com.vet.hc.api.shared.domain.query.Result;

public interface DeleteSpeciePort {
    Result<Void, SpecieFailure> deleteById(Long id);
}
