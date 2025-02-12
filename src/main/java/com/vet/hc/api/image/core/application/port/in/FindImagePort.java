package com.vet.hc.api.image.core.application.port.in;

import java.io.OutputStream;

/**
 * Find image port.
 */
public interface FindImagePort {
    /**
     * Find an image by its name.
     *
     * @param imageName The image name.
     * @return The success or a failure
     */
    void findByName(String imageName, OutputStream outputStream);
}
