package com.vluepixel.vetmanager.api.image.core.application.usecase;

import static com.vluepixel.vetmanager.api.shared.adapter.in.util.AnsiShortcuts.fgBrightRed;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Value;

import com.vluepixel.vetmanager.api.image.core.application.port.in.DeleteImagePort;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.shared.domain.exception.InternalServerErrorException;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@RequiredArgsConstructor
public final class DeleteImageUseCase implements DeleteImagePort {
    @Value("${spring.image.path}")
    private String imagesPath;

    @Override
    public void delete(String imageName) {
        MDC.put("operationId", "Image name " + imageName);
        log.info("Deleting image with name");

        try {
            // Delete the image
            Path imagePath = Path.of(System.getProperty("user.dir"), imagesPath, imageName.toString());
            Files.delete(imagePath);
        } catch (NoSuchFileException e) {
            log.error("Image not found: {}",
                    fgBrightRed(e.getMessage()));

            throw new NotFoundException("Image", "name", imageName);
        } catch (Exception e) {
            log.error("Error deleting image with name: {}",
                    fgBrightRed(imageName));

            throw new InternalServerErrorException(e);
        }
    }
}
