package com.vluepixel.vetmanager.api.race.core.integration;

import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_ADMIN_JWT;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_USER_JWT;
import static com.vluepixel.vetmanager.api.race.core.data.UpdateRaceDataProvider.INVALID_ID_NEGATIVE_UPDATE_RACE_REQUEST;
import static com.vluepixel.vetmanager.api.race.core.data.UpdateRaceDataProvider.INVALID_ID_NOT_FOUND_UPDATE_RACE_REQUEST;
import static com.vluepixel.vetmanager.api.race.core.data.UpdateRaceDataProvider.INVALID_ID_NULL_UPDATE_RACE_REQUEST;
import static com.vluepixel.vetmanager.api.race.core.data.UpdateRaceDataProvider.INVALID_NAME_BLANK_UPDATE_RACE_REQUEST;
import static com.vluepixel.vetmanager.api.race.core.data.UpdateRaceDataProvider.INVALID_NAME_EMPTY_UPDATE_RACE_REQUEST;
import static com.vluepixel.vetmanager.api.race.core.data.UpdateRaceDataProvider.INVALID_NAME_NULL_UPDATE_RACE_REQUEST;
import static com.vluepixel.vetmanager.api.race.core.data.UpdateRaceDataProvider.INVALID_NAME_TOO_LONG_UPDATE_RACE_REQUEST;
import static com.vluepixel.vetmanager.api.race.core.data.UpdateRaceDataProvider.INVALID_SPECIES_ID_NEGATIVE_UPDATE_RACE_REQUEST;
import static com.vluepixel.vetmanager.api.race.core.data.UpdateRaceDataProvider.INVALID_SPECIES_ID_NOT_FOUND_UPDATE_RACE_REQUEST;
import static com.vluepixel.vetmanager.api.race.core.data.UpdateRaceDataProvider.INVALID_SPECIES_ID_NULL_UPDATE_RACE_REQUEST;
import static com.vluepixel.vetmanager.api.race.core.data.UpdateRaceDataProvider.VALID_NAME_MAX_LENGTH_UPDATE_RACE_REQUEST;
import static com.vluepixel.vetmanager.api.race.core.data.UpdateRaceDataProvider.VALID_UPDATE_RACE_REQUEST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import com.vluepixel.vetmanager.api.base.BaseIntegrationTest;

/**
 * Integration tests for the update race use case.
 */
public class UpdateRaceIntegrationTest extends BaseIntegrationTest {
    private static final String MESSAGE_OK = "Raza actualizada exitosamente";
    private static final String MESSAGE_NOT_FOUND = "Raza no encontrado(a)";
    // -----------------------------------------------------------------------------------------------------------------
    // Without authentication:
    // -----------------------------------------------------------------------------------------------------------------

    @Test
    @DirtiesContext
    void noUser_UpdateRaceWithValidArguments_Ok() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_RACE_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // - Validations

