package com.vluepixel.vetmanager.api.image.core.integration;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.vluepixel.vetmanager.api.base.BaseIntegrationTest;
import com.vluepixel.vetmanager.api.image.core.application.port.in.DeleteImagePort;
import com.vluepixel.vetmanager.api.image.core.application.port.in.SaveImagePort;
import com.vluepixel.vetmanager.api.image.core.domain.model.enums.ImageMimeType;
import com.vluepixel.vetmanager.api.shared.domain.exception.NotFoundException;

/**
 * Image integration test.
 */
class ImageIntegrationTest extends BaseIntegrationTest {
    // -----------------------------------------------------------------------------------------------------------------
    // Without authentication:
    // It is not necessary to have authentication to upload an image.
    // -----------------------------------------------------------------------------------------------------------------

    @Autowired
    private SaveImagePort saveImagePort;
    @Autowired
    private DeleteImagePort deleteImagePort;
    private static String imageName;

    @Order(1)
    @Test
    void noUser_UploadImage_Ok() throws Exception {
        var result = saveImagePort.save("image".getBytes(), ImageMimeType.PNG);

        String url = result;
        String[] urlSplit = url.split("/");
        ImageIntegrationTest.imageName = urlSplit[urlSplit.length - 1];
    }

    @Order(2)
    @Test
    void noUser_GetImage_Ok() throws Exception {
        mockMvc.perform(get("/image/{name}", imageName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG));
    }

    @Order(3)
    @Test
    void noUser_RemoveImage_Ok() throws Exception {
        deleteImagePort.delete(imageName);
    }

    @Order(4)
    @Test
    void noUser_GetImageTest_NotFound() throws Exception {
        mockMvc.perform(get("/image/{name}", imageName))
                .andExpect(status().isNotFound());
    }

    @Order(5)
    @Test
    void noUser_RemoveImageTest_NotFound() throws Exception {
        assertThrowsExactly(NotFoundException.class, () -> deleteImagePort.delete(imageName));
    }
}
