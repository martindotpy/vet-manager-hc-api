package com.vet.hc.api.image.core.application.usecase;

import static com.vet.hc.api.shared.adapter.in.util.AnsiShortcuts.fgBrightRed;
import static com.vet.hc.api.shared.domain.result.Result.failure;
import static com.vet.hc.api.shared.domain.result.Result.ok;

import java.nio.file.Files;
import java.nio.file.Path;

import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Value;

import com.vet.hc.api.image.core.application.port.in.DeleteImagePort;
import com.vet.hc.api.image.core.domain.failure.ImageFailure;
import com.vet.hc.api.shared.application.annotations.UseCase;
import com.vet.hc.api.shared.domain.result.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@RequiredArgsConstructor
public final class DeleteImageUseCase implements DeleteImagePort {
    @Value("${spring.image.path}")
    private String imagesPath;

    @Override
    public Result<Void, ImageFailure> delete(String imageName) {
        MDC.put("operationId", "Image name " + imageName);
        log.info("Deleting image with name");

        try {
            // Delete the image
            Path imagePath = Path.of(System.getProperty("user.dir"), imagesPath, imageName.toString());
            Files.delete(imagePath);
        } catch (NoSuchFieldError e) {
            log.error("Image not found: {}",
                    fgBrightRed(e.getMessage()));

            return failure(ImageFailure.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting image with name: {}", imageName, e);

            return failure(ImageFailure.UNEXPECTED);
        }

        return ok();
    }
}
