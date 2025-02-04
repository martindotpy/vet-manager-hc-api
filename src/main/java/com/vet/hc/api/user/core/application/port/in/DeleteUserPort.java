package com.vet.hc.api.user.core.application.port.in;

import com.vet.hc.api.shared.application.port.in.DeleteEntityPort;
import com.vet.hc.api.user.core.domain.failure.UserFailure;

/**
 * Delete user port.
 */
public interface DeleteUserPort extends DeleteEntityPort<UserFailure, Long> {
}
