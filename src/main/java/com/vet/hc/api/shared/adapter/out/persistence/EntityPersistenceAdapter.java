package com.vet.hc.api.shared.adapter.out.persistence;

import static com.vet.hc.api.shared.adapter.in.util.AnsiShortcuts.fgBrightBlack;
import static com.vet.hc.api.shared.adapter.in.util.AnsiShortcuts.fgBrightBlue;
import static com.vet.hc.api.shared.domain.result.Result.failure;
import static com.vet.hc.api.shared.domain.result.Result.ok;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vet.hc.api.shared.application.mapper.BasicMapper;
import com.vet.hc.api.shared.domain.failure.ExceptionFailureHandler;
import com.vet.hc.api.shared.domain.failure.Failure;
import com.vet.hc.api.shared.domain.failure.GenericFailure;
import com.vet.hc.api.shared.domain.query.FieldUpdate;
import com.vet.hc.api.shared.domain.result.Result;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class EntityPersistenceAdapter<E, ID, DTO, F extends Failure, R extends JpaRepository<E, ID>> {
    private final R repository;
    private final String entityName;
    protected final ExceptionFailureHandler<F> exceptionFailureHandler;
    protected final BasicMapper<E, DTO, F> mapper;
    protected final Class<E> entityClass;
    @PersistenceContext
    protected EntityManager entityManager;

    @SuppressWarnings("unchecked")

    public EntityPersistenceAdapter(
            R repository,
            ExceptionFailureHandler<F> exceptionFailureHandler,
            BasicMapper<E, DTO, F> mapper) {
        this.repository = repository;
        this.exceptionFailureHandler = exceptionFailureHandler;
        this.mapper = mapper;

        this.entityClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        this.entityName = entityClass.getSimpleName();
    }

    public List<E> findAll() {
        log.debug("Finding all {}",
                fgBrightBlack(entityName));

        return repository.findAll();
    }

    public Optional<E> findById(ID id) {
        log.debug("Finding {} by id: {}",
                fgBrightBlack(entityName),
                fgBrightBlue(id));

        return repository.findById(id);
    }

    public Result<E, F> save(E entity) {
        try {
            log.debug("Saving {}: {}",
                    fgBrightBlack(entityName),
                    fgBrightBlue(entity));

            return ok(repository.save(entity));
        } catch (Exception e) {
            return failure(exceptionFailureHandler.handle(e));
        }
    }

    public Result<Void, F> deleteById(ID id) {
        try {
            log.debug("Deleting {} by id: {}",
                    fgBrightBlack(entityName),
                    fgBrightBlue(id));

            if (!repository.existsById(id)) {
                log.debug("{} not found",
                        fgBrightBlack(entityName));

                return failure(mapper.toFailure(GenericFailure.NOT_FOUND));
            }

            repository.deleteById(id);

            return ok();
        } catch (Exception e) {
            return failure(exceptionFailureHandler.handle(e));
        }
    }

    public boolean existsById(ID id) {
        log.debug("Checking if {} exists by id: {}",
                fgBrightBlack(entityName),
                fgBrightBlue(id));

        return repository.existsById(id);
    }

    public Result<E, F> update(ID id, FieldUpdate necessaryField, FieldUpdate... fieldUpdates) {
        FieldUpdate[] allFieldUpdates = new FieldUpdate[fieldUpdates.length + 1];
        allFieldUpdates[0] = necessaryField;
        System.arraycopy(fieldUpdates, 0, allFieldUpdates, 1, fieldUpdates.length);

        return update(id, allFieldUpdates);
    }

    private Result<E, F> update(ID id, FieldUpdate... fieldUpdates) {
        try {
            log.debug("Updating {} by id: {}",
                    fgBrightBlack(entityName),
                    fgBrightBlue(id));

            if (!repository.existsById(id)) {
                log.debug("{} not found",
                        fgBrightBlack(entityName));

                return failure(mapper.toFailure(GenericFailure.NOT_FOUND));
            }

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaUpdate<E> update = cb.createCriteriaUpdate(entityClass);
            Root<E> root = update.from(entityClass);

            // Set each field for the update
            applyFieldUpdates(update, root, fieldUpdates);

            // Add the condition for the specific ID
            update.where(cb.equal(root.get(getIdFieldName()), id));

            // Execute the update
            int rowsAffected = entityManager.createQuery(update).executeUpdate();

            log.debug("{} rows affected",
                    fgBrightBlack(rowsAffected));

            E updatedEntity = repository.findById(id).get();

            return ok(updatedEntity);
        } catch (Exception e) {
            return failure(exceptionFailureHandler.handle(e));
        }
    }

    protected Path<?> resolvePath(Root<E> root, String field) {
        try {
            Path<?> path = root;
            for (String part : field.split("\\.")) {
                path = path.get(part);
            }
            return path;
        } catch (IllegalArgumentException e) {
            log.warn("Invalid field path: {}", field);
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    protected void applyFieldUpdates(CriteriaUpdate<E> update, Root<E> root, FieldUpdate[] fieldUpdates) {
        Arrays.stream(fieldUpdates)
                .forEach(fu -> update.set((Path<Object>) resolvePath(root, fu.getField()), fu.getValue()));
    }

    public Result<E, F> update(ID id, Collection<FieldUpdate> fieldUpdates) {
        return update(id, fieldUpdates.toArray(FieldUpdate[]::new));
    }

    private String getIdFieldName() {
        for (var field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(jakarta.persistence.Id.class)) {
                return field.getName();
            }
        }

        return null;
    }
}
