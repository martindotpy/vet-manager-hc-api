package com.vluepixel.vetmanager.api.user.core.profile_image.integration;

import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_ADMIN_JWT;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_USER_JWT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.vluepixel.vetmanager.api.base.BaseIntegrationTest;

/**
 * Integration test for the update user profile image functionality.
 */
public class UpdateProfileImageIntetrationTest extends BaseIntegrationTest {
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
                .andExpect(status().isForbidden());
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
                })
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isForbidden());
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
                .andExpect(status().isForbidden());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // With authentication:
    // -----------------------------------------------------------------------------------------------------------------

    // Role: USER
    @Test
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
                .andExpect(status().isOk());
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
                .andExpect(status().isUnprocessableEntity());
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
                .andExpect(status().isUnprocessableEntity());
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
                .andExpect(status().isOk());
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
                .andExpect(status().isUnprocessableEntity());
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
                .andExpect(status().isUnprocessableEntity());
    }
}
