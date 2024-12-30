package com.vet.hc.api.bill.productsale.adapter.out.persistence;

import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;

import com.vet.hc.api.auth.core.adapter.annotations.PersistenceAdapter;
import com.vet.hc.api.bill.productsale.adapter.out.mapper.ProductSaleMapper;
import com.vet.hc.api.bill.productsale.adapter.out.persistence.repository.ProductSaleHibernateRepository;
import com.vet.hc.api.bill.productsale.domain.failure.ProductSaleFailure;
import com.vet.hc.api.bill.productsale.domain.model.ProductSale;
import com.vet.hc.api.bill.productsale.domain.repository.ProductSaleRepository;
import com.vet.hc.api.shared.domain.query.Result;

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

    @Override
    public Optional<ProductSale> findById(Long id) {
        return productSaleHibernateRepository.findById(id)
                .map(productSaleMapper::toDomain);
    }

    @Override
    public Result<ProductSale, ProductSaleFailure> save(ProductSale productSale) {
        try {
            return Result.success(
                    productSaleMapper.toDomain(
                            productSaleHibernateRepository.save(productSaleMapper.toEntity(productSale))));
        } catch (ConstraintViolationException e) {
            return Result.failure(ProductSaleFailure.UNEXPECTED);
        } catch (RollbackException e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(ProductSaleFailure.UNEXPECTED);

            log.error("Error saving product sale: {}", productSale, e);

            return Result.failure(ProductSaleFailure.UNEXPECTED);
        } catch (Exception e) {
            if (e.getCause() instanceof ConstraintViolationException constraintViolationException)
                return Result.failure(ProductSaleFailure.UNEXPECTED);

            else if (e instanceof EntityNotFoundException entityNotFoundException)
                return Result.failure(ProductSaleFailure.UNEXPECTED);

            log.error("Error saving product sale: {}", productSale, e);

            return Result.failure(ProductSaleFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, ProductSaleFailure> deleteById(Long id) {
        try {
            productSaleHibernateRepository.deleteById(id);

            return Result.success();

        } catch (IllegalArgumentException e) {
            log.error("Product sale with id {} not found", id);

            return Result.failure(ProductSaleFailure.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting product sale with id: {}", id, e);

            return Result.failure(ProductSaleFailure.UNEXPECTED);
        }
    }
}
