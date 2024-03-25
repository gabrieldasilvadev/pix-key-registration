package com.itau.pixkeyregistration.domain.pix;

import com.itau.pixkeyregistration.domain.account.enums.AccountType;
import com.itau.pixkeyregistration.domain.pix.enums.PixKeyStatus;
import com.itau.pixkeyregistration.domain.pix.enums.PixKeyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class PixKeyUpdate {
    String keyId;
    PixKeyType keyType;
    String keyValue;
    AccountType accountType;
    String agency;
    String accountNumber;
    String firstName;
    String lastName;
    String accountId;
    PixKeyStatus keyStatus;
    LocalDateTime keyAddedAt;
}
