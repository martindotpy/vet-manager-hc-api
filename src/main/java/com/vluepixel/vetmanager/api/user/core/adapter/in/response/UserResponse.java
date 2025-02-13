package com.vluepixel.vetmanager.api.user.core.adapter.in.response;

import com.vluepixel.vetmanager.api.shared.adapter.in.response.ContentResponse;
import com.vluepixel.vetmanager.api.user.core.application.dto.UserDto;

import lombok.experimental.SuperBuilder;

/**
 * User response.
 */
@SuperBuilder
public final class UserResponse extends ContentResponse<UserDto> {
}
