package com.vet.hc.api.user.core.adapter.in.response;

import com.vet.hc.api.shared.adapter.in.response.ContentResponse;
import com.vet.hc.api.user.core.domain.dto.UserDto;

import lombok.experimental.SuperBuilder;

/**
 * User response.
 */
@SuperBuilder
public class UserResponse extends ContentResponse<UserDto> {
}
