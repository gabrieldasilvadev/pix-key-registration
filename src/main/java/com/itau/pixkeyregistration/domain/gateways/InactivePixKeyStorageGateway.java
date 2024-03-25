package com.itau.pixkeyregistration.domain.gateways;

import com.itau.pixkeyregistration.domain.pix.PixKey;
import com.itau.pixkeyregistration.domain.pix.inactive.PixKeyInactive;

public interface InactivePixKeyStorageGateway {
    PixKeyInactive inactive(PixKey pixKey);
}
