package com.vluepixel.vetmanager.api.client.core.integration;

import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_ADMIN_JWT;
import static com.vluepixel.vetmanager.api.auth.core.data.AuthDataProvider.BEARER_USER_JWT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.vluepixel.vetmanager.api.base.BaseIntegrationTest;

/**
 * Integration tests for the get user use case.
 */
public class GetClientIntegrationTest extends BaseIntegrationTest {
    private static final String MESSAGE_OK = "Tipos de cliente encontradas";
    // -----------------------------------------------------------------------------------------------------------------
    // Without authentication:
    // -----------------------------------------------------------------------------------------------------------------

    @Test
    void noUser_GetClient_Forbidden() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", "1");
        queryParams.add("size", "10");

        mockMvc.perform(get("/client")
                .queryParams(queryParams))
                .andExpect(status().isForbidden());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // With authentication:
    // -----------------------------------------------------------------------------------------------------------------

    // Role: USER
    @Test
    void user_GetClient_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", "1");
        queryParams.add("size", "10");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_USER_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(2),
                        jsonPath("$.total_pages").value(1),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(2));
    }

    // Role: ADMIN
    @Test
    void admin_GetClient_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", "1");
        queryParams.add("size", "10");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(2),
                        jsonPath("$.total_pages").value(1),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(2));
    }

    @Test
    void admin_GetClientWithoutParams_Ok() throws Exception {
        mockMvc.perform(get("/client")
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(2),
                        jsonPath("$.total_pages").value(1),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(2));
    }

    // ID
    @Test
    void admin_GetClientWithInvalidParams_ID_Invalid_UnprocessableEntity() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("id", "invalid");
        queryParams.add("page", "1");
        queryParams.add("size", "10");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("id"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages[0]").value(String.format(
                                "Illegal argument: For input string: \"%s\"", queryParams.get("id").toArray()[0])));
    }

    @Test
    void admin_GetClientWithInvalidParams_ID_NotFound_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("id", "20");
        queryParams.add("page", "1");
        queryParams.add("size", "10");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(0),
                        jsonPath("$.total_pages").value(0),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(0));
    }

    @Test
    void admin_GetClientWithInvalidParams_ID_Blank_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("id", " ");
        queryParams.add("page", "1");
        queryParams.add("size", "10");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(2),
                        jsonPath("$.total_pages").value(1),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(2));
    }

    @Test
    void admin_GetClientWithInvalidParams_ID_Null_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("id", null);
        queryParams.add("page", "1");
        queryParams.add("size", "10");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(2),
                        jsonPath("$.total_pages").value(1),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(2));
    }

    // Page
    @Test
    void admin_GetClientWithInvalidParams_Page_NotNumber_UnprocessableEntity() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", "abcd");
        queryParams.add("size", "10");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("page"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages[0]").value(
                                String.format("Illegal argument: For input string: \"%s\"",
                                        queryParams.get("page").toArray()[0])));
    }

    @Test
    void admin_GetClientWithInvalidParams_Page_Negative_UnprocessableEntity() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", "-1");
        queryParams.add("size", "10");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("query.page"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages[0]").value("La página debe ser mayor a 0"));
    }

    @Test
    void admin_GetClientWithInvalidParams_Page_Blank_UnprocessableEntity() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", " ");
        queryParams.add("size", "10");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(2),
                        jsonPath("$.total_pages").value(1),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(2));
    }

    @Test
    void admin_GetClientWithInvalidParams_Page_Null_UnprocessableEntity() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", null);
        queryParams.add("size", "10");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(2),
                        jsonPath("$.total_pages").value(1),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(2));
    }

    // Size
    @Test
    void admin_GetClientWithInvalidParams_Size_NotNumber_UnprocessableEntity() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", "1");
        queryParams.add("size", "abcd");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("size"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages[0]").value(
                                String.format("Illegal argument: For input string: \"%s\"",
                                        queryParams.get("size").toArray()[0])));
    }

    @Test
    void admin_GetClientWithInvalidParams_Size_Negative_UnprocessableEntity() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", "1");
        queryParams.add("size", "-1");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("query.size"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages[0]").value("El tamaño debe ser mayor a 0"));
    }

    @Test
    void admin_GetClientWithInvalidParams_Size_Largest_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", "1");
        queryParams.add("size", "111");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(111),
                        jsonPath("$.total_elements").value(2),
                        jsonPath("$.total_pages").value(1),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(2));
    }

    @Test
    void admin_GetClientWithInvalidParams_Size_Blank_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", "1");
        queryParams.add("size", " ");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(2),
                        jsonPath("$.total_pages").value(1),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(2));
    }

    @Test
    void admin_GetClientWithInvalidParams_Size_Null_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", "1");
        queryParams.add("size", null);

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(2),
                        jsonPath("$.total_pages").value(1),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(2));
    }

    // Order
    @Test
    void admin_GetClientWithInvalidParams_Order_Without_Orderby_UnprocessableEntity() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", "1");
        queryParams.add("size", "10");
        queryParams.add("order", "asc");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("query.order"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages[0]")
                                .value("El campo para ordenar es requerido cuando se ha definido un orden"));
    }

    @Test
    void admin_GetClientWithInvalidParams_Order_Invalid_UnprocessableEntity() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", "1");
        queryParams.add("size", "10");
        queryParams.add("order", "abcd");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("order"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages[0]")
                                .value("asc, desc, none"));
    }

    @Test
    void admin_GetClientWithInvalidParams_Order_Null_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", "1");
        queryParams.add("size", "10");
        queryParams.add("order", null);

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(2),
                        jsonPath("$.total_pages").value(1),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(2));
    }

    // OrderBy
    // TODO
    @Test
    void admin_GetClientWithInvalidParams_OrderBy_Name_Invalid_UnprocessableEntity() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", "1");
        queryParams.add("size", "10");
        queryParams.add("order", "asc");
        queryParams.add("order_by", "invalid");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("query.order_by"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages[0]")
                                .value("???"));
    }

    @Test
    void admin_GetClientWithInvalidParams_OrderBy_Name_Valid_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", "1");
        queryParams.add("size", "10");
        queryParams.add("order", "asc");
        queryParams.add("order_by", "first_name");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(2),
                        jsonPath("$.total_pages").value(1),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(2),
                        jsonPath("$.content[0].first_name").value("Juan"),
                        jsonPath("$.content[1].first_name").value("María"));
    }

    @Test
    void admin_GetClientWithInvalidParams_OrderBy_Name_Null_UnprocessableEntity() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", "1");
        queryParams.add("size", "10");
        queryParams.add("order", "asc");
        queryParams.add("order_by", null);

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("query.order"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages[0]")
                                .value("El campo para ordenar es requerido cuando se ha definido un orden"));
    }

    // FirstName
    @Test
    void admin_GetClientWithInvalidParams_FirstName_Valid_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("first_name", "Juan");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(1),
                        jsonPath("$.total_pages").value(1),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(1),
                        jsonPath("$.content[0].first_name").value("Juan"));
    }

    // TODO
    @Test
    void admin_GetClientWithInvalidParams_FirstName_Blank_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("first_name", " ");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(0),
                        jsonPath("$.total_pages").value(0),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(0));
    }

    @Test
    void admin_GetClientWithInvalidParams_FirstName_Null_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("first_name", null);

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(2),
                        jsonPath("$.total_pages").value(1),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(2));
    }

    // LastName
    @Test
    void admin_GetClientWithInvalidParams_LastName_Valid_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("last_name", "Pérez");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(1),
                        jsonPath("$.total_pages").value(1),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(1),
                        jsonPath("$.content[0].last_name").value("Pérez"));
    }

    @Test
    void admin_GetClientWithInvalidParams_LastName_Blank_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("last_name", " ");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(0),
                        jsonPath("$.total_pages").value(0),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(0));
    }

    @Test
    void admin_GetClientWithInvalidParams_LastName_Null_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("last_name", null);

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(2),
                        jsonPath("$.total_pages").value(1),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(2));
    }

    // Identification
    @Test
    void admin_GetClientWithInvalidParams_Identification_Valid_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("identification", "12345678");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(1),
                        jsonPath("$.total_pages").value(1),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(1),
                        jsonPath("$.content[0].identification").value("12345678"));
    }

    @Test
    void admin_GetClientWithInvalidParams_LastName_Identification_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("identification", " ");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(0),
                        jsonPath("$.total_pages").value(0),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(0));
    }

    @Test
    void admin_GetClientWithInvalidParams_Identification_Null_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("identification", null);

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(2),
                        jsonPath("$.total_pages").value(1),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(2));
    }

    // Identification Type
    @Test
    void admin_GetClientWithInvalidParams_IdentificationType_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("identification_type", "DNI");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(1),
                        jsonPath("$.total_pages").value(1),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(1));
    }

    @Test
    void admin_GetClientWithInvalidParams_IdentificationType_Invalid_UnprocessableEntity() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("identification_type", "abcd");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isUnprocessableEntity())
                .andExpectAll(
                        jsonPath("$.message").value(MESSAGE_UNPROCESSABLE_ENTITY),
                        jsonPath("$.details.length()").value(1),
                        jsonPath("$.details[0].field").value("identification_type"),
                        jsonPath("$.details[0].messages.length()").value(1),
                        jsonPath("$.details[0].messages[0]")
                                .value("Illegal argument: No enum constant com.vluepixel.vetmanager.api.client.core.domain.enums.IdentificationType.abcd"));
    }

    @Test
    void admin_GetClientWithInvalidParams_IdentificationType_Null_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("identification_type", null);

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(2),
                        jsonPath("$.total_pages").value(1),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(2));
    }

    // Address
    @Test
    void admin_GetClientWithInvalidParams_Address_Valid_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("address", "Av. Santa Anita");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(1),
                        jsonPath("$.total_pages").value(1),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(1),
                        jsonPath("$.content[0].address").value("Av. Santa Anita"));
    }

    @Test
    void admin_GetClientWithInvalidParams_Address_Blank_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("address", " ");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(0),
                        jsonPath("$.total_pages").value(0),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(0));
    }

    @Test
    void admin_GetClientWithInvalidParams_Address_Null_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("address", null);

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(2),
                        jsonPath("$.total_pages").value(1),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(2));
    }

    // Phone
    // TODO
    @Test
    void admin_GetClientWithInvalidParams_Phone_Valid_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("phone", "999999999");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(1),
                        jsonPath("$.total_pages").value(1),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(1),
                        jsonPath("$.content[0].phones[0]").value("999999999"));
    }

    @Test
    void admin_GetClientWithInvalidParams_Phone_Blank_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("phone", " ");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(0),
                        jsonPath("$.total_pages").value(0),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(0));
    }

    @Test
    void admin_GetClientWithInvalidParams_Phone_Null_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("phone", null);

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(2),
                        jsonPath("$.total_pages").value(1),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(2));
    }

    // Email
    // TODO
    @Test
    void admin_GetClientWithInvalidParams_Email_Valid_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("email", "firstclient@firstclient.com");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(1),
                        jsonPath("$.total_pages").value(1),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(1),
                        jsonPath("$.content[0].emails[0]").value("firstclient@firstclient.com"));
    }

    @Test
    void admin_GetClientWithInvalidParams_Email_Blank_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("email", " ");

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(0),
                        jsonPath("$.total_pages").value(0),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(0));
    }

    @Test
    void admin_GetClientWithInvalidParams_Email_Null_Ok() throws Exception {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("email", null);

        mockMvc.perform(get("/client")
                .queryParams(queryParams)
                .header("Authorization", BEARER_ADMIN_JWT))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.page").value(1),
                        jsonPath("$.size").value(10),
                        jsonPath("$.total_elements").value(2),
                        jsonPath("$.total_pages").value(1),
                        jsonPath("$.message").value(MESSAGE_OK),
                        jsonPath("$.content").isArray(),
                        jsonPath("$.content.length()").value(2));
    }
}