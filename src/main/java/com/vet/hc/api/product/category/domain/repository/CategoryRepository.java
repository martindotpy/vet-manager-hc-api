package com.vet.hc.api.product.category.domain.repository;

import java.util.List;

import com.vet.hc.api.product.category.domain.failure.CategoryFailure;
import com.vet.hc.api.product.category.domain.model.Category;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailureType;

/**
 * Category repository interface.
 */
public interface CategoryRepository {
    /**
     * Find all categories.
     *
     * @return List of categories.
     */
    List<Category> findAll();

    /**
     * Saves a category.
     *
     * @param category The category to save.
     * @return The saved category if successful, the failure otherwise. The failure
     *         can be:
     *         <ul>
     *         <li>{@link RepositoryFailureType#DUPLICATED} if the category name is
     *         already in use.</li>
     *         <li>{@link RepositoryFailureType#UNEXPECTED} if an internal error
     *         occurred while saving the category.</li>
     *         </ul>
     */
    Result<Category, CategoryFailure> save(Category category);

    /**
     * Updates a product.
     *
     * @param id The id of the product to update.
     */
    Result<Void, CategoryFailure> deleteById(Long id);
}
