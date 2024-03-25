package com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa;

import com.itau.pixkeyregistration.commons.mappers.PixMapper;
import com.itau.pixkeyregistration.domain.exceptions.PixKeyNotFoundException;
import com.itau.pixkeyregistration.domain.gateways.PixStorageGateway;
import com.itau.pixkeyregistration.domain.pix.PixKey;
import com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.entities.AccountTable;
import com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.repositories.AccountRepository;
import com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.repositories.PixKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PixStorageJpa implements PixStorageGateway {
    private final PixKeyRepository pixKeyRepository;
    private final AccountRepository accountRepository;
    private final PixMapper pixMapper;

    @Override
    public PixKey findById(String keyId) {
        var pixKeyTable = pixKeyRepository.findById(keyId).orElseThrow(() -> new PixKeyNotFoundException(
                "PIX_KEY_NOT_FOUND",
                String.format("The key with the id: %s was not found", keyId)
        ));

        return pixMapper.toDomain(pixKeyTable);
    }

    @Override
    public void save(PixKey pixKey, String accountId) {
        AccountTable accountTable = accountRepository.findById(accountId).orElseThrow();
        var pixKeyTable = pixMapper.toTable(pixKey, accountTable);
        pixKeyRepository.save(pixKeyTable);
    }

    @Override
    public boolean areUniqueKey(String value) {
        return pixKeyRepository.areKeysUnique(value);
    }
}
