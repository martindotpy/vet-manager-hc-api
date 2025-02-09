package com.vet.hc.api.image.core.application.port.in;

import com.vet.hc.api.image.core.domain.failure.ImageFailure;
import com.vet.hc.api.image.core.domain.model.enums.ImageMimeType;
import com.vet.hc.api.shared.domain.result.Result;

/**
 * Save image port.
 */
public interface SaveImagePort {
    /**
     * Save image.
     *
     * @param image The image.
     * @param type  The mime type.
     * @return The result of the operation
     */
    Result<String, ImageFailure> save(byte[] image, ImageMimeType type);
}
