package com.itau.pixkeyregistration.application.pix;

import com.itau.pixkeyregistration.application.web.dto.InactivePixKeyResponseDto;
import com.itau.pixkeyregistration.application.web.dto.PixKeyRegistrationRequestDto;
import com.itau.pixkeyregistration.application.web.dto.PixKeyUpdateRequestDto;
import com.itau.pixkeyregistration.application.web.dto.PixKeyUpdateResponseDto;

public interface PixApplicationService {
    void registerKey(PixKeyRegistrationRequestDto pixKeyRegistrationRequestDto);

    InactivePixKeyResponseDto inactiveKey(String keyId);

    PixKeyUpdateResponseDto update(PixKeyUpdateRequestDto pixKeyUpdateRequestDto);
}
