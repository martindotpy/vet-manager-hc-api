package com.vet.hc.api.auth.adapter.in.request;

import lombok.Getter;

@Getter
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
