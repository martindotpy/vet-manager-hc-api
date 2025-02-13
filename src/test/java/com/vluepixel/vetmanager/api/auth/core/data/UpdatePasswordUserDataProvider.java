package com.vluepixel.vetmanager.api.auth.core.data;

import com.vluepixel.vetmanager.api.auth.core.adapter.in.request.UpdatePasswordRequest;
import com.vluepixel.vetmanager.api.auth.core.domain.payload.UpdatePasswordPayload;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Update password user data provider.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdatePasswordUserDataProvider {

    public static final UpdatePasswordPayload VALID_UPDATE_PASSWORD_REQUEST = UpdatePasswordRequest
            .builder()
            .password("user")
            .newPassword("hgbfgpdpswvmliuczlcydlogaqvuizsucsbveaqeybufpjssdigfzcribvnalewldrcxs")
            .build();
}
