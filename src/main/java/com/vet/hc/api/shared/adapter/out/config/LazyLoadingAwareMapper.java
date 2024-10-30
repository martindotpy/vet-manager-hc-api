package com.vet.hc.api.shared.adapter.out.config;

import java.util.Collection;

import org.hibernate.Hibernate;

/**
 * Interface to check if a collection is lazy loaded or not.
 */
public interface LazyLoadingAwareMapper {
    /**
     * Check if a collection is lazy loaded.
     *
     * @param collection Collection to check.
     * @return True if the collection is not lazy loaded, false otherwise
     */
    default boolean isNotLazyLoaded(Collection<?> collection) {
        if (Hibernate.isInitialized(collection)) {
            return true;
        }

        return false;
    }
}
