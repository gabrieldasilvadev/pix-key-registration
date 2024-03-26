package com.itau.pixkeyregistration.infrastructure.outbound.persistence;

import com.itau.pixkeyregistration.commons.mappers.PixMapper;
import com.itau.pixkeyregistration.domain.gateways.PixKeyUpdateGateway;
import com.itau.pixkeyregistration.domain.pix.PixKey;
import com.itau.pixkeyregistration.domain.pix.update.PixKeyUpdate;
import com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.repositories.AccountRepository;
import com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.repositories.PixKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PixKeyUpdateGatewayImpl implements PixKeyUpdateGateway {
    private final PixKeyRepository pixKeyRepository;
    private final AccountRepository accountRepository;
    private final PixMapper pixMapper;

    @Override
    public PixKeyUpdate update(PixKey pixKey) {
        var account = accountRepository.findById(pixKey.getAccountId()).orElseThrow();
        var pixKeyCreatedAt = pixKeyRepository.findById(pixKey.getId()).orElseThrow().getCreatedAt();
        var pixKeyUpdate = pixMapper.toTable(pixKey, account);

        pixKeyRepository.save(pixKeyUpdate);

        return new PixKeyUpdate(
                pixKeyUpdate.getId(),
                pixKeyUpdate.getKeyType(),
                pixKeyUpdate.getKeyValue(),
                account.getType(),
                account.getAgencyNumber(),
                account.getNumber(),
                account.getPerson().getFirstName(),
                account.getPerson().getLastName(),
                account.getId(),
                pixKeyUpdate.getStatus(),
                pixKeyCreatedAt
        );
    }
}
