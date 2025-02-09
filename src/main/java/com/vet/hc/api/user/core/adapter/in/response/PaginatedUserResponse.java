package com.vet.hc.api.user.core.adapter.in.response;

import com.vet.hc.api.shared.adapter.in.response.PaginatedResponse;
import com.vet.hc.api.user.core.application.dto.UserDto;

import lombok.experimental.SuperBuilder;

/**
 * User paginated response.
 */
@SuperBuilder
public final class PaginatedUserResponse extends PaginatedResponse<UserDto> {
}
