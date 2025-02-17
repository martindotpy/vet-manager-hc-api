package com.vluepixel.vetmanager.api.user.core.profileimage.integration;

import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.ADMIN_DTO;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_ADMIN_JWT;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_USER_JWT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.jayway.jsonpath.JsonPath;
import com.vluepixel.vetmanager.api.base.BaseIntegrationTest;
import com.vluepixel.vetmanager.api.image.core.application.port.in.DeleteImagePort;

/**
 * Integration test for the update user profile image functionality.
 */
public class UpdateProfileImageIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private DeleteImagePort deleteImagePort;

    private static final Function<String, String> MESSAGE_NOT_FOUND = parameter -> String
            .format("Imagen con nombre %s no encontrado(a)", parameter);
    private static final String MESSAGE_OK = "Image del perfil de usuario ha sido actualizado correctamente";
    // -----------------------------------------------------------------------------------------------------------------
    // Without authentication:
    // -----------------------------------------------------------------------------------------------------------------

    @Test
    void noUser_UpdateCurrentUserProfileImageWithValidArguments_Forbidden() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile(
                "image_file",
                "profile.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "fake image content".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/user/profile-image")
                .file(imageFile)
                .with(request -> {
                    request.setMethod("PUT");
                    return request;
                }))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_UpdateCurrentUserProfileImageWithInValidArguments_File_NotImage_Forbidden() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile(
                "image_file",
                "profile.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                "fake image content".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/user/profile-image")
                .file(imageFile)
                .with(request -> {
                    request.setMethod("PUT");
                    return request;
                }))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_UpdateCurrentUserProfileImageWithInValidArguments_File_Null_Forbidden() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile(
                "image_file",
                "profile.png",
                MediaType.IMAGE_PNG_VALUE,
                new byte[0]);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/user/profile-image")
                .file(imageFile)
                .with(request -> {
                    request.setMethod("PUT");
                    return request;
                }))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // -----------------------------------------------------------------------------------------------------------------
    // With authentication:
    // -----------------------------------------------------------------------------------------------------------------

    // /user/profile-image
    // Role: USER
    @Test
    @DirtiesContext
    void user_UpdateCurrentUserProfileImageWithValidArguments_Ok() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile(
                "image_file",
                "profile.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "fake image content".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/user/profile-image")
                .file(imageFile)
                .with(request -> {
                    request.setMethod("PUT");
                    return request;
                })
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content.jwt").isString());
    }

    @Test
    void user_UpdateCurrentUserProfileImageWithInValidArguments_File_NotImage_UnprocessableEntity() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile(
                "image_file",
                "profile.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                "fake image content".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/user/profile-image")
                .file(imageFile)
                .with(request -> {
                    request.setMethod("PUT");
                    return request;
                })
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("param.image_file"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages[0]")
                                .value("Valid values for `param.image_file` are: PNG, JPG, JPEG, WEBP"));
    }

    @Test
    void user_UpdateCurrentUserProfileImageWithInValidArguments_File_Null_UnprocessableEntity() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile(
                "image_file",
                "profile.png",
                MediaType.IMAGE_PNG_VALUE,
                new byte[0]);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/user/profile-image")
                .file(imageFile)
                .with(request -> {
                    request.setMethod("PUT");
                    return request;
                })
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("param.image_file"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages[0]")
                                .value("El archivo de imagen no puede estar vacío"));
    }

    // Role: ADMIN
    // /user/profile-image
    @Test
    void admin_UpdateCurrentUserProfileImageWithValidArguments_Ok() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile(
                "image_file",
                "profile.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "fake image content".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/user/profile-image")
                .file(imageFile)
                .with(request -> {
                    request.setMethod("PUT");
                    return request;
                })
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content.jwt").isString());
    }

    @Test
    void admin_UpdateCurrentUserProfileImageWithInValidArguments_File_NotImage_UnprocessableEntity() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile(
                "image_file",
                "profile.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                "fake image content".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/user/profile-image")
                .file(imageFile)
                .with(request -> {
                    request.setMethod("PUT");
                    return request;
                })
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("param.image_file"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages[0]")
                                .value("Valid values for `param.image_file` are: PNG, JPG, JPEG, WEBP"));
    }

    @Test
    void admin_UpdateCurrentUserProfileImageWithInValidArguments_File_Null_UnprocessableEntity() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile(
                "image_file",
                "profile.png",
                MediaType.IMAGE_PNG_VALUE,
                new byte[0]);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/user/profile-image")
                .file(imageFile)
                .with(request -> {
                    request.setMethod("PUT");
                    return request;
                })
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("param.image_file"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages[0]").value("El archivo de imagen no puede estar vacío"));
    }

    // /user/profile-image/{id}
    @Test
    @DirtiesContext
    void admin_UpdateCurrentUserProfileImageAsOtherUserWithValidArguments_Ok() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile(
                "image_file",
                "profile.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "fake image content".getBytes());

        var result = mockMvc.perform(MockMvcRequestBuilders.multipart("/user/profile-image/{id}", ADMIN_DTO.getId())
                .file(imageFile)
                .with(request -> {
                    request.setMethod("PUT");
                    return request;
                })
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content.id").value(ADMIN_DTO.getId()),
                        jsonPath("$.content.first_name").value(ADMIN_DTO.getFirstName()),
                        jsonPath("$.content.last_name").value(ADMIN_DTO.getLastName()),
                        jsonPath("$.content.email").value(ADMIN_DTO.getEmail()),
                        jsonPath("$.content.roles").value(ADMIN_DTO.getRoles().toArray()[0].toString()),
                        jsonPath("$.content.profile_image_url").isString())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        String profileImageUrl = JsonPath.read(responseContent, "$.content.profile_image_url");
        String imageName = profileImageUrl.substring(profileImageUrl.lastIndexOf("/") + 1);

        deleteImagePort.delete(imageName);

        mockMvc.perform(get("/image/{name}", imageName))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(MESSAGE_NOT_FOUND.apply(imageName)));
    }
}
