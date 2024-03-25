package com.itau.pixkeyregistration.domain.pix.usecases;

import com.itau.pixkeyregistration.domain.gateways.InactivePixKeyStorageGateway;
import com.itau.pixkeyregistration.domain.gateways.PixStorageGateway;
import com.itau.pixkeyregistration.domain.pix.PixKey;
import com.itau.pixkeyregistration.domain.pix.inactive.PixKeyInactive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InactivePixKeyUseCase {
    private final PixStorageGateway pixStorage;
    private final InactivePixKeyStorageGateway inactivePixKeyStorage;
    public PixKeyInactive inactive(String keyId) {
        PixKey pixKey = pixStorage.findById(keyId);
        pixKey.inactive();
        return inactivePixKeyStorage.inactive(pixKey);
    }
}
