package com.itau.pixkeyregistration.domain.gateways;

import com.itau.pixkeyregistration.domain.pix.PixKey;

public interface PixStorageGateway {
    PixKey findById(String keyId);

    void save(PixKey pixKey, String account);

    boolean areUniqueKey(String value);
}
