package com.vet.hc.api.image.core.application.port.in;

/**
 * Delete image port.
 */
public interface DeleteImagePort {
    /**
     * Delete image.
     *
     * @param imageName The image name.
     * @return The result of the operation
     */
    void delete(String imageName);
}
