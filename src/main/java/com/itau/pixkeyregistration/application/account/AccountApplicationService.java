package com.itau.pixkeyregistration.application.account;

import com.itau.pixkeyregistration.application.web.dto.*;
import com.itau.pixkeyregistration.domain.account.Account;

public interface AccountApplicationService {
    Account create(CreateAccountRequestDto createAccountRequestDto);

    CreateAddressResponseDto registerAddress(CreateAddressRequestDto createAddressRequestDto);

    PixKeyRegistrationResponseDto registerPix(PixKeyRegistrationRequestDto pixKeyRegistrationRequestDto);

    PixKeyUpdateResponseDto updatePixKey(PixKeyUpdateAccountRequestDto pixKeyUpdateAccountRequestDto);

    InactivePixKeyResponseDto inactivePixKey(String keyId);
}
