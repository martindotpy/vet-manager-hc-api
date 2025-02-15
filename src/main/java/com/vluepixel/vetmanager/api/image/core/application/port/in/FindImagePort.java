package com.vluepixel.vetmanager.api.image.core.application.port.in;

import java.io.OutputStream;

/**
 * Find image port.
 */
public interface FindImagePort {
    /**
     * Find an image by its name. The image will be written to the output stream.
     *
     * @param imageName The image name.
     */
    void findByName(String imageName, OutputStream outputStream);
}
