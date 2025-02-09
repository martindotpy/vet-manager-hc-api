package com.vet.hc.api.image.core.application.port.in;

import java.io.OutputStream;

import com.vet.hc.api.image.core.domain.failure.ImageFailure;
import com.vet.hc.api.shared.domain.result.Result;

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
    Result<Void, ImageFailure> findByName(String imageName, OutputStream outputStream);
}
