package com.vet.hc.api.bill.core.domain.repository;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.bill.core.domain.model.Bill;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Paginated;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

/**
 * Repository for bills.
 */
public interface BillRepository {
    /**
     * Find all bills.
     *
     * @return The list of bills.
     */
    List<Bill> findAll();

    /**
     * Find a bill by id.
     *
     * @param id The id of the bill to find.
     * @return The bill with the given id.
     */
    Optional<Bill> findById(Long id);

    /**
     * Create a new bill.
     *
     * @param bill The bill to create.
     * @return The created bill
     */
    Result<Bill, RepositoryFailure> save(Bill bill);

    /**
     * Find bills that match the given criteria.
     *
     * @param criteria The criteria to match.
     * @return The list of bills that match the criteria, the failure
     *         otherwise.
     *         The
     *         failure can be:
     *         <ul>
     *         <li>{@link RepositoryFailure#FIELD_NOT_FOUND} if the field in the
     *         criteria is not found</li>
     *         <li>{@link RepositoryFailure#UNEXPECTED} if an internal error
     *         occurred
     *         while saving the bill phone.</li>
     *         </ul>
     */
    Result<Paginated<Bill>, RepositoryFailure> match(Criteria criteria);

    /**
     * Delete a bill.
     *
     * @param id The id of the bill to delete.
     * @return The result of the operation
     */
    Result<Void, RepositoryFailure> deleteById(Long id);
}
