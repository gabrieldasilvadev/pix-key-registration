package com.itau.pixkeyregistration.domain.pix.usecases;

import com.itau.pixkeyregistration.domain.gateways.PixKeyUpdateGateway;
import com.itau.pixkeyregistration.domain.gateways.PixStorageGateway;
import com.itau.pixkeyregistration.domain.pix.PixKey;
import com.itau.pixkeyregistration.domain.pix.update.PixKeyUpdate;
import com.itau.pixkeyregistration.domain.valueobjects.PixKeyUpdateTransient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PixKeyUpdateUseCase {
    private final PixKeyUpdateGateway pixKeyUpdateGateway;
    private final PixStorageGateway pixStorageGateway;
    public PixKeyUpdate update(PixKeyUpdateTransient PixKeyUpdateTransient) {
        PixKey pixKey = pixStorageGateway.findById(PixKeyUpdateTransient.keyId());
        pixKey.updateKeyValue(PixKeyUpdateTransient.keyValue());
        return pixKeyUpdateGateway.update(pixKey);
    }
}
