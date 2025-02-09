package com.vet.hc.api.image.core.application.port.in;

import com.vet.hc.api.image.core.domain.failure.ImageFailure;
import com.vet.hc.api.shared.domain.result.Result;

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
    Result<Void, ImageFailure> delete(String imageName);
}
