package com.itau.pixkeyregistration.domain.gateways;

import com.itau.pixkeyregistration.domain.pix.PixKey;
import com.itau.pixkeyregistration.domain.pix.update.PixKeyUpdate;

public interface PixKeyUpdateGateway {
    PixKeyUpdate update(PixKey pixKey);
}
