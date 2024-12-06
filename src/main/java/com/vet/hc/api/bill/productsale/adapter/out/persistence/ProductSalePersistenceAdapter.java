package com.vet.hc.api.bill.productsale.adapter.out.persistence;

import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;

import com.vet.hc.api.auth.core.adapter.annotations.PersistenceAdapter;
import com.vet.hc.api.bill.productsale.adapter.out.mapper.ProductSaleMapper;
import com.vet.hc.api.bill.productsale.adapter.out.persistence.repository.ProductSaleHibernateRepository;
import com.vet.hc.api.bill.productsale.domain.model.ProductSale;
import com.vet.hc.api.bill.productsale.domain.repository.ProductSaleRepository;
import com.vet.hc.api.shared.adapter.out.mapper.RepositoryFailureMapper;
import com.vet.hc.api.shared.adapter.out.repository.MySQLRepositoryFailure;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.RollbackException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Adapter to persist product sales in the database.
 */
@Slf4j
@PersistenceAdapter
@RequiredArgsConstructor
public final class ProductSalePersistenceAdapter implements ProductSaleRepository {
    private final ProductSaleHibernateRepository productSaleHibernateRepository;
    private final ProductSaleMapper productSaleMapper;
    private final RepositoryFailureMapper repositoryFailureMapper;

    @Override
    public Optional<ProductSale> findById(Long id) {
        return productSaleHibernateRepository.findById(id)
                .map(productSaleMapper::toDomain);
    }

    @Override
    public Result<ProductSale, RepositoryFailure> save(ProductSale productSale) {
        try {
            return Result.success(
                    productSaleMapper.toDomain(
                            productSaleHibernateRepository.save(productSaleMapper.toEntity(productSale))));
        } catch (ConstraintViolationException e) {
            return Result.failure(manageConstraintViolations(e, productSale));
        } catch (RollbackException e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(manageConstraintViolations(constraintViolationException, productSale));

            log.error("Error saving product sale: {}", productSale, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        } catch (Exception e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(manageConstraintViolations(constraintViolationException, productSale));

            else if (e instanceof EntityNotFoundException entityNotFoundException)
                return Result.failure(manageEntityNotFoundException(entityNotFoundException, productSale));

            log.error("Error saving product sale: {}", productSale, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, RepositoryFailure> deleteById(Long id) {
        try {
            productSaleHibernateRepository.deleteById(id);

            return Result.success();

        } catch (IllegalArgumentException e) {
            log.error("Product sale with id {} not found", id);

            return Result.failure(RepositoryFailure.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting product sale with id: {}", id, e);

            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    /**
     * Manage the constraint violations.
     *
     * @param e           The exception.
     * @param productSale The product sale.
     * @return The repository failure
     */
    private RepositoryFailure manageConstraintViolations(
            ConstraintViolationException e,
            ProductSale productSale) {
        MySQLRepositoryFailure mySqlFailure = MySQLRepositoryFailure.from(e.getErrorCode());

        RepositoryFailure repositoryFailure = repositoryFailureMapper.toRespositoryFailure(mySqlFailure);

        log.error("Error saving product sale with id `{}`", productSale.getId(), e);

        return repositoryFailure;
    }

    /**
     * Manage the entity not found exception.
     *
     * @param e           The exception.
     * @param productSale The product sale.
     * @return The repository failure
     */
    private RepositoryFailure manageEntityNotFoundException(
            EntityNotFoundException e,
            ProductSale productSale) {
        if (e.getMessage().contains("ProductTypeEntity")) {
            log.error("Product sale with id `{}` not found", productSale.getId());

            RepositoryFailure repositoryFailure = RepositoryFailure.ENTITY_NOT_FOUND;

            repositoryFailure.setField("product_type");

            return repositoryFailure;
        }

        else if (e.getMessage().contains("ProductEntity")) {
            log.error("Product sale with id `{}` not found", productSale.getId());

            RepositoryFailure repositoryFailure = RepositoryFailure.ENTITY_NOT_FOUND;

            repositoryFailure.setField("product");

            return repositoryFailure;
        }

        log.error("Error saving product sale", e);

        return RepositoryFailure.NOT_FOUND;
    }
}
