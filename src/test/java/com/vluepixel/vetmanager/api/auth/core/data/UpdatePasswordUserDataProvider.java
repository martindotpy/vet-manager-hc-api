package com.vluepixel.vetmanager.api.auth.core.data;

import com.vluepixel.vetmanager.api.auth.core.domain.request.UpdatePasswordRequest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Update password user data provider.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdatePasswordUserDataProvider {

    public static final UpdatePasswordRequest VALID_UPDATE_PASSWORD_REQUEST = UpdatePasswordRequest
            .builder()
            .password("user")
            .newPassword("hgbfgpdpswvmliuczlcydlogaqvuizsucsbveaqeybufpjssdigfzcribvnalewldrcxs")
            .build();
}
