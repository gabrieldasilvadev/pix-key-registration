package com.itau.pixkeyregistration.domain.gateways;

import com.itau.pixkeyregistration.domain.pix.PixKey;
import com.itau.pixkeyregistration.domain.pix.PixKeyUpdate;

public interface PixKeyUpdateGateway {
    PixKeyUpdate update(PixKey pixKey);
}
