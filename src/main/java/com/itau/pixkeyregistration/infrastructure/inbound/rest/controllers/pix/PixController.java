package com.itau.pixkeyregistration.infrastructure.inbound.rest.controllers.pix;

import com.itau.pixkeyregistration.application.pix.PixApplicationService;
import com.itau.pixkeyregistration.application.web.api.PixApi;
import com.itau.pixkeyregistration.application.web.dto.InactivePixKeyResponseDto;
import com.itau.pixkeyregistration.application.web.dto.PixKeyRegistrationRequestDto;
import com.itau.pixkeyregistration.application.web.dto.PixKeyUpdateRequestDto;
import com.itau.pixkeyregistration.application.web.dto.PixKeyUpdateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PixController implements PixApi {
    private final PixApplicationService pixService;

    @Override
    public ResponseEntity<Void> registerKey(PixKeyRegistrationRequestDto pixKeyRegistrationRequestDto) {
        pixService.registerKey(pixKeyRegistrationRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Override
    public ResponseEntity<PixKeyUpdateResponseDto> updatePixKey(PixKeyUpdateRequestDto pixKeyUpdateRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(pixService.update(pixKeyUpdateRequestDto));
    }

    @Override
    public ResponseEntity<InactivePixKeyResponseDto> inactiveKey(String keyId) {
        return ResponseEntity.status(HttpStatus.OK).body(pixService.inactiveKey(keyId));
    }
}
