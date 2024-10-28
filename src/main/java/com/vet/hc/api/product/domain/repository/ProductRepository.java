package com.vet.hc.api.product.domain.repository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.vet.hc.api.product.domain.model.Product;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

/**
 * Represents a repository for products.
 */
public interface ProductRepository {
    /**
     * Finds all products.
     *
     * @return List of products
     */
    List<Product> findAll();

    /**
     * Finds a product by id.
     *
     * @param id The id to search for.
     * @return The product if found, empty otherwise
     */
    Optional<Product> findById(Long id);

    /**
     * Finds all products by name.
     *
     * @param name The name to search for.
     * @return List of products
     */
    List<Product> findAllByMatchingName(String name);

    /**
     * Finds all products by id.
     *
     * @param id The id to search for.
     * @return List of products
     */
    List<Product> findAllByMatchingId(Long id);

    /**
     * Saves a product.
     *
     * @param product The product to save.
     * @return The saved product if successful, the failure otherwise. The failure
     *         can be:
     *         <ul>
     *         <li>{@link RepositoryFailure#DUPLICATE} if the product name is
     *         already in use.</li>
     *         <li>{@link RepositoryFailure#UNEXPECTED} if an internal error
     *         occurred while saving the product.</li>
     *         </ul>
     */
    Result<Product, RepositoryFailure> save(Product product);

    /**
     * Finds all products by category ids.
     *
     * @param categoryIds The category ids to search for.
     * @return List of products
     */
    List<Product> findAllByCategoryIds(Iterator<Long> categoryIds);
}
