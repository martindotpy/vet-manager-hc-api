package com.vluepixel.vetmanager.api.medicalrecord.core.application.usecase;

import static com.vluepixel.vetmanager.api.shared.domain.criteria.Filter.equal;

import org.slf4j.MDC;
import org.springframework.transaction.annotation.Transactional;

import com.vluepixel.vetmanager.api.medicalrecord.core.application.dto.MedicalRecordDto;
import com.vluepixel.vetmanager.api.medicalrecord.core.application.mapper.MedicalRecordMapper;
import com.vluepixel.vetmanager.api.medicalrecord.core.application.port.in.UpdateMedicalRecordPort;
import com.vluepixel.vetmanager.api.medicalrecord.core.domain.model.MedicalRecord;
import com.vluepixel.vetmanager.api.medicalrecord.core.domain.repository.MedicalRecordRepository;
import com.vluepixel.vetmanager.api.medicalrecord.core.domain.request.UpdateMedicalRecordRequest;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.shared.domain.criteria.Criteria;
import com.vluepixel.vetmanager.api.shared.domain.exception.InternalServerErrorException;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;
import com.vluepixel.vetmanager.api.shared.domain.query.FieldUpdate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Update medical record use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public class UpdateMedicalRecordUseCase implements UpdateMedicalRecordPort {
    private final MedicalRecordRepository medicalRecordRepository;
    private final MedicalRecordMapper medicalRecordMapper;

    @Override
    @Transactional
    public MedicalRecordDto update(Long patientId, UpdateMedicalRecordRequest request) {
        MDC.put("operationId", "Medical record id " + request.getId());
        log.info("Updating medical record");

        // Update the medical record
        var medicalRecordUpdated = medicalRecordMapper.fromRequest(request).build();
        int rowsModified = medicalRecordRepository.updateBy(
                Criteria.of(
                        equal("id", request.getId()),
                        equal("patient.id", patientId)),
                FieldUpdate.set("entryAt", medicalRecordUpdated.getEntryAt()),
                FieldUpdate.set("reason", medicalRecordUpdated.getReason()),
                FieldUpdate.set("physicalExam", medicalRecordUpdated.getPhysicalExam()),
                FieldUpdate.set("temperatureInCelsius", medicalRecordUpdated.getTemperatureInCelsius()),
                FieldUpdate.set("respitarionRate", medicalRecordUpdated.getRespitarionRate()),
                FieldUpdate.set("heartRate", medicalRecordUpdated.getHeartRate()),
                FieldUpdate.set("weight", medicalRecordUpdated.getWeight()),
                FieldUpdate.set("sterilized", medicalRecordUpdated.isSterilized()),
                FieldUpdate.set("recipe", medicalRecordUpdated.getRecipe()),
                FieldUpdate.set("diagnosis", medicalRecordUpdated.getDiagnosis()),
                FieldUpdate.set("vet", medicalRecordUpdated.getVet()));

        if (rowsModified == 0) {
            throw new NotFoundException(MedicalRecord.class, request.getId());
        } else if (rowsModified > 1) {
            throw new InternalServerErrorException(
                    new IllegalStateException(
                            "Medical record with patient id '" +
                                    patientId +
                                    "' and id '" +
                                    request.getId() +
                                    "' has more than one row modified"));
        }

        medicalRecordUpdated = medicalRecordRepository.findById(request.getId()).get();

        return medicalRecordMapper.toDto(medicalRecordUpdated);
    }
}
