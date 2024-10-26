package com.vet.hc.api.auth.application.service;

import com.vet.hc.api.auth.application.port.in.LoginUserPort;
import com.vet.hc.api.auth.domain.command.LoginUserCommand;
import com.vet.hc.api.auth.domain.failure.AuthFailure;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.user.application.response.UserDto;

public class LoginUserService implements LoginUserPort{
    @Override
    public Result<UserDto, AuthFailure> loginUser(LoginUserCommand command) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loginUser'");
    }
}
