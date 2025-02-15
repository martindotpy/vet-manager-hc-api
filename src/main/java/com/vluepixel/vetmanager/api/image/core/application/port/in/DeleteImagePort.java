package com.vluepixel.vetmanager.api.image.core.application.port.in;

/**
 * Delete image port.
 */
public interface DeleteImagePort {
    /**
     * Delete image.
     *
     * @param imageName The image name.
     */
    void delete(String imageName);
}