    // ID
    @Test
    void noUser_UpdateRaceWithInvalidArguments_ID_NotFound_NotFound() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ID_NOT_FOUND_UPDATE_RACE_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_UpdateRaceWithInvalidArguments_ID_Negative_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ID_NEGATIVE_UPDATE_RACE_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_UpdateRaceWithInvalidArguments_ID_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ID_NULL_UPDATE_RACE_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // Name
    @Test
    void noUser_UpdateRaceWithInvalidArguments_Name_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_TOO_LONG_UPDATE_RACE_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    @DirtiesContext
    void noUser_UpdateRaceWithValidArguments_Name_MaxLength_Ok() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_NAME_MAX_LENGTH_UPDATE_RACE_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_UpdateRaceWithInvalidArguments_Name_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_BLANK_UPDATE_RACE_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_UpdateRaceWithInvalidArguments_Name_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_EMPTY_UPDATE_RACE_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_UpdateRaceWithInvalidArguments_Name_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_NULL_UPDATE_RACE_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // Species ID
    @Test
    void noUser_UpdateRaceWithInvalidArguments_SpeciesID_NotFound_NotFound() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_SPECIES_ID_NOT_FOUND_UPDATE_RACE_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_UpdateRaceWithInvalidArguments_SpeciesID_Negative_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_SPECIES_ID_NEGATIVE_UPDATE_RACE_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    @Test
    void noUser_UpdateRaceWithInvalidArguments_SpeciesID_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_SPECIES_ID_NULL_UPDATE_RACE_REQUEST)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(MESSAGE_FORBIDDEN));
    }

    // -----------------------------------------------------------------------------------------------------------------
    // With authentication:
    // -----------------------------------------------------------------------------------------------------------------

    // Role: USER
    @Test
    @DirtiesContext
    void user_UpdateRaceWithValidArguments_Ok() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_RACE_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content.id").value(VALID_UPDATE_RACE_REQUEST.getId()),
                        jsonPath("$.content.name").value(VALID_UPDATE_RACE_REQUEST.getName()),
                        jsonPath("$.content.species.id").value(VALID_UPDATE_RACE_REQUEST.getSpeciesId()),
                        jsonPath("$.content.species.name").value("Perro"));
    }

    // - Validations

    // ID
    @Test
    void user_UpdateRaceWithInvalidArguments_ID_NotFound_NotFound() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ID_NOT_FOUND_UPDATE_RACE_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(MESSAGE_NOT_FOUND));
    }

    @Test
    void user_UpdateRaceWithInvalidArguments_ID_Negative_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ID_NEGATIVE_UPDATE_RACE_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("id"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El id debe ser mayor a 0"));
    }

    @Test
    void user_UpdateRaceWithInvalidArguments_ID_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ID_NULL_UPDATE_RACE_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("id"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El id es requerido"));
    }

    // Name
    @Test
    void user_UpdateRaceWithInvalidArguments_Name_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_TOO_LONG_UPDATE_RACE_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre no puede tener más de 50 caracteres"));
    }

    @Test
    @DirtiesContext
    void user_UpdateRaceWithValidArguments_Name_MaxLength_Ok() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_NAME_MAX_LENGTH_UPDATE_RACE_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content.id").value(VALID_NAME_MAX_LENGTH_UPDATE_RACE_REQUEST.getId()),
                        jsonPath("$.content.name").value(VALID_NAME_MAX_LENGTH_UPDATE_RACE_REQUEST.getName()),
                        jsonPath("$.content.species.id")
                                .value(VALID_NAME_MAX_LENGTH_UPDATE_RACE_REQUEST.getSpeciesId()),
                        jsonPath("$.content.species.name").value("Perro"));
    }

    @Test
    void user_UpdateRaceWithInvalidArguments_Name_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_BLANK_UPDATE_RACE_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es requerido"));
    }

    @Test
    void user_UpdateRaceWithInvalidArguments_Name_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_EMPTY_UPDATE_RACE_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es requerido"));
    }

    @Test
    void user_UpdateRaceWithInvalidArguments_Name_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_NULL_UPDATE_RACE_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es requerido"));
    }

    // Species ID
    @Test // TODO
    void user_UpdateRaceWithInvalidArguments_SpeciesID_NotFound_NotFound() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_SPECIES_ID_NOT_FOUND_UPDATE_RACE_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(MESSAGE_NOT_FOUND));
    }

    @Test
    void user_UpdateRaceWithInvalidArguments_SpeciesID_Negative_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_SPECIES_ID_NEGATIVE_UPDATE_RACE_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("species_id"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El id de la especie debe ser mayor a 0"));
    }

    @Test
    void user_UpdateRaceWithInvalidArguments_SpeciesID_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_SPECIES_ID_NULL_UPDATE_RACE_REQUEST))
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("species_id"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El id de la especie es requerido"));
    }

    // Role: ADMIN
    @Test
    @DirtiesContext
    void admin_UpdateRaceWithValidArguments_Ok() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_UPDATE_RACE_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content.id").value(VALID_UPDATE_RACE_REQUEST.getId()),
                        jsonPath("$.content.name").value(VALID_UPDATE_RACE_REQUEST.getName()),
                        jsonPath("$.content.species.id").value(VALID_UPDATE_RACE_REQUEST.getSpeciesId()),
                        jsonPath("$.content.species.name").value("Perro"));
    }

    // - Validations

    // ID
    @Test
    void admin_UpdateRaceWithInvalidArguments_ID_NotFound_NotFound() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ID_NOT_FOUND_UPDATE_RACE_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(MESSAGE_NOT_FOUND));
    }

    @Test
    void admin_UpdateRaceWithInvalidArguments_ID_Negative_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ID_NEGATIVE_UPDATE_RACE_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("id"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El id debe ser mayor a 0"));
    }

    @Test
    void admin_UpdateRaceWithInvalidArguments_ID_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_ID_NULL_UPDATE_RACE_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("id"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El id es requerido"));
    }

    // Name
    @Test
    void admin_UpdateRaceWithInvalidArguments_Name_TooLong_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_TOO_LONG_UPDATE_RACE_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre no puede tener más de 50 caracteres"));
    }

    @Test
    @DirtiesContext
    void admin_UpdateRaceWithValidArguments_Name_MaxLength_Ok() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_NAME_MAX_LENGTH_UPDATE_RACE_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content.id").value(VALID_NAME_MAX_LENGTH_UPDATE_RACE_REQUEST.getId()),
                        jsonPath("$.content.name").value(VALID_NAME_MAX_LENGTH_UPDATE_RACE_REQUEST.getName()),
                        jsonPath("$.content.species.id")
                                .value(VALID_NAME_MAX_LENGTH_UPDATE_RACE_REQUEST.getSpeciesId()),
                        jsonPath("$.content.species.name").value("Perro"));
    }

    @Test
    void admin_UpdateRaceWithInvalidArguments_Name_Blank_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_BLANK_UPDATE_RACE_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es requerido"));
    }

    @Test
    void admin_UpdateRaceWithInvalidArguments_Name_Empty_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_EMPTY_UPDATE_RACE_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es requerido"));
    }

    @Test
    void admin_UpdateRaceWithInvalidArguments_Name_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_NAME_NULL_UPDATE_RACE_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("name"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El nombre es requerido"));
    }

    // Species ID
    @Test // TODO
    void admin_UpdateRaceWithInvalidArguments_SpeciesID_NotFound_NotFound() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_SPECIES_ID_NOT_FOUND_UPDATE_RACE_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(MESSAGE_NOT_FOUND));
    }

    @Test
    void admin_UpdateRaceWithInvalidArguments_SpeciesID_Negative_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_SPECIES_ID_NEGATIVE_UPDATE_RACE_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("species_id"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El id de la especie debe ser mayor a 0"));
    }

    @Test
    void admin_UpdateRaceWithInvalidArguments_SpeciesID_Null_UnprocessableEntity() throws Exception {
        mockMvc.perform(put("/race")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(INVALID_SPECIES_ID_NULL_UPDATE_RACE_REQUEST))
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details").isArray(),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("species_id"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages").value("El id de la especie es requerido"));
    }
}
