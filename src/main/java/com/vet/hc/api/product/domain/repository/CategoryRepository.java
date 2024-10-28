package com.vet.hc.api.product.domain.repository;

import java.util.List;

import com.vet.hc.api.product.domain.model.Category;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

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
     *         <li>{@link RepositoryFailure#DUPLICATE} if the category name is
     *         already in use.</li>
     *         <li>{@link RepositoryFailure#UNEXPECTED} if an internal error
     *         occurred while saving the category.</li>
     *         </ul>
     */
    Result<Category, RepositoryFailure> save(Category category);
}
