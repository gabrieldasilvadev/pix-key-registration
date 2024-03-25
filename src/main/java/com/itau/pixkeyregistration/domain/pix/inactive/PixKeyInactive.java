package com.itau.pixkeyregistration.domain.pix.inactive;

import com.itau.pixkeyregistration.domain.pix.enums.PixKeyStatus;
import com.itau.pixkeyregistration.domain.pix.enums.PixKeyType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PixKeyInactive {
    String keyId;
    PixKeyType keyType;
    String keyValue;
    PixKeyStatus keyStatus;
    PixKeyInactiveAccountDetails accountDetails;
    LocalDateTime keyAddedAt;
    LocalDateTime keyInactivatedAt;
}
