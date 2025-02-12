package com.vluepixel.vetmanager.api.user.core.adapter.in.response;

import com.vluepixel.vetmanager.api.shared.adapter.in.response.PaginatedResponse;
import com.vluepixel.vetmanager.api.user.core.application.dto.UserDto;

import lombok.experimental.SuperBuilder;

/**
 * User paginated response.
 */
@SuperBuilder
public final class PaginatedUserResponse extends PaginatedResponse<UserDto> {
}
