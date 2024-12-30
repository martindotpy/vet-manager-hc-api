package com.vet.hc.api.product.core.domain.repository;

import java.util.Optional;

import com.vet.hc.api.product.core.domain.failure.ProductFailure;
import com.vet.hc.api.product.core.domain.model.Product;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Paginated;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailureType;

/**
 * Represents a repository for products.
 */
public interface ProductRepository {
    /**
     * Find all matching products.
     *
     * @param criteria    The criteria to filter the products.
     * @param categoryIds The category ids to filter the products.
     * @return The matching products
     */
    Result<Paginated<Product>, ProductFailure> match(Criteria criteria, Iterable<Integer> categoryIds);

    /**
     * Finds a product by id.
     *
     * @param id The id to search for.
     * @return The product if found, empty otherwise
     */
    Optional<Product> findById(Long id);

    /**
     * Saves a product.
     *
     * @param product The product to save.
     * @return The saved product if successful, the failure otherwise. The failure
     *         can be:
     *         <ul>
     *         <li>{@link RepositoryFailureType#DUPLICATED} if the product name is
     *         already in use.</li>
     *         <li>{@link RepositoryFailureType#UNEXPECTED} if an internal error
     *         occurred while saving the product.</li>
     *         </ul>
     */
    Result<Product, ProductFailure> save(Product product);

    /**
     * Updates a product.
     *
     * @param id The id of the product to update.
     */
    Result<Void, ProductFailure> deleteById(Long id);
}
