package com.vet.hc.api.auth.core.data;

import java.util.List;

import com.vet.hc.api.user.core.application.dto.UserDto;
import com.vet.hc.api.user.core.domain.model.enums.UserRole;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Auth data provider.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthDataProvider {
    // -----------------------------------------------------------------------------------------------------------------
    // Users
    // -----------------------------------------------------------------------------------------------------------------

    // - Role: USER
    // cspell:disable-next-line
    public static final String USER_JWT = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjp7ImlkIjoyLCJmaXJzdF9uYW1lIjoiVXNlciIsImxhc3RfbmFtZSI6IlVzZXIiLCJlbWFpbCI6InVzZXJAdXNlci5jb20iLCJyb2xlcyI6WyJVU0VSIl0sInByb2ZpbGVfaW1hZ2VfdXJsIjpudWxsfSwic3ViIjoiMiJ9.j43TE5dJHU1_DLME_7yj6Ux-wXrf_M7jYliaMBybsbE";

    public static final String BEARER_USER_JWT = "Bearer " + USER_JWT;

    public static final UserDto USER_DTO = UserDto.builder()
            .id(2L)
            .firstName("User")
            .lastName("User")
            .email("user@user.com")
            .profileImageUrl(null)
            .roles(List.of(UserRole.USER))
            .build();

    // - Role: ADMIN
    public static final String ADMIN_JWT = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjp7ImlkIjoxLCJmaXJzdF9uYW1lIjoiQWRtaW4iLCJsYXN0X25hbWUiOiJBZG1pbiIsImVtYWlsIjoiYWRtaW5AYWRtaW4uY29tIiwicm9sZXMiOlsiQURNSU4iXSwicHJvZmlsZV9pbWFnZV91cmwiOm51bGx9LCJzdWIiOiIxIn0.mj6KdYCJbXmZbXts1HDG252mzT-VUX8WienLj0hh2WU";

    public static final String BEARER_ADMIN_JWT = "Bearer " + ADMIN_JWT;

    public static final UserDto ADMIN_DTO = UserDto.builder()
            .id(1L)
            .firstName("admin")
            .lastName("admin")
            .email("admin@admin.com")
            .profileImageUrl(null)
            .roles(List.of(UserRole.ADMIN))
            .build();
}
