package com.vet.hc.api.user.adapter.out.response;

import com.vet.hc.api.shared.domain.query.Response;
import com.vet.hc.api.user.application.response.UserDto;

import lombok.experimental.SuperBuilder;

/**
 * User response.
 */
@SuperBuilder
public class UserResponse extends Response<UserDto> {
}
