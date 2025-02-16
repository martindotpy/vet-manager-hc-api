package com.vluepixel.vetmanager.api.image.core.application.usecase;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;

import com.vluepixel.vetmanager.api.image.core.application.port.in.FindImagePort;
import com.vluepixel.vetmanager.api.shared.application.annotation.UseCase;
import com.vluepixel.vetmanager.api.shared.domain.exception.InternalServerErrorException;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;

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
    public void findByName(String imageName, OutputStream outputStream) {
        MDC.put("operationId", "Image id " + imageName);
        log.info("Searching image");

        Path path = Path.of(System.getProperty("user.dir"), imagePath, imageName.toString());

        if (!path.toFile().exists()) {
            log.error("Image not found");

            throw new NotFoundException("Imagen", "nombre", imageName);
        }

        try (InputStream inputStream = path.toUri().toURL().openStream()) {
            byte[] image = inputStream.readAllBytes();

            outputStream.write(image);

            log.info("Image found");

        } catch (Exception e) {
            log.error("Error while reading image");

            throw new InternalServerErrorException(e);
        }
    }
}
