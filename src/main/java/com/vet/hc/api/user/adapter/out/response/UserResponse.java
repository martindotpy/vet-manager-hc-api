package com.vet.hc.api.user.adapter.out.response;

import com.vet.hc.api.shared.domain.query.ContentResponse;
import com.vet.hc.api.user.application.response.UserDto;

import lombok.experimental.SuperBuilder;

/**
 * User response.
 */
@SuperBuilder
public class UserResponse extends ContentResponse<UserDto> {
}
