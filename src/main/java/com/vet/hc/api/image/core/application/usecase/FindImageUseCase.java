package com.vet.hc.api.image.core.application.usecase;

import static com.vet.hc.api.shared.domain.result.Result.failure;
import static com.vet.hc.api.shared.domain.result.Result.ok;

import java.io.OutputStream;
import java.nio.file.Path;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;

import com.vet.hc.api.image.core.application.port.in.FindImagePort;
import com.vet.hc.api.image.core.domain.failure.ImageFailure;
import com.vet.hc.api.shared.application.annotations.UseCase;
import com.vet.hc.api.shared.domain.result.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Find image use case.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class FindImageUseCase implements FindImagePort {
    @Value("${spring.image.path}")
    private String imagePath;

    @Override
    public Result<Void, ImageFailure> findByName(String imageName, OutputStream outputStream) {
        MDC.put("operationId", "Image id " + imageName);
        log.info("Searching image");

        Path path = Path.of(System.getProperty("user.dir"), imagePath, imageName.toString());

        if (!path.toFile().exists()) {
            log.error("Image not found");

            return failure(ImageFailure.NOT_FOUND);
        }

        try (var inputStream = path.toUri().toURL().openStream()) {
            byte[] image = inputStream.readAllBytes();

            outputStream.write(image);

            log.info("Image found");

            return ok();
        } catch (Exception e) {
            log.error("Error while reading image", e);

            return failure(ImageFailure.UNEXPECTED);
        }
    }
}
