package com.vet.hc.api.patient.core.domain.repository;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.patient.core.domain.model.Patient;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Paginated;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

/**
 * Repository for patients.
 */
public interface PatientRepository {
    /**
     * Find all patients.
     *
     * @return The list of patients.
     */
    List<Patient> findAll();

    /**
     * Find a patient by id.
     *
     * @param id The id of the patient to find.
     * @return The patient with the given id.
     */
    Optional<Patient> findById(Long id);

    /**
     * Create a new patient.
     *
     * @param patient The patient to create.
     * @return The created patient
     */
    Result<Patient, RepositoryFailure> save(Patient patient);

    /**
     * Find patients that match the given criteria.
     *
     * @param criteria The criteria to match.
     * @return The list of patients that match the criteria, the failure
     *         otherwise.
     *         The
     *         failure can be:
     *         <ul>
     *         <li>{@link RepositoryFailure#FIELD_NOT_FOUND} if the field in the
     *         criteria is not found</li>
     *         <li>{@link RepositoryFailure#UNEXPECTED} if an internal error
     *         occurred
     *         while saving the patient phone.</li>
     *         </ul>
     */
    Result<Paginated<Patient>, RepositoryFailure> match(Criteria criteria);

    /**
     * Delete a patient.
     *
     * @param id The id of the patient to delete.
     * @return The result of the operation
     */
    Result<Void, RepositoryFailure> deleteById(Long id);
}
