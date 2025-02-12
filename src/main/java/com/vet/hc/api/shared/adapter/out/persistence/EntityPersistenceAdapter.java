package com.vet.hc.api.shared.adapter.out.persistence;

import static com.vet.hc.api.shared.adapter.in.util.AnsiShortcuts.fgBrightBlack;
import static com.vet.hc.api.shared.adapter.in.util.AnsiShortcuts.fgBrightBlue;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vet.hc.api.shared.application.mapper.BasicMapper;
import com.vet.hc.api.shared.domain.exception.NotFoundException;
import com.vet.hc.api.shared.domain.exception.RepositoryException;
import com.vet.hc.api.shared.domain.query.FieldUpdate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class EntityPersistenceAdapter<E, ID, DTO, R extends JpaRepository<E, ID>> {
    private final R repository;
    private final String entityName;
    protected final BasicMapper<E, DTO> mapper;
    protected final Class<E> entityClass;
    @PersistenceContext
    protected EntityManager entityManager;

    @SuppressWarnings("unchecked")

    public EntityPersistenceAdapter(
            R repository,
            BasicMapper<E, DTO> mapper) {
        this.repository = repository;
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

    public E save(E entity) {
        try {
            log.debug("Saving {}: {}",
                    fgBrightBlack(entityName),
                    fgBrightBlue(entity));

            return repository.save(entity);
        } catch (Exception e) {
            throw new RepositoryException(e, entityClass);
        }
    }

    public void deleteById(ID id) {
        try {
            log.debug("Deleting {} by id: {}",
                    fgBrightBlack(entityName),
                    fgBrightBlue(id));

            if (!repository.existsById(id)) {
                log.debug("{} not found",
                        fgBrightBlack(entityName));

                throw new NotFoundException(entityName, id);
            }

            repository.deleteById(id);
        } catch (Exception e) {
            throw new RepositoryException(e, entityClass);
        }
    }

    public boolean existsById(ID id) {
        log.debug("Checking if {} exists by id: {}",
                fgBrightBlack(entityName),
                fgBrightBlue(id));

        return repository.existsById(id);
    }

    public E update(ID id, FieldUpdate necessaryField, FieldUpdate... fieldUpdates) {
        FieldUpdate[] allFieldUpdates = new FieldUpdate[fieldUpdates.length + 1];
        allFieldUpdates[0] = necessaryField;
        System.arraycopy(fieldUpdates, 0, allFieldUpdates, 1, fieldUpdates.length);

        return update(id, allFieldUpdates);
    }

    private E update(ID id, FieldUpdate... fieldUpdates) {
        try {
            log.debug("Updating {} by id: {}",
                    fgBrightBlack(entityName),
                    fgBrightBlue(id));

            if (!repository.existsById(id)) {
                log.debug("{} not found",
                        fgBrightBlack(entityName));

                throw new RuntimeException("Entity not found");
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

            return updatedEntity;
        } catch (Exception e) {
            throw new RepositoryException(e, entityClass);
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

    public E update(ID id, Collection<FieldUpdate> fieldUpdates) {
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
